package ca.retrylife.inputlib.types;

import java.util.HashMap;
import java.util.function.Function;

/**
 * Types is an enum containing a list of (mostly) primitive Java types, and many
 * functions to convert between them all.
 * 
 * Example: Types.BOOLEAN.toCharacter(true) == 'T'
 */
public enum Types {

    // String type
    STRING("String", String.class),

    // Character type
    CHARACTER("Character", Character.class),

    // Integer type
    INTEGER("Integer", Integer.class),

    // Double type
    DOUBLE("Double", Double.class),

    // Float type
    FLOAT("Float", Float.class),

    // Boolean type
    BOOLEAN("Boolean", Boolean.class);

    // Configure all the conversions
    static {

        // String -> Character
        STRING.addConversion(CHARACTER, (s) -> {

            // Ensure the string exists
            if (s != null && ((String) s).length() > 0) {

                // Return the first char
                return ((String) s).charAt(0);
            } else {

                // Return nothing
                return null;
            }
        });

        // String -> Boolean
        STRING.addConversion(BOOLEAN, (s) -> {

            // Return true if the string exists
            return s != null && ((String) s).length() > 0;
        });

        // Character -> String
        CHARACTER.addConversion(STRING, (c) -> {

            // Make sure C is not null
            if (c != null) {
                return c.toString();
            } else {
                return null;
            }
        });

        // Character -> Integer
        CHARACTER.addConversion(INTEGER, (c) -> {

            // Make sure C is not null
            if (c != null) {

                // Cast the char up to an int
                return (Integer) (int) ((Character) c).charValue();
            } else {
                return null;
            }
        });

        // Character -> Double
        CHARACTER.addConversion(DOUBLE, (c) -> {

            // Make sure C is not null
            if (c != null) {

                // Cast the char up to a double
                return (Double) (double) (int) ((Character) c).charValue();
            } else {
                return null;
            }
        });

        // Character -> Float
        CHARACTER.addConversion(FLOAT, (c) -> {

            // Make sure C is not null
            if (c != null) {

                // Cast the char up to a float
                return (Float) (float) (int) ((Character) c).charValue();
            } else {
                return null;
            }
        });

        // Character -> Boolean
        CHARACTER.addConversion(BOOLEAN, (c) -> {
            return c != null && Character.isDefined((Character) c) && !((Character) c).equals('0');
        });

        // Integer -> String
        INTEGER.addConversion(STRING, (i) -> {
            return i.toString();
        });

        // Integer -> Character
        INTEGER.addConversion(CHARACTER, (i) -> {

            // Make sure I is not null
            if (i != null) {

                // Cast the int to a char
                return (Character) (char) ((Integer) i).intValue();
            } else {
                return null;
            }
        });

        // Integer -> Double
        INTEGER.addConversion(DOUBLE, (i) -> {
            return (Double) ((Integer) i).doubleValue();
        });

        // Integer -> Float
        INTEGER.addConversion(FLOAT, (i) -> {
            return (Float) ((Integer) i).floatValue();
        });

        // Integer -> Boolean
        INTEGER.addConversion(BOOLEAN, (i) -> {
            return i != null && ((Integer) i) != 0;
        });

        // Double -> String
        DOUBLE.addConversion(STRING, (d) -> {
            return d.toString();
        });

        // Double -> Character
        DOUBLE.addConversion(CHARACTER, (d) -> {

            // Make sure D is not null
            if (d != null) {

                // Cast the double to a character
                return (Character) (char) ((Double) d).intValue();
            } else {
                return null;
            }
        });

        // Double -> Integer
        DOUBLE.addConversion(INTEGER, (d) -> {
            return (Integer) ((Double) d).intValue();
        });

        // Double -> Float
        DOUBLE.addConversion(FLOAT, (d) -> {
            return (Float) ((Double) d).floatValue();
        });

        // Double -> Boolean
        DOUBLE.addConversion(BOOLEAN, (d) -> {
            return d != null && !((Double) d).isNaN() && !((Double) d).equals(0.0);
        });

        // Float -> String
        FLOAT.addConversion(STRING, (f) -> {
            return f.toString();
        });

        // Float -> Character
        FLOAT.addConversion(CHARACTER, (f) -> {

            // Make sure F is not null
            if (f != null) {

                // Cast the float to a character
                return (Character) (char) ((Float) f).intValue();
            } else {
                return null;
            }
        });

        // Float -> Integer
        FLOAT.addConversion(INTEGER, (f) -> {
            return (Integer) ((Float) f).intValue();
        });

        // Float -> Double
        FLOAT.addConversion(DOUBLE, (f) -> {
            return (Double) ((Float) f).doubleValue();
        });

        // Float -> Boolean
        FLOAT.addConversion(BOOLEAN, (f) -> {
            return f != null && !((Float) f).isNaN() && !((Float) f).equals(0.0f);
        });

        // Boolean -> String
        BOOLEAN.addConversion(STRING, (b) -> {
            return b.toString();
        });

        // Boolean -> Character
        BOOLEAN.addConversion(CHARACTER, (b) -> {

            // Handle B's state
            if (b != null && ((Boolean) b)) {
                return 'T';
            } else {
                return 'f';
            }
        });

        // Boolean -> Int
        BOOLEAN.addConversion(INTEGER, (b) -> {
            // Handle B's state
            if (b != null && ((Boolean) b)) {
                return 1;
            } else {
                return 0;
            }
        });

        // Boolean -> Double
        BOOLEAN.addConversion(DOUBLE, (b) -> {

            // Use pre-defined conversions to handle this
            return INTEGER.toDouble(BOOLEAN.toInteger(b));
        });

        // Boolean -> Float
        BOOLEAN.addConversion(FLOAT, (b) -> {

            // Use pre-defined conversions to handle this
            return INTEGER.toFloat(BOOLEAN.toInteger(b));
        });

    }

    // Data about self
    private final String name;
    public final String ID;
    private final Class clazz;

    // Converter map
    private HashMap<Types, Function<Object, Object>> converterMap;

    /**
     * Create a type
     * 
     * @param name  Unique type name
     * @param clazz Type class
     */
    private Types(String name, Class clazz) {
        this.name = name;
        this.ID = this.name;
        this.clazz = clazz;
        this.converterMap = new HashMap<>();
    }

    /**
     * Add a conversion for the type
     * 
     * @param t         Type to convert to
     * @param converter Function to handle conversion
     */
    private void addConversion(Types t, Function<Object, Object> converter) {
        this.converterMap.put(t, converter);
    }

    /**
     * Check if this is a specific type
     * 
     * @param t Type to check if this is
     * @return Is this type the same?
     */
    public boolean isType(Types t) {
        return getName().equals(t.getName());
    }

    /**
     * Get this type's name
     * 
     * @return name;
     */
    public String getName() {
        return name;
    }

    /**
     * Check if this type can be converted to another type
     * 
     * @param t Type to convert to
     * @return Can be converted?
     */
    public boolean canConvertTo(Types t) {
        return this.converterMap.containsKey(t) || isType(t);
    }

    /**
     * Get the internal converter for a specific type
     * 
     * @param t Type to convert to
     * @return Converter
     */
    public Function<Object, Object> getConverterFor(Types t) {
        return this.converterMap.get(t);
    }

    /**
     * Convert a value of this type to a String
     * 
     * @param value Value of this type
     * @return String for that value
     */
    public String toString(Object value) {
        if (isType(STRING)) {
            // Handle conversion to self
            return (String) value;
        } else if (canConvertTo(STRING)) {
            // Handle using a converter
            return (String) getConverterFor(STRING).apply(value);
        } else {
            // Handle an impossible conversion
            throw new RuntimeException(
                    String.format("Type %s cannot convert directly to type %s", getName(), STRING.getName()));
        }
    }

    /**
     * Convert a value of this type to a Character
     * 
     * @param value Value of this type
     * @return Character for that value
     */
    public Character toCharacter(Object value) {
        if (isType(CHARACTER)) {
            // Handle conversion to self
            return (Character) value;
        } else if (canConvertTo(CHARACTER)) {
            // Handle using a converter
            return (Character) getConverterFor(CHARACTER).apply(value);
        } else {
            // Handle an impossible conversion
            throw new RuntimeException(
                    String.format("Type %s cannot convert directly to type %s", getName(), CHARACTER.getName()));
        }
    }

    /**
     * Convert a value of this type to an Integer
     * 
     * @param value Value of this type
     * @return Integer for that value
     */
    public Integer toInteger(Object value) {
        if (isType(INTEGER)) {
            // Handle conversion to self
            return (Integer) value;
        } else if (canConvertTo(INTEGER)) {
            // Handle using a converter
            return (Integer) getConverterFor(INTEGER).apply(value);
        } else {
            // Handle an impossible conversion
            throw new RuntimeException(
                    String.format("Type %s cannot convert directly to type %s", getName(), INTEGER.getName()));
        }
    }

    /**
     * Convert a value of this type to a Double
     * 
     * @param value Value of this type
     * @return Double for that value
     */
    public Double toDouble(Object value) {
        if (isType(DOUBLE)) {
            // Handle conversion to self
            return (Double) value;
        } else if (canConvertTo(DOUBLE)) {
            // Handle using a converter
            return (Double) getConverterFor(DOUBLE).apply(value);
        } else {
            // Handle an impossible conversion
            throw new RuntimeException(
                    String.format("Type %s cannot convert directly to type %s", getName(), DOUBLE.getName()));
        }
    }

    /**
     * Convert a value of this type to a Float
     * 
     * @param value Value of this type
     * @return Float for that value
     */
    public Float toFloat(Object value) {
        if (isType(FLOAT)) {
            // Handle conversion to self
            return (Float) value;
        } else if (canConvertTo(FLOAT)) {
            // Handle using a converter
            return (Float) getConverterFor(FLOAT).apply(value);
        } else {
            // Handle an impossible conversion
            throw new RuntimeException(
                    String.format("Type %s cannot convert directly to type %s", getName(), FLOAT.getName()));
        }
    }

    /**
     * Convert a value of this type to a Boolean
     * 
     * @param value Value of this type
     * @return Boolean for that value
     */
    public Boolean toBoolean(Object value) {
        if (isType(BOOLEAN)) {
            // Handle conversion to self
            return (Boolean) value;
        } else if (canConvertTo(BOOLEAN)) {
            // Handle using a converter
            return (Boolean) getConverterFor(BOOLEAN).apply(value);
        } else {
            // Handle an impossible conversion
            throw new RuntimeException(
                    String.format("Type %s cannot convert directly to type %s", getName(), BOOLEAN.getName()));
        }
    }
}