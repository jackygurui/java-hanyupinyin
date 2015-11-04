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

import com.github.pffy.chinese.Tone;

/**
 * ToneTest.java - JUnit Test Cases for Tone.java
 * 
 * @author The Pffy Authors
 * 
 */
public class ToneTest {

  /**
   * Test method for {@link com.github.pffy.chinese.Tone#Tone(int)}.
   */
  @Test
  public void testTone() {
    // no constructor for enum Type
  }

  /**
   * Test method for {@link com.github.pffy.chinese.Tone#getMode()}.
   */
  @Test
  public void testGetMode() {

    // true enum by definition, but just in case there are typos...
    Assert.assertTrue(Tone.TONE_NUMBERS.getMode() == 1);
    Assert.assertTrue(Tone.TONES_OFF.getMode() == 2);
    Assert.assertTrue(Tone.TONE_MARKS.getMode() == 3);

  }

}
