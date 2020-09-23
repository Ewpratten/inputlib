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
        return Parser.parseToToken(line);
    }

    public String promptString(String prompt, boolean oneLine) {

        // Get a token, and its string until the data is valid
        while (true) {

            // Get input
            Token input = promptToken(String.format(((oneLine) ? "%s" : "%s%n"), prompt));

            // If the input is valid, return
            if (input != null && input.hasType(Types.STRING) && input.getString().length() > 0) {
                return input.getString();
            }
        }
    }

    public String promptString(String prompt) {
        return promptString(prompt, false);
    }

    public String promptMultiLineString(String prompt) {

        // Print the user prompt
        out.println(prompt);

        // Print a message explaining how to use this
        out.println("When finished, enter \".\" on a new line.");

        // Handle line reading
        StringBuilder totalInput = new StringBuilder();
        while (true) {

            // Get a line of input
            Token input = promptToken("");

            // Handle the line type
            if (input.hasType(Types.STRING)) {

                // Check if this is the last line
                if (input.getString().equals(".")) {
                    break;
                }

                // Add the line to the total input
                totalInput.append(input.getString());
            }

            // Move to next line
            totalInput.append("\n");

        }

        // Return the built string
        return totalInput.toString();
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
            if (input != null && input.hasType(Types.INTEGER) && input.getInteger() != null) {
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
            int input = promptInteger(String.format("%s%none of [%s] ", prompt, allowedIntsString.toString()), true);

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
            int input = promptInteger(String.format("%s%nselect [%d...%d] ",prompt, min, max), true);

            // Check if the input is valid
            if (min <= input && input <= max) {
                return input;
            }

            // Handle no valid input
            out.println("! Invalid selection");
        }
    }

    public String promptList(String title, String... options) {

        // Build the options list
        StringBuilder prompt = new StringBuilder();

        // Add the title
        prompt.append(title);
        prompt.append(String.format(":%n"));

        // Add every option
        for (int i = 0; i < options.length; i++) {
            prompt.append(String.format("  %d: %s%n", i, options[i]));
        }

        // Prompt for a range, and select the matching array element
        return options[promptIntegerRangeSelection(prompt.toString(), 0, options.length - 1)];
    }

    public double promptDouble(String prompt, boolean oneLine) {

        // Get a token, and its value until the data is valid
        while (true) {

            // Get input
            Token input = promptToken(String.format(((oneLine) ? "%s" : "%s%n"), prompt));

            // If the input is valid, return
            if (input != null && input.hasType(Types.DOUBLE) && input.getDouble() != null) {
                return input.getDouble();
            }

            // Warn the user
            out.println("! Must be a number");
        }
    }

    public double promptDouble(String prompt) {
        return promptDouble(prompt, false);
    }

    public float promptFloat(String prompt, boolean oneLine) {

        // Get a token, and its value until the data is valid
        while (true) {

            // Get input
            Token input = promptToken(String.format(((oneLine) ? "%s" : "%s%n"), prompt));

            // If the input is valid, return
            if (input != null && input.hasType(Types.FLOAT) && input.getFloat() != null) {
                return input.getFloat();
            }

            // Warn the user
            out.println("! Must be a number");
        }
    }

    public float promptFloat(String prompt) {
        return promptFloat(prompt, false);
    }

    public boolean promptBoolean(String prompt, boolean oneLine) {

        // Get a token, and its value until the data is valid
        while (true) {

            // Get input
            Token input = promptToken(String.format(((oneLine) ? "%s" : "%s%n"), prompt));

            // If the input is valid, return
            if (input != null && input.hasType(Types.BOOLEAN) && input.getBoolean() != null) {
                return input.getBoolean();
            }

            // Warn the user
            out.println("! Must be a boolean");
        }
    }

    public boolean promptBoolean(String prompt) {
        return promptBoolean(prompt, false);
    }

}