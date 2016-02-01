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
package com.github.pffy.chinese.test;

import com.github.pffy.chinese.HanyuPinyin;
import com.github.pffy.chinese.Tone;
import java.util.stream.IntStream;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;

/**
 * HanyuPinyinTest.java - JUnit Test Cases for HanyuPinyin.java
 *
 * @author The Pffy Authors
 *
 */
public class HanyuPinyinTest {

    /**
     * Test method for
     * {@link com.github.pffy.chinese.HanyuPinyin#convert(java.lang.String)}.
     */
    @Test
    public void testConvert() {
        String hw = "你好世界";
        System.out.println("Tone Number = " + HanyuPinyin.convert(hw));
        Assert.assertEquals("ni3 hao3 shi4 jie4", HanyuPinyin.convert(hw));
    }

    /**
     * Test method for
     * {@link com.github.pffy.chinese.HanyuPinyin#convert(java.lang.String, com.github.pffy.chinese.Tone)}.
     */
    @Test
    public void testConvertWithToneOff() {
        String hw = "你好世界";
        System.out.println("Tone Off    = " + HanyuPinyin.convert(hw, Tone.TONES_OFF));
        Assert.assertEquals("ni hao shi jie", HanyuPinyin.convert(hw, Tone.TONES_OFF));
    }

    /**
     * Test method for
     * {@link com.github.pffy.chinese.HanyuPinyin#convert(java.lang.String, com.github.pffy.chinese.Tone)}.
     */
    @Test
    public void testConvertWithToneMarks() {
        String hw = "你好世界";
        System.out.println("Tone Marks  = " + HanyuPinyin.convert(hw, Tone.TONE_MARKS));
        Assert.assertEquals("nǐ haǒ shì jiè", HanyuPinyin.convert(hw, Tone.TONE_MARKS));
    }

    @Test
    public void testConvertBenchMark() {
        String hw = "你好世界";
        HanyuPinyin.convert(hw);
        StopWatch s = new StopWatch();
        s.start();
        IntStream.rangeClosed(1, 10000).parallel().forEach(e -> {
            HanyuPinyin.convert(hw);
        });
        s.stop();
        System.out.println("Time taken to convert 你好世界 10000 times: " + s.toString());
        Assert.assertNotNull(s);
    }

}
