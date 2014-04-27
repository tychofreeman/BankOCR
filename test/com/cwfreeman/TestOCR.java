package com.cwfreeman;

import org.junit.Assert;
import org.junit.Test;

public class TestOCR {
    @Test
    public void readsCorrectDigits() {
        Assert.assertEquals(1, new DigitData(
                "   ",
                "  |",
                "  |").convertToDigit());
        Assert.assertEquals(2, new DigitData(
                " _ ",
                " _|",
                "|_ ").convertToDigit());
        Assert.assertEquals(3, new DigitData(
                " _ ",
                " _|",
                " _|").convertToDigit());
        Assert.assertEquals(4, new DigitData(
                "   ",
                "|_|",
                "  |").convertToDigit());
        Assert.assertEquals(5, new DigitData(
                " _ ",
                "|_ ",
                " _|").convertToDigit());
        Assert.assertEquals(6, new DigitData(
                " _ ",
                "|_ ",
                "|_|").convertToDigit());
        Assert.assertEquals(7, new DigitData(
                " _ ",
                "  |",
                "  |").convertToDigit());
        Assert.assertEquals(8, new DigitData(
                " _ ",
                "|_|",
                "|_|").convertToDigit());
        Assert.assertEquals(9, new DigitData(
                " _ ",
                "|_|",
                " _|").convertToDigit());
        Assert.assertEquals(0, new DigitData(
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
