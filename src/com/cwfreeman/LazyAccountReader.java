package com.cwfreeman;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cwfreeman on 4/27/14.
 */
class LazyAccountReader implements Iterable<AccountData>, Iterator<AccountData>
{
    private final Iterator<String> lines;

   public LazyAccountReader(Iterator<String> lines) {
        this.lines = lines;
    }

    private List<String> getNextFourLines(Iterator<String> lines) {
        List<String> nextGroup = new ArrayList<String>();
        for( int i = 0; lines.hasNext() && i < 4; i++ ) {
            nextGroup.add(lines.next());
        }
        return nextGroup;
    }

    public Iterator<AccountData> iterator() {
        return this;
    }

   List<String> nextFourLines = null;

    @Override
    public boolean hasNext() {
        nextFourLines = getNextFourLines(lines);
        return nextFourLines.size() == 4;
    }

    @Override
    public AccountData next() {
        return new AccountData(nextFourLines);
    }

    @Override
    public void remove() {
        throw new NotImplementedException();
    }
}
