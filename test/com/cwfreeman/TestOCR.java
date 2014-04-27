package com.cwfreeman;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestOCR {
    @Test
    public void readsCorrectDigits() {
        Assert.assertEquals(1, OCR.getDigitForLines(Arrays.asList("   ", "  |", "  |")));
        Assert.assertEquals(2, OCR.getDigitForLines(Arrays.asList(" _ ", " _|", "|_ ")));
        Assert.assertEquals(3, OCR.getDigitForLines(Arrays.asList(" _ ", " _|", " _|")));
        Assert.assertEquals(4, OCR.getDigitForLines(Arrays.asList("   ", "|_|", "  |")));
        Assert.assertEquals(5, OCR.getDigitForLines(Arrays.asList(" _ ", "|_ ", " _|")));
        Assert.assertEquals(6, OCR.getDigitForLines(Arrays.asList(" _ ", "|_ ", "|_|")));
        Assert.assertEquals(7, OCR.getDigitForLines(Arrays.asList(" _ ", "  |", "  |")));
        Assert.assertEquals(8, OCR.getDigitForLines(Arrays.asList(" _ ", "|_|", "|_|")));
        Assert.assertEquals(9, OCR.getDigitForLines(Arrays.asList(" _ ", "|_|", " _|")));
        Assert.assertEquals(0, OCR.getDigitForLines(Arrays.asList(" _ ", "| |", "|_|")));
    }

    @Test
    public void readsWholeAccountNumber() {
        String actual = OCR.recognizeOpticalCharacters(
                "    _  _     _  _  _  _  _ \n" +
                "  | _| _||_||_ |_   ||_||_|\n" +
                "  ||_  _|  | _||_|  ||_| _|\n"
        );
        Assert.assertEquals("123456789", actual);
    }

    @Test
    public void alertsIfAcctNumIsIllegible() {
        String actual = OCR.recognizeOpticalCharacters(
                "    _  _     _  _  _  _  _ \n" +
                "    _  _     _  _  _  _  _ \n" +
                "    _  _     _  _  _  _  _ \n"
        );
        Assert.assertEquals("????????? ILL", actual);
    }

    @Test
    public void alertsIfWrongCheckSum() {
        String actual = OCR.recognizeOpticalCharacters(
                " _  _     _  _        _  _ \n" +
                "|_ |_ |_| _|  |  ||_||_||_ \n" +
                "|_||_|  | _|  |  |  | _| _|\n"
        );
        Assert.assertEquals("664371495 ERR", actual);
    }
}
