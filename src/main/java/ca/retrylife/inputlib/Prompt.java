package ca.retrylife.inputlib;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import ca.retrylife.inputlib.types.Token;
import ca.retrylife.inputlib.types.Types;

public class Prompt {

    // IO
    private Scanner in;
    private PrintStream out;

    public Prompt() {
        this(System.in, System.out);
    }

    public Prompt(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public Token promptToken(String prompt) {

        // Print prompt
        out.print(prompt);
        out.print("> ");

        // Get a line
        String line = in.nextLine();

        // Parse the data into a token
        return parseToToken(line);
    }

    public String promptString(String prompt, boolean oneLine) {

        // Get a token, and its string until the data is valid
        while (true) {

            // Get input
            Token input = promptToken(String.format(((oneLine) ? "%s" : "%s%n"), prompt));

            // If the input is valid, return
            if (input.hasType(Types.STRING) && input.getString().length() > 0) {
                return input.getString();
            }
        }
    }

    public String promptString(String prompt) {
        return promptString(prompt, false);
    }

    public char promptCharacter(String prompt, boolean oneLine) {
        return promptString(prompt, oneLine).charAt(0);
    }

    public char promptCharacter(String prompt) {
        return promptCharacter(prompt, false);
    }

    public char promptCharacterSelection(String prompt, char... allowedChars) {

        // Build the allowed chars list into a nice string
        StringBuilder allowedCharsString = new StringBuilder();

        for (int i = 0; i < allowedChars.length; i++) {

            // Add char to list
            allowedCharsString.append(allowedChars[i]);

            // If not the last char, add a comma
            if (i < allowedChars.length - 1) {
                allowedCharsString.append(",");
            }
        }

        // Get a char until an allowed char is found
        while (true) {

            // Get input
            char input = promptCharacter(String.format("%s%none of [%s] ", allowedCharsString.toString()), true);

            // Check if the input is valid
            for (char c : allowedChars) {
                if (input == c) {
                    return input;
                }
            }

            // Handle no valid input
            out.println("! Invalid selection");
        }
    }

    public int promptInteger(String prompt, boolean oneLine) {

        // Get a token, and its value until the data is valid
        while (true) {

            // Get input
            Token input = promptToken(String.format(((oneLine) ? "%s" : "%s%n"), prompt));

            // If the input is valid, return
            if (input.hasType(Types.INTEGER) && input.getInteger() != null) {
                return input.getInteger();
            }

            // Warn the user
            out.println("! Must be an integer");
        }
    }

    public int promptInteger(String prompt) {
        return promptInteger(prompt, false);
    }

    public int promptIntegerSelection(String prompt, int... allowedInts) {

        // Build the allowed ints list into a nice string
        StringBuilder allowedIntsString = new StringBuilder();

        for (int i = 0; i < allowedInts.length; i++) {

            // Add int to list
            allowedIntsString.append(allowedInts[i]);

            // If not the last int, add a comma
            if (i < allowedInts.length - 1) {
                allowedIntsString.append(",");
            }
        }

        // Get an int until an allowed int is found
        while (true) {

            // Get input
            int input = promptCharacter(String.format("%s%none of [%s] ", allowedIntsString.toString()), true);

            // Check if the input is valid
            for (int i : allowedInts) {
                if (input == i) {
                    return input;
                }
            }

            // Handle no valid input
            out.println("! Invalid selection");
        }
    }

    public int promptIntegerRangeSelection(String prompt, int min, int max) {

        // Get an int until an allowed int is found
        while (true) {

            // Get input
            int input = promptCharacter(String.format("%s%nselect [%d...%d] ", min, max), true);

            // Check if the input is valid
            if (min <= input && input <= max) {
                return input;
            }

            // Handle no valid input
            out.println("! Invalid selection");
        }
    }

    private Token parseToToken(String data) {
        return null;
    }

}