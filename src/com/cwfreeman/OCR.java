package com.cwfreeman;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    private static Iterable<AccountData> parseAccountNumbers(String[] lines) {
        AccountList accounts = new AccountList(lines);
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

class AccountList implements Iterable<AccountData>
{
    List<AccountData> accounts;
    public AccountList(String[] lines) {
        accounts = new ArrayList<AccountData>();
        for( int i = 0; i < lines.length; i+= 4 ) {
            final AccountData account = new AccountData(Arrays.copyOfRange(lines, i, i + 3));
            accounts.add(account);
        }
    }

    public Iterator<AccountData> iterator() {
        return accounts.iterator();
    }
}