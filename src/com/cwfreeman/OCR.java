package com.cwfreeman;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cwfreeman on 4/26/14.
 */
public class OCR {
    private static String ONE =   "   "
                                + "  |"
                                + "  |";

    private static String TWO =   " _ "
                                + " _|"
                                + "|_ ";

    private static String THREE = " _ "
                                + " _|"
                                + " _|";
    private static String FOUR =  "   "
                                + "|_|"
                                + "  |";
    private static String FIVE =  " _ "
                                + "|_ "
                                + " _|";
    private static String SIX =   " _ "
                                + "|_ "
                                + "|_|";
    private static String SEVEN = " _ "
                                + "  |"
                                + "  |";
    private static String EIGHT = " _ "
                                + "|_|"
                                + "|_|";
    private static String NINE =  " _ "
                                + "|_|"
                                + " _|";
    private static String ZERO =  " _ "
                                + "| |"
                                + "|_|";

    private static final Map<String,Integer> DIGITS = new HashMap<String, Integer>();
    static {
        final List<String> digitList = Arrays.asList(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE);
        for( int i = 0; i < 10; i++ ) {
            DIGITS.put(encodeDigit(digitList.get(i)), i);
        }

    }

    private static String encodeDigit(String digitData) {
        return digitData.replace('|', '*').replace('_', '*');
    }

    public static int getDigitForLines(List<String> digitLines) {
        final String digitData = digitLines.get(0) + digitLines.get(1) + digitLines.get(2);
        return convertDataToDigit(digitData);
    }

    private static int convertDataToDigit(String digitData) {
        final String bitwise = encodeDigit(digitData);
        if( DIGITS.containsKey(bitwise) )
            return DIGITS.get(bitwise);
        return -1;
    }

    public static String recognizeOpticalCharacters(String lines) {
        final String ocrResult = recognizeOpticalCharacters(lines.split("\n"));
        return encodeAcctNum(ocrResult);
    }

    private static boolean passesCheckSum(String ocrResult) {
        int sum = 0;
        for( int i = 0; i < 9; i++) {
            final int digit = Integer.parseInt(ocrResult.substring(i, i+1));
            sum += (9 - i) * digit;
        }
        return (sum % 11) == 0;
    }

    private static String encodeAcctNum(String ocrResult) {
        if( ocrResult.contains("?") )
            return ocrResult + " ILL";
        if( !passesCheckSum(ocrResult) )
            return ocrResult + " ERR";
        return ocrResult;
    }

    private static String recognizeOpticalCharacters(String[] strings) {
        String accumulator = "";
        for( int i = 0; i < 9; i++) {
            List<String> digitData = getNthDigitData(strings, i);
            Integer num = getDigitForLines(digitData);
            if( num >= 0 )
                accumulator += num;
            else
                accumulator += "?";
        }
        return accumulator;
    }

    private static List<String> getNthDigitData(String[] strings, int n) {
        final int i = 3 * n;
        return Arrays.asList(
                strings[0].substring(i, i + 3),
                strings[1].substring(i, i + 3),
                strings[2].substring(i, i + 3));
    }

}
