package com.cwfreeman;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TestOCR {
    @Test
    public void readsCorrectDigits() {
        Assert.assertEquals(new Integer(1), new AccountDigit(
                "   ",
                "  |",
                "  |").value());
        Assert.assertEquals(new Integer(2), new AccountDigit(
                " _ ",
                " _|",
                "|_ ").value());
        Assert.assertEquals(new Integer(3), new AccountDigit(
                " _ ",
                " _|",
                " _|").value());
        Assert.assertEquals(new Integer(4), new AccountDigit(
                "   ",
                "|_|",
                "  |").value());
        Assert.assertEquals(new Integer(5), new AccountDigit(
                " _ ",
                "|_ ",
                " _|").value());
        Assert.assertEquals(new Integer(6), new AccountDigit(
                " _ ",
                "|_ ",
                "|_|").value());
        Assert.assertEquals(new Integer(7), new AccountDigit(
                " _ ",
                "  |",
                "  |").value());
        Assert.assertEquals(new Integer(8), new AccountDigit(
                " _ ",
                "|_|",
                "|_|").value());
        Assert.assertEquals(new Integer(9), new AccountDigit(
                " _ ",
                "|_|",
                " _|").value());
        Assert.assertEquals(new Integer(0), new AccountDigit(
                " _ ",
                "| |",
                "|_|").value());
    }

    @Test
    public void readsWholeAccountNumber() {
        String actual = new AccountData(new String[]{
                "    _  _     _  _  _  _  _ \n",
                "  | _| _||_||_ |_   ||_||_|\n",
                "  ||_  _|  | _||_|  ||_| _|\n"
        }).toString();
        Assert.assertEquals("123456789", actual);
    }

    @Test
    public void alertsIfAcctNumIsIllegible() {
        String actual = new AccountData(new String[]{
                "    _  _     _  _  _  _  _ \n",
                "    _  _     _  _  _  _  _ \n",
                "    _  _     _  _  _  _  _ \n"
        }).toString();
        Assert.assertEquals("????????? ILL", actual);
    }

    @Test
    public void readsMultipleRecords() {
        String expected =
                "000000000\n" +
                "123456789\n" +
                "664371485\n";
        String input =
                " _  _  _  _  _  _  _  _  _ \n" +
                "| || || || || || || || || |\n" +
                "|_||_||_||_||_||_||_||_|| |\n" +
                "                           \n" +
                "    _  _     _  _  _  _  _ \n" +
                "  | _| _||_||_ |_   ||_||_|\n" +
                "  ||_  _|  | _||_|  ||_| _|\n" +
                "                           \n" +
                " _  _     _  _        _  _ \n" +
                "|_ |_ |_| _|  |  ||_||_||_ \n" +
                "|_||_|  | _|  |  |  | _| _|\n" +
                "                           \n" +
                "";
        Assert.assertEquals(expected, OCR.ocrMultipleAccounts(input));
    }

    @Test
    public void SevenIsANeighborOfOne() {
        Set<Integer> neighbors = new DigitMap().getNeighbors(
                "   "
              + "  |"
              + "  |"
        );
        Assert.assertTrue(neighbors.contains(7));
    }

    @Test
    public void findsNeighbors() {
        String[] input = {
                " _  _  _  _  _  _  _  _  _ \n",
                "  || || || || || || || || |\n",
                "  ||_||_||_||_||_||_||_||_|\n",
                "                           \n" } ;

        Set<List<Integer>> neighbors = new AccountData(input).neighbors();

        Assert.assertTrue(neighbors.contains(Arrays.asList(1,0,0,0,0,0,0,0,0)));
        Assert.assertTrue(neighbors.contains(Arrays.asList(7,8,0,0,0,0,0,0,0)));
        Assert.assertTrue(neighbors.contains(Arrays.asList(7,0,0,0,0,8,0,0,0)));
    }

    @Test
    public void findsNeighborsWithValidCheckSum() {
        String[] input = {
           " _  _  _  _  _  _  _  _  _ \n",
           "|_ |_ |_ |_ |_ |_ |_ |_ |_ \n",
           " _| _| _| _| _| _| _| _| _|\n"
        };
        Set<List<Integer>> validNeighbors = new AccountData(input).validNeighbors();
        Assert.assertFalse(validNeighbors.contains(Arrays.asList(5,5,5,5,5,5,5,5,5)));
        Assert.assertTrue(validNeighbors.contains(Arrays.asList(5, 5, 5, 6, 5, 5, 5, 5, 5)));
        Assert.assertFalse(validNeighbors.contains(Arrays.asList(5, 5, 5, 9, 5, 5, 5, 5, 5)));
    }

    @Test
    public void printsUniqueNeighborWithValidCheckSum() {
        String[] input = {
          " _  _  _  _  _  _  _  _  _ \n",
          " _| _| _| _| _| _| _| _| _|\n",
          " _| _| _| _| _| _| _| _| _|\n",
          "\n"
        };
        String expected = "333393333";
        String actual = new AccountData(input).toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findsNeighborsIfErrors() {
        String[] input = {
                " _  _  _  _  _  _  _  _  _ \n",
                "| || || || || || || || || |\n",
                "|_||_||_||_||_||_||_||_|| |\n",
                "                           \n",
        } ;

        String neighbors = new AccountData(input).toString();

        Assert.assertTrue(neighbors.contains("000000000"));
    }

}
