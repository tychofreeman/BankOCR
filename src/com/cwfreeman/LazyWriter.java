package com.cwfreeman;

import java.util.Iterator;

/**
 * Created by cwfreeman on 4/27/14.
 */
public class LazyWriter implements Iterable<String>, Iterator<String>{
    private final Iterator<AccountData> accounts;

    public LazyWriter(Iterator<String> data) {
        accounts = new LazyAccountReader(data).iterator();
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return accounts.hasNext();
    }

    @Override
    public String next() {
        AccountData next = accounts.next();
        return OCR.encodeAcctNumber(next);
    }

    @Override
    public void remove() {
        accounts.remove();
    }
}