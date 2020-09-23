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
    public void testEmptyStringParsing() {

        // Parse a token
        Token t = Parser.parseToToken("");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.STRING, t.getPrimaryType());
        assertEquals("Value", "", t.getString());
    }
    @Test
    public void testPeriodStringParsing() {

        // Parse a token
        Token t = Parser.parseToToken(".");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.STRING, t.getPrimaryType());
        assertEquals("Value", ".", t.getString());
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
        Token t = Parser.parseToToken("7");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.INTEGER, t.getPrimaryType());
        assertEquals("Value", (Integer) 7, t.getInteger());
    }

    @Test
    public void testHexIntegerParsing() {

        // Parse a token
        Token t = Parser.parseToToken("0xff");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.INTEGER, t.getPrimaryType());
        assertEquals("Value", (Integer) 255, t.getInteger());
    }

    @Test
    public void testBinIntegerParsing() {

        // Parse a token
        Token t = Parser.parseToToken("0b1011");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.INTEGER, t.getPrimaryType());
        assertEquals("Value", (Integer) 11, t.getInteger());
    }

    @Test
    public void testDoubleParsing() {

        // Parse a token
        Token t = Parser.parseToToken("100_000.001");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.DOUBLE, t.getPrimaryType());
        assertEquals("Value", (Double) 100000.001, t.getDouble());
    }

    @Test
    public void testFloatParsing() {

        // Parse a token
        Token t = Parser.parseToToken("100_000.001f");

        // Make sure the primary type, and data are correct
        assertEquals("Primary Type", Types.FLOAT, t.getPrimaryType());
        assertEquals("Value", (Float) 100000.001f, t.getFloat());
    }

    @Test
    public void testBooleanParsing() {

        // Check every defined entry in the parser's boolean map
        for (String str : Parser.VALID_BOOLEANS_MAP.keySet()) {

            // Parse a token
            Token t = Parser.parseToToken(str);

            // Make sure the primary type, and data are correct
            assertEquals("Primary Type", Types.BOOLEAN, t.getPrimaryType());
            assertEquals("Value", (Boolean) Parser.VALID_BOOLEANS_MAP.get(str), t.getBoolean());
        }
    }
}