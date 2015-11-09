java-hanyupinyin
================

Java implementation of Hanyu Pinyin object using the JSON-Java reference implementation

## FEATURES 

  + Convert Hanzi to Pinyin (Chinese characters to Hanyu Pinyin)
  + Convert pinyin tone marks to tone numbers
  + Convert pinyin tone numbers to tone marks

## DEMO

```java

package foo;

import com.github.pffy.chinese.HanyuPinyin;
import com.github.pffy.chinese.Tone;

public class Foo {
  public static void main(String[] args) {
    
    String str;
    HanyuPinyin hp;
    
    // bad grammar, good example
    str = "你是不是中国人吗是不是";
    hp = new HanyuPinyin();
    
    hp.setMode(Tone.TONE_MARKS);
    hp.setInput(str);
    
    // nǐ shì bù shi zhōng guó rén ma shì bù shi 
    System.out.println(hp);
    
  }
}


```

## SYNOPSIS

  + HanyuPinyin()
  + HanyuPinyin(String)
  + HanyuPinyin(String, Tone)
  + ~~HanyuPinyin(String, int)~~ (DEPRECATED)

  + toString() : String

  + getInput() : String
  + setInput(String) : HanyuPinyin

  + getMode() : Tone
  + setMode(Tone) : HanyuPinyin
  + ~~setMode(int) : HanyuPinyin~~ (DEPRECATED)
    

### Dependencies

+ Idx Files
  + https://github.com/pffy/idx-json

+ JSON-Java
  + https://github.com/pffy/JSON-java

