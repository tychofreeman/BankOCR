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
        List<String> accum = new ArrayList<String>();
        for( AccountData account : parseAccountNumbers(lines)) {
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
        String ocrResult = lineData.getValue();
        if(lineData.hasReadError())
            return ocrResult + " ILL";
        if( !lineData.passesCheckSum() )
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

class Digit extends HashMap<String, Integer>
{

}

class AccountDigit
{
    private static final Map<String,Integer> DIGITS = new Digit();
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


    static {
        final List<String> digitList = Arrays.asList(AccountDigit.ZERO, AccountDigit.ONE, AccountDigit.TWO, AccountDigit.THREE, AccountDigit.FOUR, AccountDigit.FIVE, AccountDigit.SIX, AccountDigit.SEVEN, AccountDigit.EIGHT, AccountDigit.NINE);
        for( int i = 0; i < 10; i++ ) {
            AccountDigit.DIGITS.put(digitList.get(i), i);
        }

    }

    private String cells;

    public AccountDigit(String line1, String line2, String line3) {
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

    boolean passesCheckSum() {
        int sum = 0;
        for( int i = 0; i < 9; i++) {
            final int digit = Integer.parseInt(value.substring(i, i + 1));
            sum += (9 - i) * digit;
        }
        return (sum % 11) == 0;
    }

    boolean hasReadError() {
        return value.contains("?");
    }

    String getValue() {
        return value;
    }

    private String parseAcctNumber(List<String> data) {
        String accumulator = "";
        for( int i = 0; i < 9; i++) {
            AccountDigit digitData = getNthDigitData(i, data);
            Integer num = digitData.convertToDigit();
            if( num >= 0 )
                accumulator += num;
            else
                accumulator += "?";
        }
        return accumulator;
    }

    private AccountDigit getNthDigitData(int n, List<String> data) {
        final int i = 3 * n;
        return new AccountDigit(
                data.get(0).substring(i, i + 3),
                data.get(1).substring(i, i + 3),
                data.get(2).substring(i, i + 3));
    }
}