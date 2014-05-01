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
    + toString()
    + setInput(String)
    + getInput()
    + setMode(Tone)
    + setMode(int)
    + getMode()
    - convert()
    - atomize()
    - vacuum()
    - init()

```