package com.cwfreeman;

import org.junit.Assert;
import org.junit.Test;

public class TestOCR {
    @Test
    public void readsCorrectDigits() {
        Assert.assertEquals(1, new AccountDigit(
                "   ",
                "  |",
                "  |").convertToDigit());
        Assert.assertEquals(2, new AccountDigit(
                " _ ",
                " _|",
                "|_ ").convertToDigit());
        Assert.assertEquals(3, new AccountDigit(
                " _ ",
                " _|",
                " _|").convertToDigit());
        Assert.assertEquals(4, new AccountDigit(
                "   ",
                "|_|",
                "  |").convertToDigit());
        Assert.assertEquals(5, new AccountDigit(
                " _ ",
                "|_ ",
                " _|").convertToDigit());
        Assert.assertEquals(6, new AccountDigit(
                " _ ",
                "|_ ",
                "|_|").convertToDigit());
        Assert.assertEquals(7, new AccountDigit(
                " _ ",
                "  |",
                "  |").convertToDigit());
        Assert.assertEquals(8, new AccountDigit(
                " _ ",
                "|_|",
                "|_|").convertToDigit());
        Assert.assertEquals(9, new AccountDigit(
                " _ ",
                "|_|",
                " _|").convertToDigit());
        Assert.assertEquals(0, new AccountDigit(
                " _ ",
                "| |",
                "|_|").convertToDigit());
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
