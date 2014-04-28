package com.cwfreeman;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cwfreeman on 4/27/14.
 */
class AccountDigit
{
    private static final DigitMap DIGITS = new DigitMap();

    private final Integer digit;
    boolean readError = false;
    private String cells;

    public AccountDigit(String line1, String line2, String line3) {
        cells = line1 + line2 + line3;
        if( DIGITS.containsKey(cells) )    {
            digit = DIGITS.getValue(cells);
            readError = false;
        } else {
            digit = null;
            readError = true;
        }
    }

    public Integer value() {
        return digit;
    }

    boolean hasReadError() {
        return readError;
    }

    @Override
    public String toString() {
        if( readError )
            return "?";
        else
            return digit.toString();
    }

    public Set<Integer> neighbors() {
        return DIGITS.getNeighbors(cells);
    }
}
