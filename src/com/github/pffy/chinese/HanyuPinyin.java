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

import java.io.InputStream;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @name     : PinyinFormat.java
 * @version  : 1.7
 * @updated  : 2015-11-03
 * @license  : http://unlicense.org/ The Unlicense
 * @git      : https://github.com/pffy/java-hanyupinyin
 * @notes    : Converts Hanzi to Hanyu Pinyin with display options.
 *
 */
public class HanyuPinyin {

  private final String HPJSON = "/json/IdxHanyuPinyin.json";
  private final String TMJSON = "/json/IdxToneMarks.json";
  private final String TNJSON = "/json/IdxToneNumbers.json";
  private final String TRJSON = "/json/IdxToneRemoval.json";
  private final String TFJSON = "/json/IdxToneFive.json";

  private final String FILE_NOT_LOADED = "File not loaded: ";

  private String output = "";
  private String input = "";

  private Tone toneMode = Tone.TONE_NUMBERS;

  private JSONObject hpdx = new JSONObject();
  private JSONObject tmdx = new JSONObject();
  private JSONObject tndx = new JSONObject();
  private JSONObject trdx = new JSONObject();
  private JSONObject tfdx = new JSONObject();

  /**
   * Builds this object.
   */
  public HanyuPinyin() {
    init();
  }

  /**
   * Builds object and sets input string.
   *
   * @param str Chinese character or Hanyu Pinyin input
   */
  public HanyuPinyin(String str) {
    init();
    setInput(str);
  }

  /**
   * Builds object, sets input string, and sets tone mode
   *
   * @param str - Chinese character or Hanyu Pinyin input
   * @param mode - tone mark display mode as Enum
   */
  public HanyuPinyin(String str, Tone mode) {
    init();
    setMode(mode);
    setInput(str);
  }

  /**
   * Builds object, sets input string, and sets tone mode
   *
   * @param str - Chinese character or Hanyu Pinyin input
   * @param mode - tone mark display mode as n integer
   * @deprecated since 1.7
   * 
   */
  public HanyuPinyin(String str, int mode) {
    init();
    setMode(mode);
    setInput(str);
  }

  /**
   * Returns string implementation of this object
   *
   * @return str - Hanyu Pinyin in specified tone mode
   */
  @Override
  public String toString() {
    return this.output;
  }

  /**
   * Returns input as a string
   *
   * @return str - input string
   */
  public String getInput() {
    return this.input;
  }

  /**
   * Sets input string for conversion by the object
   *
   * @param str - input string for conversion
   * @return HanyuPinyin - this object
   */
  public final HanyuPinyin setInput(String str) {

    if (str == null) {
      this.input = "";
    } else {
      this.input = normalizeUmlaut(str);
    }

    convert();
    return this;
  }

  /**
   * Returns tone display mode with an enum type Tone
   *
   * @return Tone - The enum Type called Tone
   */
  public Tone getMode() {
    return this.toneMode;
  }

  /**
   * Sets the tone display mode with an Enum type
   *
   * @param mode - tone display mode of enum type Tone
   * @return HanyuPinyin - this object
   */
  public final HanyuPinyin setMode(Tone mode) {

    if (mode == null) {
      this.toneMode = Tone.TONE_NUMBERS;
    } else {
      this.toneMode = mode;
    }

    convert();
    return this;
  }

  /**
   * Sets the tone display mode with an integer.
   * <p>
   * 2 - TONE_MARKS; 3 - TONES_OFF; otherwise - TONE_NUMBERS
   *
   * @param mode - tone display mode as an integer
   * @return HanyuPinyin - this object
   * @deprecated since 1.7
   */
  public final HanyuPinyin setMode(int mode) {

    switch(mode)
    {
      case 2:
        this.toneMode = Tone.TONES_OFF;
        break;
      case 3:
        this.toneMode = Tone.TONE_MARKS;
        break;
      default:
        this.toneMode = Tone.TONE_NUMBERS;
        break;
    }

    convert();
    return this;
  }

  // converts input based on class properties
  private void convert() {

    String str;
    Tone tone;

    Iterator<?> keys;
    String key;

    str = input;
    tone = toneMode;

    keys = hpdx.keys();

    // converts Hanzi to Pinyin
    while (keys.hasNext()) {
      key = (String) keys.next();
      str = str.replace(key, hpdx.getString(key) + " ");
    }

    // converts pinyin display based on tone mode setting
    switch (tone) {

      case TONE_MARKS:

        keys = tmdx.keys();

        // converts to tone marks
        while (keys.hasNext()) {
          key = (String) keys.next();
          str = str.replace(key, tmdx.getString(key) + " ");
        }

        keys = tfdx.keys();

        // safely removes tone5
        while (keys.hasNext()) {
          key = (String) keys.next();
          str = str.replace(key, tfdx.getString(key) + " ");
        }

        break;
      case TONES_OFF:

        keys = trdx.keys();

        // remove all tone numbers and marks
        while (keys.hasNext()) {
          key = (String) keys.next();
          str = str.replace(key, trdx.getString(key) + " ");
        }

        keys = tfdx.keys();

        // safely removes tone5
        while (keys.hasNext()) {
          key = (String) keys.next();
          str = str.replace(key, tfdx.getString(key) + " ");
        }

        break;

      default:

        keys = tndx.keys();

        // converts marks to numbers
        while (keys.hasNext()) {
          key = (String) keys.next();
          str = str.replace(key, tndx.getString(key) + " ");
        }

        break;
    }

    str = atomize(str);
    this.output = str;
  }

  // atomizes pinyin, creating space between pinyin units
  private String atomize(String str) {

    Iterator<?> keys;
    String key;

    keys = tmdx.keys();

    // atomizes pin1yin1 -> pin1 yin1
    while (keys.hasNext()) {
      key = (String) keys.next();
      str = str.replace(key, key + " ");
    }

    return vacuum(str);
  }

  // removes excess space between pinyin units
  private String vacuum(String str) {
    return str.replaceAll("[^\\S\\n]{2,}", " ");
  }


  // normalizes umlaut u to double-u (uu)
  private String normalizeUmlaut(String str) {
    return str.replaceAll("ü", "uu").replaceAll("u:", "uu");
  }


  // startup method
  private void init() {

    try {

      // load idx files
      this.hpdx = loadIdx(this.HPJSON);
      this.tmdx = loadIdx(this.TMJSON);
      this.tndx = loadIdx(this.TNJSON);
      this.trdx = loadIdx(this.TRJSON);
      this.tfdx = loadIdx(this.TFJSON);

    } catch (Exception ex) {
      System.out.println(this.FILE_NOT_LOADED + ex.getMessage());
    }
  }

  // loads JSON idx files into JSONObjects
  private JSONObject loadIdx(String str) {

    JSONObject jo;
    InputStream is;

    is = HanyuPinyin.class.getResourceAsStream(str);
    jo = new JSONObject(new JSONTokener(is));

    return jo;
  }
}
