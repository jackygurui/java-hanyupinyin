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
 * HanyuPinyin.java - Converts Chinese Characters and Hanyu Pinyin into pinyin with tone marks, tone
 * marks, or no tones.
 * 
 * @author The Pffy Authors
 * @version 0.1
 * 
 */

public class HanyuPinyin {

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
    this.toneMode = mode;
    setInput(str);
  }


  /**
   * Builds object, sets input string, sets tone mode
   * 
   * @param str - Chinese character or Hanyu Pinyin input
   * @param mode - tone mark display mode as number
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
   * Sets input string for conversion by the object
   * 
   * @param str - input string for conversion
   * @return HanyuPinyin - this object
   */
  public HanyuPinyin setInput(String str) {
    this.input = normalizeUmlaut(str);
    convert();
    return this;
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
   * Sets the tone display mode with an Enum type
   * 
   * @param mode - tone display mode of enum type Tone
   * @return HanyuPinyin - this object
   */
  public HanyuPinyin setMode(Tone mode) {
    this.toneMode = mode;
    setInput(this.input);
    return this;
  }


  /**
   * Sets the tone display mode with an integer
   * 
   * @param mode - tone display mode of type integer
   * @return HanyuPinyin - this object
   */
  public HanyuPinyin setMode(int mode) {
    return setMode(toneMode.setModeValue(mode));
  }


  /**
   * Returns tone display mode with an enum type Tone
   * 
   * @return Tone - The enum Type called Tone
   */
  public Tone getMode() {
    return this.toneMode;
  }


  // converts input based on class properties
  private void convert() {

    String str;
    str = input;

    Iterator<?> keys;
    String key;

    keys = hpdx.keys();

    // converts Hanzi to Pinyin
    while (keys.hasNext()) {
      key = (String) keys.next();
      str = str.replace(key, hpdx.getString(key) + " ");
    }

    // converts pinyin display based on tone mode setting
    switch (toneMode) {

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
    output = str;
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
    return str.replaceAll("   ", " ").replaceAll("  ", " ");
  }

  // normalizes umlaut u to double-u (uu)
  private String normalizeUmlaut(String str) {
    return str.replaceAll("ü", "uu").replaceAll("u:", "uu");
  }


  // loads JSON idx files into JSONObjects
  private void init() {

    InputStream is;

    try {

      is = HanyuPinyin.class.getResourceAsStream("/json/IdxHanyuPinyin.json");
      hpdx = new JSONObject(new JSONTokener(is));

      is = HanyuPinyin.class.getResourceAsStream("/json/IdxToneMarks.json");
      tmdx = new JSONObject(new JSONTokener(is));

      is = HanyuPinyin.class.getResourceAsStream("/json/IdxToneNumbers.json");
      tndx = new JSONObject(new JSONTokener(is));

      is = HanyuPinyin.class.getResourceAsStream("/json/IdxToneRemoval.json");
      trdx = new JSONObject(new JSONTokener(is));
      
      is = HanyuPinyin.class.getResourceAsStream("/json/IdxToneFive.json");
      tfdx = new JSONObject(new JSONTokener(is));

    } catch (Exception ex) {
      // failing silently is fine for now.
    }
  }

}
