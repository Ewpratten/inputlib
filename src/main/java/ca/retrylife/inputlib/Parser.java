package ca.retrylife.inputlib;

import java.util.HashMap;

import ca.retrylife.inputlib.types.Token;
import ca.retrylife.inputlib.types.Types;

/**
 * Utils for parsing strings
 */
public class Parser {

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

        // List of every valid boolean string
        HashMap<String, Boolean> validBooleanStrings = new HashMap<>();
        validBooleanStrings.put("y", true);
        validBooleanStrings.put("n", false);
        validBooleanStrings.put("yes", true);
        validBooleanStrings.put("no", false);
        validBooleanStrings.put("t", true);
        validBooleanStrings.put("f", false);
        validBooleanStrings.put("true", true);
        validBooleanStrings.put("false", false);
        validBooleanStrings.put("accept", true);
        validBooleanStrings.put("deny", false);

        // Number types
        boolean isHex = false;
        boolean isBinary = false;

        // New scope
        typeFinder: {
            boolean isNumber = true;
            boolean isFloat = false;
            boolean isDouble = false;

            // Check if the data fits in the valid boolean list
            boolean mightBeBoolean = false;
            for (String validBooleanString : validBooleanStrings.keySet()) {
                if (data.equals(validBooleanString)) {
                    mightBeBoolean = true;
                    break;
                }
            }

            // Hex, binary, double, and float cannot co-exist
            // THis is used to track what is "active"
            int numberModifiers = 0;

            // Search through the data
            for (char c : data.toCharArray()) {

                // If a space or a newline is found, this must be a string
                if (c == ' ' || c == '\n') {
                    type = Types.STRING;
                    break typeFinder;
                }

                // If a character that is not valid in a number is found, remove possibility of
                // number
                if (!Character.isDigit(c) && c != '_' && c != 'f' && c != 'x' && c != 'b' && c != '.') {
                    isNumber = false;
                }

                // Check for a character that might make this a float
                if (c == 'f') {
                    isFloat = true;
                    numberModifiers += 1;
                }

                // Check for a character that might make this a double
                if (c == '.') {
                    isDouble = true;
                }

                // Check for a character that would make this a hex number
                if (c == 'x') {
                    isHex = true;
                    numberModifiers += 1;
                }

                // Check for a character that would make this a binary number
                if (c == 'b') {
                    isBinary = true;
                    numberModifiers += 1;
                }

                // Handle having multiple number modifiers
                if (numberModifiers > 1) {
                    isNumber = false;
                }
                if (isHex && numberModifiers == 1) {
                    isNumber = false;
                }

            }

            // Use logic to determine the type
            if (data.length() == 1) {
                if (mightBeBoolean) {
                    type = Types.BOOLEAN;
                } else {
                    type = Types.CHARACTER;
                }
            } else {
                if (mightBeBoolean) {
                    type = Types.BOOLEAN;
                } else {
                    if (isNumber) {
                        if (isFloat) {
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
                return new Token(Integer.parseInt(data, (isBinary) ? 2 : (isHex) ? 16 : 10));
            case DOUBLE:
                // Handles a java parser bug
                return new Token(Double.parseDouble(data.replaceAll("_", "")));
            case FLOAT:
                // Handles a java parser bug
                return new Token(Float.parseFloat(data.replaceAll("_", "").replaceAll("f", "")));
            case BOOLEAN:
                return new Token(validBooleanStrings.get(data));
        }

        return null;
    }
}