package ca.retrylife.inputlib.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TypesTest {

    @Test
    public void testString() {

        // Check convertibility
        assertTrue("String can be converted to a String", Types.STRING.canConvertTo(Types.STRING));
        assertTrue("String can be converted to a Character", Types.STRING.canConvertTo(Types.CHARACTER));
        assertFalse("String can not be converted to a Integer", Types.STRING.canConvertTo(Types.INTEGER));
        assertFalse("String can not be converted to a Double", Types.STRING.canConvertTo(Types.DOUBLE));
        assertFalse("String can not be converted to a Float", Types.STRING.canConvertTo(Types.FLOAT));
        assertTrue("String can be converted to a Boolean", Types.STRING.canConvertTo(Types.BOOLEAN));

        // Check conversions
        assertEquals("hello", Types.STRING.toString("hello"));
        assertEquals((Character) (char) 'h', Types.STRING.toCharacter("hello"));
        assertThrows(RuntimeException.class, () -> Types.STRING.toInteger("hello"));
        assertThrows(RuntimeException.class, () -> Types.STRING.toDouble("hello"));
        assertThrows(RuntimeException.class, () -> Types.STRING.toFloat("hello"));
        assertEquals(true, Types.STRING.toBoolean("hello"));
    }

    @Test
    public void testCharacter() {

        // Check convertibility
        assertTrue("Character can be converted to a String", Types.CHARACTER.canConvertTo(Types.STRING));
        assertTrue("Character can be converted to a Character", Types.CHARACTER.canConvertTo(Types.CHARACTER));
        assertTrue("Character can be converted to a Integer", Types.CHARACTER.canConvertTo(Types.INTEGER));
        assertTrue("Character can be converted to a Double", Types.CHARACTER.canConvertTo(Types.DOUBLE));
        assertTrue("Character can be converted to a Float", Types.CHARACTER.canConvertTo(Types.FLOAT));
        assertTrue("Character can be converted to a Boolean", Types.CHARACTER.canConvertTo(Types.BOOLEAN));

        // Check conversions
        assertEquals("h", Types.CHARACTER.toString('h'));
        assertEquals((Character) (char) 'h', Types.CHARACTER.toCharacter('h'));
        assertEquals((Integer) (int) 'h', Types.CHARACTER.toInteger('h'));
        assertEquals((Double) (double) (int) 'h', Types.CHARACTER.toDouble('h'));
        assertEquals((Float) (float) (int) 'h', Types.CHARACTER.toFloat('h'));
        assertEquals(true, Types.CHARACTER.toBoolean('h'));
    }

    @Test
    public void testInteger() {

        // Check convertibility
        assertTrue("Integer can be converted to a String", Types.INTEGER.canConvertTo(Types.STRING));
        assertTrue("Integer can be converted to a Character", Types.INTEGER.canConvertTo(Types.CHARACTER));
        assertTrue("Integer can be converted to a Integer", Types.INTEGER.canConvertTo(Types.INTEGER));
        assertTrue("Integer can be converted to a Double", Types.INTEGER.canConvertTo(Types.DOUBLE));
        assertTrue("Integer can be converted to a Float", Types.INTEGER.canConvertTo(Types.FLOAT));
        assertTrue("Integer can be converted to a Boolean", Types.INTEGER.canConvertTo(Types.BOOLEAN));

        // Check conversions
        assertEquals("100", Types.INTEGER.toString(100));
        assertEquals((Character) (char) 100, Types.INTEGER.toCharacter(100));
        assertEquals((Integer) 100, Types.INTEGER.toInteger(100));
        assertEquals((Double) 100.0, Types.INTEGER.toDouble(100));
        assertEquals((Float) 100.0f, Types.INTEGER.toFloat(100));
        assertEquals(true, Types.INTEGER.toBoolean(100));
    }

    @Test
    public void testDouble() {

        // Check convertibility
        assertTrue("Double can be converted to a String", Types.DOUBLE.canConvertTo(Types.STRING));
        assertTrue("Double can be converted to a Character", Types.DOUBLE.canConvertTo(Types.CHARACTER));
        assertTrue("Double can be converted to a Integer", Types.DOUBLE.canConvertTo(Types.INTEGER));
        assertTrue("Double can be converted to a Double", Types.DOUBLE.canConvertTo(Types.DOUBLE));
        assertTrue("Double can be converted to a Float", Types.DOUBLE.canConvertTo(Types.FLOAT));
        assertTrue("Double can be converted to a Boolean", Types.DOUBLE.canConvertTo(Types.BOOLEAN));

        // Check conversions
        assertEquals("100.0", Types.DOUBLE.toString(100.0));
        assertEquals((Character) (char) 100, Types.DOUBLE.toCharacter(100.0));
        assertEquals((Integer) 100, Types.DOUBLE.toInteger(100.0));
        assertEquals((Double) 100.0, Types.DOUBLE.toDouble(100.0));
        assertEquals((Float) 100.0f, Types.DOUBLE.toFloat(100.0));
        assertEquals(true, Types.DOUBLE.toBoolean(100.0));
    }

    @Test
    public void testFloat() {

        // Check convertibility
        assertTrue("Float can be converted to a String", Types.FLOAT.canConvertTo(Types.STRING));
        assertTrue("Float can be converted to a Character", Types.FLOAT.canConvertTo(Types.CHARACTER));
        assertTrue("Float can be converted to a Integer", Types.FLOAT.canConvertTo(Types.INTEGER));
        assertTrue("Float can be converted to a Double", Types.FLOAT.canConvertTo(Types.DOUBLE));
        assertTrue("Float can be converted to a Float", Types.FLOAT.canConvertTo(Types.FLOAT));
        assertTrue("Float can be converted to a Boolean", Types.FLOAT.canConvertTo(Types.BOOLEAN));

        // Check conversions
        assertEquals("100.0", Types.FLOAT.toString(100.0f));
        assertEquals((Character) (char) 100, Types.FLOAT.toCharacter(100.0f));
        assertEquals((Integer) 100, Types.FLOAT.toInteger(100.0f));
        assertEquals((Double) 100.0, Types.FLOAT.toDouble(100.0f));
        assertEquals((Float) 100.0f, Types.FLOAT.toFloat(100.0f));
        assertEquals(true, Types.FLOAT.toBoolean(100.0f));
    }

    @Test
    public void testBoolean() {

        // Check convertibility
        assertTrue("Boolean can be converted to a String", Types.BOOLEAN.canConvertTo(Types.STRING));
        assertTrue("Boolean can be converted to a Character", Types.BOOLEAN.canConvertTo(Types.CHARACTER));
        assertTrue("Boolean can be converted to a Integer", Types.BOOLEAN.canConvertTo(Types.INTEGER));
        assertTrue("Boolean can be converted to a Double", Types.BOOLEAN.canConvertTo(Types.DOUBLE));
        assertTrue("Boolean can be converted to a Float", Types.BOOLEAN.canConvertTo(Types.FLOAT));
        assertTrue("Boolean can be converted to a Boolean", Types.BOOLEAN.canConvertTo(Types.BOOLEAN));

        // Check conversions
        assertEquals("true", Types.BOOLEAN.toString(true));
        assertEquals((Character) 'T', Types.BOOLEAN.toCharacter(true));
        assertEquals((Integer) 1, Types.BOOLEAN.toInteger(true));
        assertEquals((Double) 1.0, Types.BOOLEAN.toDouble(true));
        assertEquals((Float) 1f, Types.BOOLEAN.toFloat(true));
        assertEquals(true, Types.BOOLEAN.toBoolean(true));
    }

}