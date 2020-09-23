package ca.retrylife.inputlib;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.retrylife.inputlib.types.Token;
import ca.retrylife.inputlib.types.Types;

public class ParserTest {

    @Test
    public void testStringParsing() {

        // Parse a token
        Token t = Parser.parseToToken("hello");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.STRING, t.getPrimaryType());
        assertEquals("Value", "hello", t.getString());
    }

    @Test
    public void testCharacterParsing() {

        // Parse a token
        Token t = Parser.parseToToken("h");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.CHARACTER, t.getPrimaryType());
        assertEquals("Value", (Character) 'h', t.getCharacter());
    }

    @Test
    public void testIntegerParsing() {

        // Parse a token
        Token t = Parser.parseToToken("100");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.INTEGER, t.getPrimaryType());
        assertEquals("Value", (Integer) 100, t.getInteger());
    }

    @Test
    public void testHexIntegerParsing() {

        // Parse a token
        Token t = Parser.parseToToken("0xff");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.INTEGER, t.getPrimaryType());
        assertEquals("Value", (Integer) 255, t.getInteger());
    }
}