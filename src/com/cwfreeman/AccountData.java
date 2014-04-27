package com.cwfreeman;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cwfreeman on 4/27/14.
 */
class AccountData
{
    String value;
    private boolean readError = false;

    public AccountData(String[] data)
    {
        this(Arrays.asList(data));
    }

    public AccountData(List<String> listData)
    {
        value = parseAcctNumber(listData);
    }

    String encodeAcctNumber() {
        if(hasReadError())
            return value + " ILL";
        if( !passesCheckSum() )
            return value + " ERR";
        return value;
    }

    @Override
    public String toString() {
        return this.encodeAcctNumber();
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


    private String parseAcctNumber(List<String> data) {
        String accumulator = "";
        for( AccountDigit digitData : new DigitIterator(data)) {
            if (digitData.hasReadError()) {
                readError = true;
                accumulator += "?";
            } else {
                accumulator += digitData.value();
            }
        }
        return accumulator;
    }


}


class DigitIterator implements Iterator<AccountDigit>, Iterable<AccountDigit>
{

    private List<String> data;
    private int digitIndex = 0;

    public DigitIterator(List<String> data) {

        this.data = data;
    }

    static AccountDigit getNthDigitData(int n, List<String> data) {
        final int i = 3 * n;
        return new AccountDigit(
                data.get(0).substring(i, i + 3),
                data.get(1).substring(i, i + 3),
                data.get(2).substring(i, i + 3));
    }

    @Override
    public Iterator<AccountDigit> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return digitIndex < 9;
    }

    @Override
    public AccountDigit next() {
        AccountDigit digit = getNthDigitData(digitIndex, data);
        digitIndex++;
        return digit;
    }

    @Override
    public void remove() {

    }
}