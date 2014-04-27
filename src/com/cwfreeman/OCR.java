package com.cwfreeman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by cwfreeman on 4/26/14.
 */
public class OCR {

    public static String ocrMultipleAccounts(String content) {
        String accum = "";
        for( String acct : ocrMultipleAccounts(content.split("\n"))) {
            accum += acct + "\n";
        }
        return accum;
    }

    private static List<String> ocrMultipleAccounts(String[] lines) {
        return ocrMultipleAccounts(parseAccountNumbers(lines));
    }

    private static List<String> ocrMultipleAccounts(List<AccountData> accounts) {
        List<String> accum = new ArrayList<String>();
        for( AccountData account : accounts ) {
            accum.add(encodeAcctNumber(account));
        }
        return accum;
    }

    private static List<AccountData> parseAccountNumbers(String[] lines) {
        List<AccountData> accounts = new ArrayList<AccountData>();
        for( int i = 0; i < lines.length; i+= 4 ) {
            final AccountData account = new AccountData(Arrays.copyOfRange(lines, i, i + 3));
            accounts.add(account);
        }
        return accounts;
    }

    static String encodeAcctNumber(AccountData lineData) {
        return encodeAcctNum(lineData.getValue());
    }

    private static boolean passesCheckSum(String ocrResult) {
        int sum = 0;
        for( int i = 0; i < 9; i++) {
            final int digit = Integer.parseInt(ocrResult.substring(i, i + 1));
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

    public static void main(String[] args) throws FileNotFoundException {
        if( args.length > 0 ) {
            String content = new Scanner(new File(args[0])).useDelimiter("\\Z").next();
            System.out.println(ocrMultipleAccounts(content));
        } else {
            System.err.println("Usage: OCR.class <filename>");
        }
    }

}

class DigitData
{
    static final Map<String,Integer> DIGITS = new HashMap<String, Integer>();
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


    static {
        final List<String> digitList = Arrays.asList(DigitData.ZERO, DigitData.ONE, DigitData.TWO, DigitData.THREE, DigitData.FOUR, DigitData.FIVE, DigitData.SIX, DigitData.SEVEN, DigitData.EIGHT, DigitData.NINE);
        for( int i = 0; i < 10; i++ ) {
            DigitData.DIGITS.put(digitList.get(i), i);
        }

    }

    private String cells;

    public DigitData(String line1, String line2, String line3) {
        cells = line1 + line2 + line3;
    }

    String getKey() {
        return cells;
    }

    public int convertToDigit() {
        final String digitKey = getKey();
        if( DIGITS.containsKey(digitKey) )
            return DIGITS.get(digitKey);
        return -1;
    }
}

class AccountData extends ArrayList<String>
{
    String value;
    public AccountData(String[] data)
    {
        value = parseAcctNumber(Arrays.asList(data));
    }

    String getValue() {
        return value;
    }

    private String parseAcctNumber(List<String> data) {
        String accumulator = "";
        for( int i = 0; i < 9; i++) {
            DigitData digitData = getNthDigitData(i, data);
            Integer num = digitData.convertToDigit();
            if( num >= 0 )
                accumulator += num;
            else
                accumulator += "?";
        }
        return accumulator;
    }

    private DigitData getNthDigitData(int n, List<String> data) {
        final int i = 3 * n;
        return new DigitData(
                data.get(0).substring(i, i + 3),
                data.get(1).substring(i, i + 3),
                data.get(2).substring(i, i + 3));
    }
}