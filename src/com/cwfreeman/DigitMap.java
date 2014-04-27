package com.cwfreeman;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cwfreeman on 4/27/14.
 */
class DigitMap
{

    static String ONE =   "   "
                        + "  |"
                        + "  |";
    static String TWO =   " _ "
                        + " _|"
                        + "|_ ";
    static String THREE = " _ "
                        + " _|"
                        + " _|";
    static String FOUR =  "   "
                        + "|_|"
                        + "  |";
    static String FIVE =  " _ "
                        + "|_ "
                        + " _|";
    static String SIX =   " _ "
                        + "|_ "
                        + "|_|";
    static String SEVEN = " _ "
                        + "  |"
                        + "  |";
    static String EIGHT = " _ "
                        + "|_|"
                        + "|_|";
    static String NINE =  " _ "
                        + "|_|"
                        + " _|";
    static String ZERO =  " _ "
                        + "| |"
                        + "|_|";

    Map<String, Integer> map;
    public DigitMap() {
        map = new HashMap<String,Integer>();
        final List<String> digitList = Arrays.asList(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE);
        for( int i = 0; i < 10; i++ ) {
            map.put(digitList.get(i), i);
        }
    }

    public int getValue(String key) {
        return map.get(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }
}
