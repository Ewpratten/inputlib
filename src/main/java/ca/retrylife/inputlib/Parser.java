package ca.retrylife.inputlib;

import java.util.HashMap;

import ca.retrylife.inputlib.types.Token;
import ca.retrylife.inputlib.types.Types;

/**
 * Utils for parsing strings
 */
public class Parser {

    // List of every valid boolean string
    public static HashMap<String, Boolean> VALID_BOOLEANS_MAP = new HashMap<>();

    static {
        VALID_BOOLEANS_MAP.put("yes", true);
        VALID_BOOLEANS_MAP.put("no", false);
        VALID_BOOLEANS_MAP.put("true", true);
        VALID_BOOLEANS_MAP.put("false", false);
        VALID_BOOLEANS_MAP.put("accept", true);
        VALID_BOOLEANS_MAP.put("deny", false);
    }

    /**
     * Parses a String into a Token
     * 
     * @param data Data String
     * @return Parsed Token
     */
    public static Token parseToToken(String data) {

        // Try to determine the type of data being passed in
        // The next few steps will try to eliminate various options based on some simple
        // rules
        Types type = null;

        // Number types
        boolean isHex = false;
        boolean isBinary = false;

        // New scope
        typeFinder: {
            boolean isNumber = true;
            boolean isFloat = false;
            boolean isDouble = false;

            // If the data is empty, skip this whole thing
            if (data.length() == 0) {
                type = Types.STRING;
                break typeFinder;
            }

            // Check if the data fits in the valid boolean list
            boolean mightBeBoolean = false;
            for (String validBooleanString : VALID_BOOLEANS_MAP.keySet()) {
                if (data.equals(validBooleanString)) {
                    mightBeBoolean = true;
                    break;
                }
            }

            // Search through the data
            for (char c : data.toCharArray()) {

                // If a space or a newline is found, this must be a string
                if (c == ' ' || c == '\n') {
                    type = Types.STRING;
                    break typeFinder;
                }

                // If a character that is not valid in a number is found, remove possibility of
                // number
                if (!isHex && !Character.isDigit(c) && Character.toLowerCase(c) != '_'
                        && Character.toLowerCase(c) != 'f' && Character.toLowerCase(c) != 'x'
                        && Character.toLowerCase(c) != 'b' && Character.toLowerCase(c) != '.') {
                    isNumber = false;
                }

                // Check for a character that might make this a float
                if (Character.toLowerCase(c) == 'f') {
                    isFloat = true;
                }

                // Check for a character that might make this a double
                if (Character.toLowerCase(c) == '.') {
                    isDouble = true;
                }

                // Check for a character that would make this a hex number
                if (Character.toLowerCase(c) == 'x') {
                    isHex = true;
                }

                // Check for a character that would make this a binary number
                if (Character.toLowerCase(c) == 'b') {
                    isBinary = true;
                }

            }

            // There is an edge case where the number chars are interpreted as strings when
            // they should be chars.
            if (data.length() == 1) {
                String[] issueChars = new String[] { "_", "x", "b", "." };
                for (String ic : issueChars) {
                    if (data.toLowerCase().equals(ic)) {
                        type = Types.STRING;
                        break typeFinder;
                    }
                }
            }

            // Use logic to determine the type
            if (data.length() == 1) {
                if (mightBeBoolean) {
                    type = Types.BOOLEAN;
                } else if (isNumber) {
                    if (isFloat && !isHex) {
                        type = Types.FLOAT;
                    } else if (isDouble) {
                        type = Types.DOUBLE;
                    } else {
                        type = Types.INTEGER;
                    }
                } else {
                    type = Types.CHARACTER;
                }
            } else {
                if (mightBeBoolean) {
                    type = Types.BOOLEAN;
                } else {
                    if (isNumber) {
                        if (isFloat && !isHex) {
                            type = Types.FLOAT;
                        } else if (isDouble) {
                            type = Types.DOUBLE;
                        } else {
                            type = Types.INTEGER;
                        }
                    } else {
                        type = Types.STRING;
                    }
                }
            }

        }

        // Clean up the data according to its type
        switch (type) {
            case STRING:
                return new Token(data);
            case CHARACTER:
                return new Token(data.charAt(0));
            case INTEGER:
                // Determine the base
                int base = ((isBinary) ? 2 : ((isHex) ? 16 : 10));

                // Handle base 10
                if (base == 10) {
                    return new Token(Integer.parseInt(data, base));
                } else {
                    // Base 2 and 16 require a stripped prefix
                    return new Token(Integer.parseInt(data.substring(2), base));
                }
            case DOUBLE:
                // Handles a java parser bug
                return new Token(Double.parseDouble(data.replaceAll("_", "")));
            case FLOAT:
                // Handles a java parser bug
                return new Token(Float.parseFloat(data.replaceAll("_", "").replaceAll("f", "")));
            case BOOLEAN:
                return new Token(VALID_BOOLEANS_MAP.get(data.toLowerCase()));
        }

        return null;
    }
}