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
            value += digit.toString();
        }
    }

    private boolean hasReadError() {
        return readError;
    }

    @Override
    public String toString() {
        if( hasReadError() || !passesCheckSum(toValues(digits)) ) {

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
                neighbors.add(createNeighborAcctNum(i, dNeighbor));
            }
            i++;
        }
        return neighbors;
    }

    private List<Integer> createNeighborAcctNum(int i, Integer dNeighbor) {
        List<Integer> neighbor = new ArrayList<Integer>();
        for( AccountDigit origDigit : digits ) {
            neighbor.add(origDigit.value());
        }
        neighbor.set(i, dNeighbor);
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


