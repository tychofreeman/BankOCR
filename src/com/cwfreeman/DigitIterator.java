package com.cwfreeman;

import java.util.Iterator;
import java.util.List;

/**
 * Created by cwfreeman on 4/27/14.
 */
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
