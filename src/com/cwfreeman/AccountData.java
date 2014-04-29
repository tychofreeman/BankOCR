package com.cwfreeman;

import java.util.*;

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
        }
        value = digitsAsString(digits);
    }

    private String digitsAsString(List digits) {
        String accum = "";
        for( Object digit : digits) {
            accum += digit.toString();
        }
        return accum;
    }

    private boolean hasReadError() {
        return readError;
    }

    @Override
    public String toString() {
        if( hasReadError() || !passesCheckSum(toValues(digits)) ) {
            final Iterator<List<Integer>> iterator = validNeighbors().iterator();
            if( iterator.hasNext() ) {
                final List<Integer> next = iterator.next();
                return digitsAsString(next);
            }
            if( hasReadError())
                return value + " ILL";
            if( !passesCheckSum(toValues(digits)) )
                return value + " ERR";
        }
        return value;
    }

    private boolean passesCheckSum(List<Integer> digits) {
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

    public Set<List<Integer>> neighbors() {
        Set<List<Integer>> neighbors = new HashSet<List<Integer>>();
        int i = 0;
        for(AccountDigit d : digits ) {
            for( Integer dNeighbor : d.neighbors() ) {
                final Integer[] neighborAcctNum = createNeighborAcctNum(i, dNeighbor);
                if( neighborAcctNum != null ) {
                    neighbors.add(Arrays.asList(neighborAcctNum));
                }
            }
            i++;
        }
        return neighbors;
    }

    private Integer[] createNeighborAcctNum(int replaceIndex, Integer dNeighbor) {
        Integer[] neighbor = new Integer[]{0,0,0,0,0,0,0,0,0};
        int i = 0;
        for( AccountDigit origDigit : digits ) {
            if( i != replaceIndex && origDigit.hasReadError() ) {
                return null;
            }
            neighbor[i++] = origDigit.value();
        }
        neighbor[replaceIndex] = dNeighbor;
        return neighbor;
    }


    public Set<List<Integer>> validNeighbors() {
        final HashSet<List<Integer>> validNeighbors = new HashSet<List<Integer>>();
        for( List<Integer> neighbor : neighbors() ) {
            if( passesCheckSum(neighbor) ) {
                validNeighbors.add(neighbor);
            }
        }
        return validNeighbors;
    }
}


