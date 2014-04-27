package com.cwfreeman;

/**
 * Created by cwfreeman on 4/27/14.
 */
class AccountDigit
{
    private static final DigitMap DIGITS = new DigitMap();

    private final Integer digit;
    boolean readError = false;

    public AccountDigit(String line1, String line2, String line3) {
        String cells = line1 + line2 + line3;
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
}
