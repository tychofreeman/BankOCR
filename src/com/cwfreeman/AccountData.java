package com.cwfreeman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cwfreeman on 4/27/14.
 */
class AccountData extends ArrayList<String>
{
    String value;
    private boolean readError = false;

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
        return readError;
    }

    String getValue() {
        return value;
    }

    private String parseAcctNumber(List<String> data) {
        String accumulator = "";
        for( int i = 0; i < 9; i++) {
            AccountDigit digitData = getNthDigitData(i, data);
            if (digitData.hasReadError()) {
                readError = true;
                accumulator += "?";
            } else {
                accumulator += digitData.value();
            }
        }
        return accumulator;
    }

    private static AccountDigit getNthDigitData(int n, List<String> data) {
        final int i = 3 * n;
        return new AccountDigit(
                data.get(0).substring(i, i + 3),
                data.get(1).substring(i, i + 3),
                data.get(2).substring(i, i + 3));
    }
}
