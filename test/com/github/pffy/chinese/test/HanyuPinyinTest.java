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

import org.junit.Assert;
import org.junit.Test;

import com.github.pffy.chinese.HanyuPinyin;
import com.github.pffy.chinese.Tone;

/**
 * HanyuPinyinTest.java - JUnit Test Cases for HanyuPinyin.java
 * 
 * @author The Pffy Authors
 * 
 */
public class HanyuPinyinTest {

  /**
   * Test method for {@link com.github.pffy.chinese.HanyuPinyin#HanyuPinyin()}.
   */
  @Test
  public void testHanyuPinyin() {

    // default constructor should have no drameh
    Assert.assertSame((new HanyuPinyin()).getClass(), HanyuPinyin.class);
    Assert.assertTrue((new HanyuPinyin()).getInput().equals(""));

  }

  /**
   * Test method for {@link com.github.pffy.chinese.HanyuPinyin#HanyuPinyin(java.lang.String)}.
   */
  @Test
  public void testHanyuPinyinString() {

    // null should be handled like an empty string
    Assert.assertSame((new HanyuPinyin(null)).getClass(), HanyuPinyin.class);

  }

  /**
   * Test method for
   * {@link com.github.pffy.chinese.HanyuPinyin#HanyuPinyin(java.lang.String, com.github.pffy.chinese.Tone)}
   * .
   */
  @Test
  public void testHanyuPinyinStringTone() {

    // null should be handled like an empty string
    // should not disrupt tone display modes
    Assert.assertSame((new HanyuPinyin(null, Tone.TONE_MARKS)).getClass(), HanyuPinyin.class);
    Assert.assertSame((new HanyuPinyin(null, Tone.TONE_MARKS)).getMode().getClass(), Tone.class);
    Assert.assertSame((new HanyuPinyin(null, Tone.TONE_MARKS)).getMode(), Tone.TONE_MARKS);
  }

  /**
   * Test method for {@link com.github.pffy.chinese.HanyuPinyin#HanyuPinyin(java.lang.String, int)}.
   */
  @Test
  public void testHanyuPinyinStringInt() {

    // expected
    Assert.assertTrue((new HanyuPinyin(null, 1)).getMode().equals(Tone.TONE_NUMBERS));
    Assert.assertTrue((new HanyuPinyin(null, 2)).getMode().equals(Tone.TONES_OFF));
    Assert.assertTrue((new HanyuPinyin(null, 3)).getMode().equals(Tone.TONE_MARKS));

    // unexpected. goes to HanyuPinyin(String, Tone) constructor.
    Assert.assertTrue((new HanyuPinyin(null, null)).getMode().equals(Tone.TONE_NUMBERS));

  }

  /**
   * Test method for {@link com.github.pffy.chinese.HanyuPinyin#toString()}.
   */
  @Test
  public void testToString() {

    // should be string representation of the class
    HanyuPinyin hp = new HanyuPinyin();
    Assert.assertSame(hp.toString().getClass(), String.class);

  }

  /**
   * Test method for {@link com.github.pffy.chinese.HanyuPinyin#getInput()}.
   */
  @Test
  public void testGetInput() {

    // input should be a string
    HanyuPinyin hp = new HanyuPinyin();
    Assert.assertTrue(hp.getInput().getClass().equals(String.class));

  }

  /**
   * Test method for {@link com.github.pffy.chinese.HanyuPinyin#setInput(java.lang.String)}.
   */
  @Test
  public void testSetInput() {

    // null should be handled like an empty string
    HanyuPinyin hp = new HanyuPinyin();
    Assert.assertTrue(hp.setInput(null).getClass().equals(HanyuPinyin.class));

  }

  /**
   * Test method for {@link com.github.pffy.chinese.HanyuPinyin#setInput(java.lang.String)}.
   */
  @Test
  public void testSetInputHelloWorld() {

    // hello world
    String hw = "你好世界";
    HanyuPinyin hp = new HanyuPinyin();
    Assert.assertTrue(hp.setInput(hw).getInput() == hw);
    
    // confirm conversion took place
    Assert.assertTrue(hp.setInput(hw).toString() != null);

  }
  
  /**
   * Test method for {@link com.github.pffy.chinese.HanyuPinyin#getMode()}.
   */
  @Test
  public void testGetMode() {

    // should be the enum type Tone
    HanyuPinyin hp = new HanyuPinyin();
    Assert.assertTrue(hp.getMode().getClass().equals(Tone.class));

  }

  /**
   * Test method for
   * {@link com.github.pffy.chinese.HanyuPinyin#setMode(com.github.pffy.chinese.Tone)}.
   */
  @Test
  public void testSetModeTone() {

    // null modes are set to Tone.TONE_NUMBERS
    HanyuPinyin hp = new HanyuPinyin();
    hp.setMode(null);

  }

  /**
   * Test method for {@link com.github.pffy.chinese.HanyuPinyin#setMode(int)}.
   */
  @Test
  public void testSetModeInt() {

    HanyuPinyin hp = new HanyuPinyin();

    // expected integers
    Assert.assertTrue(hp.setMode(1).getMode() == Tone.TONE_NUMBERS);
    Assert.assertTrue(hp.setMode(2).getMode() == Tone.TONES_OFF);
    Assert.assertTrue(hp.setMode(3).getMode() == Tone.TONE_MARKS);
    

    // unexpected integers, set to Tone.TONE_NUMBERS
    Assert.assertTrue(hp.setMode(888).getMode() == Tone.TONE_NUMBERS);
    Assert.assertTrue(hp.setMode(-82).getMode() == Tone.TONE_NUMBERS);
    Assert.assertTrue(hp.setMode(99).getMode() == Tone.TONE_NUMBERS);

    // curve-ball. should go to setMode(Tone) method to pass.
    // set to Tone.TONE_NUMBERS
    Assert.assertTrue(hp.setMode(null).getMode() == Tone.TONE_NUMBERS);

  }

}
