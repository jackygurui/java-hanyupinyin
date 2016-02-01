/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or distribute this software, either
 * in source code form or as a compiled binary, for any purpose, commercial or non-commercial, and
 * by any means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors of this software dedicate
 * any and all copyright interest in the software to the public domain. We make this dedication for
 * the benefit of the public at large and to the detriment of our heirs and successors. We intend
 * this dedication to be an overt act of relinquishment in perpetuity of all present and future
 * rights to this software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>
 */
package com.github.pffy.chinese;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * @name : HanyuPinyin.java
 * @version : 1.8
 * @updated : 2015-11-06
 * @license : http://unlicense.org/ The Unlicense
 * @git : https://github.com/pffy/java-hanyupinyin
 * @notes : Converts Hanzi to Hanyu Pinyin with display options.
 *
 */
public class HanyuPinyin {

    private static final String HPJSON = "/idx/IdxHanyuPinyin.json";
    private static final String TMJSON = "/idx/IdxToneMarksIso.json";
    private static final String TRJSON = "/idx/IdxToneRemoval.json";
    private static final String TFJSON = "/idx/IdxToneFive.json";
    private static final String FILE_NOT_LOADED = "File not loaded: ";
    private static final HashMap<Integer, HashMap<String, String>> hpNum = new HashMap<>();//since the map is read only. Use non thread safe hash map purely for performance reason.
    private static final HashMap<Integer, HashMap<String, String>> hpMrk = new HashMap<>();//since the map is read only. Use non thread safe hash map purely for performance reason.
    private static final HashMap<Integer, HashMap<String, String>> hpOff = new HashMap<>();//since the map is read only. Use non thread safe hash map purely for performance reason.

    // loads JSON idx files into JSONObjects
    private static HashMap<String, String> loadIdx(String str, ObjectMapper m) throws IOException {
        InputStream is = HanyuPinyin.class.getResourceAsStream(str);
        TypeReference<HashMap<String, String>> hashMap = new TypeReference<HashMap<String, String>>() {
        };
        return m.readValue(is, hashMap);
    }

    // startup method
    static {
        try {
            ObjectMapper m = new ObjectMapper();
            // load idx files
            HashMap<String, String> tmdx = loadIdx(TMJSON, m);
            HashMap<String, String> trdx = loadIdx(TRJSON, m);
            HashMap<String, String> tfdx = loadIdx(TFJSON, m);
            loadIdx(HPJSON, m).entrySet().stream().forEach(e -> {
                Integer k = e.getKey().length();
                if (!hpNum.containsKey(k)) {
                    hpNum.put(k, new HashMap<>());
                }
                hpNum.get(k).put(e.getKey(), e.getValue());

                if (!hpMrk.containsKey(k)) {
                    hpMrk.put(k, new HashMap<>());
                }

                hpMrk.get(k).put(e.getKey(), Arrays.stream(e.getValue().split(" ")).map(f -> {
                    if (f.contains("5")) {
                        return tfdx.get(f);
                    } else {
                        Iterator<String> keys = tmdx.keySet().iterator();
                        // converts to tone marks
                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (f.contains(key)) {
                                return f.replace(key, tmdx.get(key));
                            }
                        }
                    }
                    return f;
                }).collect(Collectors.joining(" ")));

                if (!hpOff.containsKey(k)) {
                    hpOff.put(k, new HashMap<>());
                }

                hpOff.get(k).put(e.getKey(), Arrays.stream(e.getValue().split(" ")).map(f -> {
                    Iterator<String> keys = trdx.keySet().iterator();
                    // converts to tone marks
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (f.contains(key)) {
                            return f.replace(key, trdx.get(key));
                        }
                    }
                    return f;
                }).collect(Collectors.joining(" ")));
            });
        } catch (Exception ex) {
            System.out.println(FILE_NOT_LOADED + ex.getMessage());
        }
    }

    public static String convert(String input) {
        return convert(input, Tone.TONE_NUMBERS);
    }

    //Todo convert per phrase.
    public static String convert(String input, Tone tone) {
        String str = normalizeUmlaut(input);
        // converts Hanzi to Pinyin
        str = str.chars().boxed().map(c -> {
            String s = String.valueOf((char) c.intValue());
            HashMap<Integer, HashMap<String, String>> m;
            switch (tone) {
                case TONE_MARKS: {
                    m = hpMrk;
                    break;
                }
                case TONES_OFF: {
                    m = hpOff;
                    break;
                }
                default: {
                    m = hpNum;
                    break;
                }
            }
            return m.get(1).containsKey(s) ? m.get(1).get(s) : s;
        }).collect(Collectors.joining(" "));

        return vacuum(str);
    }

    // removes excess space between pinyin units
    private static String vacuum(String str) {
        return str.replaceAll("[^\\S\\n]{2,}", " ");
    }

    // normalizes umlaut u to double-u (uu)
    private static String normalizeUmlaut(String str) {
        return str.replaceAll("ü", "uu").replaceAll("u:", "uu");
    }

}
