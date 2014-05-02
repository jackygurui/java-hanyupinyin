java-hanyupinyin
================

Java implementation of Hanyu Pinyin object using the JSON-Java reference implementation

## SYNOPSIS ##

```

    - output : String
    - input : String
    - toneMode : Tone
    - hpdx : JSONObject
    - tmdx : JSONObject
    - tndx : JSONObject
    - trdx : JSONObject
    + HanyuPinyin()
    + HanyuPinyin(String)
    + HanyuPinyin(String, Tone)
    + HanyuPinyin(String, int)
    + toString() : String
    + setInput(String) : HanyuPinyin
    + getInput() : String
    + setMode(Tone) : HanyuPinyin
    + setMode(int) : HanyuPinyin
    - getMode() : Tone
    - convert() : void
    - atomize(String) : String
    - vacuum(String) : String
    - normalizeUmlaut(String) : String
    - init() : void

```