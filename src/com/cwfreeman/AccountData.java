package com.cwfreeman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cwfreeman on 4/27/14.
 */
class AccountData
{
    String value;
    List<AccountDigit> digits;
    private boolean readError = false;

    public AccountData(String[] data)
    {
        this(Arrays.asList(data));
    }

    public AccountData(List<String> listData)
    {
        digits = new ArrayList<AccountDigit>();
        value = "";
        readError = false;
        for( AccountDigit digit : new DigitIterator(listData)) {
            digits.add(digit);
            readError |= digit.hasReadError();
            value += digit.toString();
        }
    }

    boolean hasReadError() {
        return readError;
    }

    @Override
    public String toString() {
        if( hasReadError() )
            return value + " ILL";
        if( !passesCheckSum(toValues(digits)) )
            return value + " ERR";
        return value;
    }

    boolean passesCheckSum(List<Integer> digits) {
        int sum = 0;
        for( int i = 0; i < 9; i++) {
            final int digit = digits.get(i);
            sum += (9 - i) * digit;
        }
        return (sum % 11) == 0;
    }

    private List<Integer> toValues(List<AccountDigit> digits) {
        List<Integer> ints = new ArrayList<Integer>();
        for( AccountDigit ad : digits ) {
            ints.add(ad.value());
        }
        return ints;
    }


}


