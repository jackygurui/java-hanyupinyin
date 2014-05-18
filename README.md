java-hanyupinyin
================

Java implementation of Hanyu Pinyin object using the JSON-Java reference implementation

## FEATURES 

  + Convert Hanzi to Pinyin (Chinese characters to Hanyu Pinyin)
  + Convert pinyin tone marks to tone numbers
  + Convert pinyin tone numbers to tone marks

## DEMO

```java

    // sandhi tone example (bad grammar, good test)
    String str = "你是不是中国人吗是不是";
    HanyuPinyin hp = new HanyuPinyin(str, Tone.TONE_MARKS);
    
    // nĭ shì bù shi zhōng guó rén ma shì bù shi 
    System.out.println(hp);


```

## SYNOPSIS

```

    + HanyuPinyin()
    + HanyuPinyin(String)
    + HanyuPinyin(String, Tone)
    + HanyuPinyin(String, int)

    + toString() : String

    + getInput() : String
    + setInput(String) : HanyuPinyin

    + getMode() : Tone
    + setMode(Tone) : HanyuPinyin
    + setMode(int) : HanyuPinyin
    

```
