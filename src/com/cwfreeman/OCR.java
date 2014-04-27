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

        for( AccountData s : new LazyAccountReader(content)) {
            accum += s + "\n";
        }
        return accum;
    }

    public static void main(String[] args) throws FileNotFoundException {
        if( args.length > 0 ) {
            Iterator<String> content = new Scanner(new File(args[0])).useDelimiter("\n");

            for( AccountData s : new LazyAccountReader(content)) {
                System.out.println(s);
            }
        } else {
            System.err.println("Usage: OCR.class <filename>");
        }
    }

}

