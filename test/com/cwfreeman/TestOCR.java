package com.cwfreeman;

import org.junit.Assert;
import org.junit.Test;

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
        String actual = OCR.encodeAcctNumber(new AccountData(new String[]{
                "    _  _     _  _  _  _  _ \n",
                "  | _| _||_||_ |_   ||_||_|\n",
                "  ||_  _|  | _||_|  ||_| _|\n"
        }));
        Assert.assertEquals("123456789", actual);
    }

    @Test
    public void alertsIfAcctNumIsIllegible() {
        String actual = OCR.encodeAcctNumber(new AccountData(new String[]{
                "    _  _     _  _  _  _  _ \n",
                "    _  _     _  _  _  _  _ \n",
                "    _  _     _  _  _  _  _ \n"
        }));
        Assert.assertEquals("????????? ILL", actual);
    }

    @Test
    public void alertsIfWrongCheckSum() {
        String actual = OCR.encodeAcctNumber(new AccountData(new String[]{
                " _  _     _  _        _  _ \n",
                "|_ |_ |_| _|  |  ||_||_||_ \n",
                "|_||_|  | _|  |  |  | _| _|\n"
        }));
        Assert.assertEquals("664371495 ERR", actual);
    }

    @Test
    public void readsMultipleRecords() {
        String expected =
                "00000000? ILL\n" +
                "123456789\n" +
                "664371495 ERR\n";
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
}
