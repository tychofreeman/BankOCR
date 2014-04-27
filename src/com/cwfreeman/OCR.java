package com.cwfreeman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by cwfreeman on 4/26/14.
 */
public class OCR {

    public static String ocrMultipleAccounts(String content) {
        Iterator<String> data = new Scanner(content).useDelimiter("\n");
        String accum = "";

        for( String s : new LazyWriter(data)) {
            accum += s + "\n";
        }
        return accum;
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
            Iterator<String> content = new Scanner(new File(args[0])).useDelimiter("\n");

            for( String s : new LazyWriter(content)) {
                System.out.println(s);
            }
        } else {
            System.err.println("Usage: OCR.class <filename>");
        }
    }

}

