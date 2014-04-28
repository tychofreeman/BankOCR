package com.cwfreeman;

import java.util.*;

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
            map.put(hash(digitList.get(i)), i);
        }

    }

    private String hash(String key) {
        return key.replace("|", ".").replace("_", ".");
    }

    public int getValue(String key) {
        return map.get(hash(key));
    }

    public boolean containsKey(String key) {
        return map.containsKey(hash(key));
    }

    public Set<Integer> getNeighbors(String oldKey) {

        String key = hash(oldKey);
        final HashSet<Integer> integers = new HashSet<Integer>();

        for( int i = 0; i < key.length(); i++ ) {
            String neighbor = flipCharAt(key, i);
            if(map.containsKey(neighbor)) {
                integers.add(map.get(neighbor));
            }
        }
        return integers;
    }

    private String flipCharAt(String key, int i) {
        char newChar = key.charAt(i);
        if( newChar == ' ' ) {
            newChar = '.';
        } else if( newChar == '.' ) {
            newChar = ' ';

        }
        return key.substring(0, i) + newChar + key.substring(i+1);
    }
}
