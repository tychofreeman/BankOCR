package com.cwfreeman;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestOCR {
    @Test
    public void readsCorrectDigits() {
        Assert.assertEquals(1, OCR.convertSingleDigit(Arrays.asList(
                "   ",
                "  |",
                "  |")));
        Assert.assertEquals(2, OCR.convertSingleDigit(Arrays.asList(
                " _ ",
                " _|",
                "|_ ")));
        Assert.assertEquals(3, OCR.convertSingleDigit(Arrays.asList(
                " _ ",
                " _|",
                " _|")));
        Assert.assertEquals(4, OCR.convertSingleDigit(Arrays.asList(
                "   ",
                "|_|",
                "  |")));
        Assert.assertEquals(5, OCR.convertSingleDigit(Arrays.asList(
                " _ ",
                "|_ ",
                " _|")));
        Assert.assertEquals(6, OCR.convertSingleDigit(Arrays.asList(
                " _ ",
                "|_ ",
                "|_|")));
        Assert.assertEquals(7, OCR.convertSingleDigit(Arrays.asList(
                " _ ",
                "  |",
                "  |")));
        Assert.assertEquals(8, OCR.convertSingleDigit(Arrays.asList(
                " _ ",
                "|_|",
                "|_|")));
        Assert.assertEquals(9, OCR.convertSingleDigit(Arrays.asList(
                " _ ",
                "|_|",
                " _|")));
        Assert.assertEquals(0, OCR.convertSingleDigit(Arrays.asList(
                " _ ",
                "| |",
                "|_|")));
    }

    @Test
    public void readsWholeAccountNumber() {
        String actual = OCR.parseAndEncodeAcctNumber(new String[]{
                "    _  _     _  _  _  _  _ \n",
                "  | _| _||_||_ |_   ||_||_|\n",
                "  ||_  _|  | _||_|  ||_| _|\n"
        });
        Assert.assertEquals("123456789", actual);
    }

    @Test
    public void alertsIfAcctNumIsIllegible() {
        String actual = OCR.parseAndEncodeAcctNumber( new String[]{
                "    _  _     _  _  _  _  _ \n",
                "    _  _     _  _  _  _  _ \n",
                "    _  _     _  _  _  _  _ \n"
        });
        Assert.assertEquals("????????? ILL", actual);
    }

    @Test
    public void alertsIfWrongCheckSum() {
        String actual = OCR.parseAndEncodeAcctNumber(new String[]{
                " _  _     _  _        _  _ \n",
                "|_ |_ |_| _|  |  ||_||_||_ \n",
                "|_||_|  | _|  |  |  | _| _|\n"
        });
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
