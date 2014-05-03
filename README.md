java-hanyupinyin
================

Java implementation of Hanyu Pinyin object using the JSON-Java reference implementation

## DEMO ##

```java

    // sandhi tone example (bad grammar, good test)
    String str = "你是不是中国人吗是不是";
    HanyuPinyin hp = new HanyuPinyin(str, Tone.TONE_MARKS);
    
    // nĭ shì bù shi zhōng guó rén ma shì bù shi 
    System.out.println(hp);


```

## SYNOPSIS ##

```

    + HanyuPinyin()
    + HanyuPinyin(String)
    + HanyuPinyin(String, Tone)
    + HanyuPinyin(String, int)
    + toString() : String
    + setInput(String) : HanyuPinyin
    + getInput() : String
    + setMode(Tone) : HanyuPinyin
    + setMode(int) : HanyuPinyin

```
