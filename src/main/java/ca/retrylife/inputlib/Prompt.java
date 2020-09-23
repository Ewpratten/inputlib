package ca.retrylife.inputlib;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import ca.retrylife.inputlib.types.Token;
import ca.retrylife.inputlib.types.Types;

/**
 * Prompt is the primary class of InputLib. All general functions are provided
 * here, and it wraps many annoying type-safety checks in simple functions.
 */
public class Prompt {

    // IO
    private Scanner in;
    private PrintStream out;

    /**
     * Create a Prompt using stdin/stdout for I/O
     */
    public Prompt() {
        this(System.in, System.out);
    }

    /**
     * Create a Prompt from two streams
     * 
     * @param in  Input
     * @param out Output
     */
    public Prompt(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * (Advanced Use Only) Get a raw parser token from the user
     * 
     * @param prompt Prompt message
     * @return Raw token
     */
    public Token promptToken(String prompt) {

        // Print prompt
        out.print(prompt);
        out.print("> ");

        // Get a line
        String line = in.nextLine();

        // Parse the data into a token
        return Parser.parseToToken(line);
    }

    /**
     * Get a String from the user
     * 
     * @param prompt  Prompt message
     * @param oneLine Should this be printed on a single line?
     * @return User-supplied string
     */
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

    /**
     * Get a String from the user
     * 
     * @param prompt Prompt message
     * @return User-supplied string
     */
    public String promptString(String prompt) {
        return promptString(prompt, false);
    }

    /**
     * Get a string spanning multiple lines from the user. This uses an ED-like
     * interface, and tells the user how to use it
     * 
     * @param prompt Prompt message
     * @return User-supplied string
     */
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

    /**
     * Prompt a single character from the user
     * 
     * @param prompt  Prompt message
     * @param oneLine Should this be printed on a single line?
     * @return User-supplied character
     */
    public char promptCharacter(String prompt, boolean oneLine) {
        return promptString(prompt, oneLine).charAt(0);
    }

    /**
     * Prompt a single character from the user
     * 
     * @param prompt Prompt message
     * @return User-supplied character
     */
    public char promptCharacter(String prompt) {
        return promptCharacter(prompt, false);
    }

    /**
     * Prompt the user to pick one of the allowed characters
     * 
     * @param prompt       Prompt message
     * @param allowedChars Allowed characters
     * @return User-supplied character
     */
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

    /**
     * Prompt an integer from the user (they can enter decimal, binary, or
     * hexadecimal)
     * 
     * @param prompt  Prompt message
     * @param oneLine Should this be printed on a single line?
     * @return User-supplied integer
     */
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

    /**
     * Prompt an integer from the user (they can enter decimal, binary, or
     * hexadecimal)
     * 
     * @param prompt Prompt message
     * @return User-supplied integer
     */
    public int promptInteger(String prompt) {
        return promptInteger(prompt, false);
    }

    /**
     * Prompt the user to select one of multiple allowed integers
     * 
     * @param prompt      Prompt message
     * @param allowedInts Allowed integers for the user to pick
     * @return User-supplied integer
     */
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

    /**
     * Prompt the user to pick an integer between (and including) the min and max
     * number
     * 
     * @param prompt Prompt message
     * @param min    Minimum number
     * @param max    Maximum number
     * @return User-supplied integer
     */
    public int promptIntegerRangeSelection(String prompt, int min, int max) {

        // Get an int until an allowed int is found
        while (true) {

            // Get input
            int input = promptInteger(String.format("%s%nselect [%d...%d] ", prompt, min, max), true);

            // Check if the input is valid
            if (min <= input && input <= max) {
                return input;
            }

            // Handle no valid input
            out.println("! Invalid selection");
        }
    }

    /**
     * Prompt the user to pick from a list of items
     * 
     * @param title   Title of the list
     * @param options Options
     * @return The selected array element
     */
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

    /**
     * Prompt a double from the user
     * 
     * @param prompt  Prompt message
     * @param oneLine Should this be printed on a single line?
     * @return User-supplied double
     */
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

    /**
     * Prompt a double from the user
     * 
     * @param prompt Prompt message
     * @return User-supplied double
     */
    public double promptDouble(String prompt) {
        return promptDouble(prompt, false);
    }

    /**
     * Prompt a float from the user (they can optionally add an "f" to the end of
     * their input)
     * 
     * @param prompt  Prompt message
     * @param oneLine Should this be printed on a single line?
     * @return User-supplied float
     */
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

    /**
     * Prompt a float from the user (they can optionally add an "f" to the end of
     * their input)
     * 
     * @param prompt Prompt message
     * @return User-supplied float
     */
    public float promptFloat(String prompt) {
        return promptFloat(prompt, false);
    }

    /**
     * Prompt a boolean from the user (This can be one of: y, n, yes, no, t, f,
     * true, false, accept, deny)
     * 
     * @param prompt  Prompt message
     * @param oneLine Should this be printed on a single line?
     * @return User-supplied boolean
     */
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

    /**
     * Prompt a boolean from the user (This can be one of: y, n, yes, no, t, f,
     * true, false, accept, deny)
     * 
     * @param prompt Prompt message
     * @return User-supplied boolean
     */
    public boolean promptBoolean(String prompt) {
        return promptBoolean(prompt, false);
    }

}