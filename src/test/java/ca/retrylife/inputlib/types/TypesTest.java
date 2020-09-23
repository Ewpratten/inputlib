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
        assertTrue(Types.STRING.toCharacter("hello").equals('h'));
        assertThrows(RuntimeException.class, () -> Types.STRING.toInteger("hello"));
        assertThrows(RuntimeException.class, () -> Types.STRING.toDouble("hello"));
        assertThrows(RuntimeException.class, () -> Types.STRING.toFloat("hello"));
        assertEquals(true, Types.STRING.toBoolean("hello"));
    }

}