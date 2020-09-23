package ca.retrylife.inputlib.types;

import java.util.HashMap;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Types {

    // String type
    public static Types STRING = new Types("String", String.class);

    // Character type
    public static Types CHARACTER = new Types("Character", Character.class);

    // Integer type
    public static Types INTEGER = new Types("Integer", Integer.class);

    // Double type
    public static Types DOUBLE = new Types("Double", Double.class);

    // Float type
    public static Types FLOAT = new Types("Float", Float.class);

    // Boolean type
    public static Types BOOLEAN = new Types("Boolean", Boolean.class);

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
            return c != null && Character.isDefined((Character) c);
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
    private final Class clazz;

    // Converter map
    private HashMap<Types, Function<@Nonnull Object, @Nullable Object>> converterMap;

    private Types(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
        this.converterMap = new HashMap<>();
    }

    private void addConversion(Types t, Function<@Nonnull Object, @Nullable Object> converter) {
        this.converterMap.put(t, converter);
    }

    public boolean isType(Types t) {
        return getName().equals(t.getName());
    }

    public String getName() {
        return name;
    }

    public boolean canConvertTo(Types t) {
        return this.converterMap.containsKey(t);
    }

    public Function<@Nonnull Object, @Nullable Object> getConverterFor(Types t) {
        return this.converterMap.get(t);
    }

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