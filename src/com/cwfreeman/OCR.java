package com.cwfreeman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
            DIGITS.put(digitList.get(i), i);
        }

    }

    private static String getDigitKey(List<String> digitLines) {
        return digitLines.get(0) + digitLines.get(1) + digitLines.get(2);
    }

    public static int convertSingleDigit(List<String> digitLines) {
        final String digitKey = getDigitKey(digitLines);
        if( DIGITS.containsKey(digitKey) )
            return DIGITS.get(digitKey);
        return -1;
    }

    public static String ocrMultipleAccounts(String content) {
        return ocrMultipleAccounts(content.split("\n"));
    }

    private static String ocrMultipleAccounts(String[] lines) {
        return encodeAccounts(createAccounts(lines));
    }

    private static String encodeAccounts(List<AccountData> accounts) {
        String accum = "";
        for( AccountData account : accounts ) {
            accum += encodeAcctNumber(account) + "\n";
        }
        return accum;
    }

    private static List<AccountData> createAccounts(String[] lines) {
        List<AccountData> accounts = new ArrayList<AccountData>();
        for( int i = 0; i < lines.length; i+= 4 ) {
            final AccountData account = new AccountData(Arrays.copyOfRange(lines, i, i + 3));
            accounts.add(account);
        }
        return accounts;
    }

    static String parseAndEncodeAcctNumber(String[] acctNumLines) {
        final AccountData lineData = new AccountData(acctNumLines);
        return encodeAcctNumber(lineData);
    }

    private static String encodeAcctNumber(AccountData lineData) {
        return encodeAcctNum(lineData.parseAcctNumber());
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

class DigitData extends ArrayList<String>
{
    public DigitData(String line1, String line2, String line3) {
        add(line1);
        add(line2);
        add(line3);
    }
}

class AccountData extends ArrayList<String>
{
    public AccountData(String[] data)
    {
        for( String s : data )
        {
            add(s);
        }
    }

    String parseAcctNumber() {
        String accumulator = "";
        for( int i = 0; i < 9; i++) {
            List<String> digitData = getNthDigitData(i);
            Integer num = OCR.convertSingleDigit(digitData);
            if( num >= 0 )
                accumulator += num;
            else
                accumulator += "?";
        }
        return accumulator;
    }

    DigitData getNthDigitData(int n) {
        final int i = 3 * n;
        return new DigitData(
                get(0).substring(i, i + 3),
                get(1).substring(i, i + 3),
                get(2).substring(i, i + 3));
    }
}