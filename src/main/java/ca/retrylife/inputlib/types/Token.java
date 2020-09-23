package ca.retrylife.inputlib.types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A Token is a representation of an input token. This class automatically
 * handles type-conversion, so the user can just call the getters. Keep in mind
 * that all getters are marked @Nullable
 */
public class Token {

    // Internal values
    private final @Nonnull Types primaryType;
    private final @Nonnull Object raw;
    private final @Nullable String _str;
    private final @Nullable Character _char;
    private final @Nullable Integer _int;
    private final @Nullable Double _double;
    private final @Nullable Float _float;
    private final @Nullable Boolean _bool;

    /**
     * Create a Token from a String
     * 
     * @param s String value
     */
    public Token(String s) {
        this(Types.STRING, s);
    }

    /**
     * Create a Token from a Character
     * 
     * @param c Character value
     */
    public Token(Character c) {
        this(Types.CHARACTER, c);
    }

    /**
     * Create a Token from an Integer
     * 
     * @param i Integer value
     */
    public Token(Integer i) {
        this(Types.INTEGER, i);
    }

    /**
     * Create a Token from a Double
     * 
     * @param d Double value
     */
    public Token(Double d) {
        this(Types.DOUBLE, d);
    }

    /**
     * Create a Token from a Float
     * 
     * @param f Float value
     */
    public Token(Float f) {
        this(Types.FLOAT, f);
    }

    /**
     * Create a Token from a Boolean
     * 
     * @param b Boolean value
     */
    public Token(Boolean b) {
        this(Types.BOOLEAN, b);
    }

    /**
     * Create a Token
     * 
     * @param parsedType Value Type
     * @param value      Value
     */
    public Token(@Nonnull Types parsedType, @Nonnull Object value) {

        // Set primary type
        this.primaryType = parsedType;
        this.raw = value;

        // String
        if (parsedType.canConvertTo(Types.STRING)) {
            this._str = parsedType.toString(value);
        } else {
            this._str = null;
        }

        // Character
        if (parsedType.canConvertTo(Types.CHARACTER)) {
            this._char = parsedType.toCharacter(value);
        } else {
            this._char = null;
        }

        // Integer
        if (parsedType.canConvertTo(Types.INTEGER)) {
            this._int = parsedType.toInteger(value);
        } else {
            this._int = null;
        }

        // Double
        if (parsedType.canConvertTo(Types.DOUBLE)) {
            this._double = parsedType.toDouble(value);
        } else {
            this._double = null;
        }

        // Float
        if (parsedType.canConvertTo(Types.FLOAT)) {
            this._float = parsedType.toFloat(value);
        } else {
            this._float = null;
        }

        // Boolean
        if (parsedType.canConvertTo(Types.BOOLEAN)) {
            this._bool = parsedType.toBoolean(value);
        } else {
            this._bool = null;
        }
    }

    /**
     * Check if this token has its value as a specific type
     * 
     * @param t Type
     * @return Can get value as this type
     */
    public boolean hasType(Types t) {
        return primaryType.canConvertTo(t);
    }

    /**
     * Gets the type of the original data. This is generally only for advanced use
     * 
     * @return Primary type of the Token
     */
    public Types getPrimaryType() {
        return primaryType;
    }

    /**
     * Get the token's value as a String (Nullable)
     * 
     * @return String or null
     */
    public @Nullable String getString() {
        return _str;
    }

    /**
     * Get the token's value as a Character (Nullable)
     * 
     * @return Character or null
     */
    public @Nullable Character getCharacter() {
        return _char;
    }

    /**
     * Get the token's value as a Integer (Nullable)
     * 
     * @return Integer or null
     */
    public @Nullable Integer getInteger() {
        return _int;
    }

    /**
     * Get the token's value as a Double (Nullable)
     * 
     * @return Double or null
     */
    public @Nullable Double getDouble() {
        return _double;
    }

    /**
     * Get the token's value as a Float (Nullable)
     * 
     * @return Float or null
     */
    public @Nullable Float getFloat() {
        return _float;
    }

    /**
     * Get the token's value as a Boolean (Nullable)
     * 
     * @return Boolean or null
     */
    public @Nullable Boolean getBoolean() {
        return _bool;
    }

    @Override
    public String toString() {
        return String.format("Token<prim: %s, val: %s>", this.primaryType, this.raw);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) {
            return false;
        }
        return obj == this || (((Token) obj).getString() == getString() && ((Token) obj).getInteger() == getInteger()
                && ((Token) obj).getCharacter() == getCharacter() && ((Token) obj).getDouble() == getDouble()
                && ((Token) obj).getFloat() == getFloat() && ((Token) obj).getBoolean() == getBoolean());
    }

}