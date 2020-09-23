package ca.retrylife.inputlib.examples;

import ca.retrylife.inputlib.Prompt;

public class Example {

    public static void main(String[] args) {

        // Create a prompt for this program
        Prompt input = new Prompt();

        // Ask the user for their name
        String name = input.promptString("Hello, what is your name?");

        // Compliment them
        System.out.println(String.format("%s is a nice name!", name));

        // Ask the user to input their favorite number between 1 and 10
        int favNumber = input.promptIntegerRangeSelection("What is your favorite number between 1 and 10?", 1, 10);

        // Give a useless fact
        boolean theyKnow = input
                .promptBoolean(String.format("Did you know that %dx2 is %d?", favNumber, favNumber * 2));

        // Handle them knowing or not
        if (theyKnow) {
            System.out.println("I figured. You seem pretty smart");
        } else {
            System.out.println("You learn something new every day!");
        }

        // Ask a final question
        String theirResponse = input.promptMultiLineString("What did you think of this example program?");

        // Print all their answers
        System.out.println("-----");
        System.out.println("Your answers");
        System.out.println(String.format("Name: %s", name));
        System.out.println(String.format("Number: %d", favNumber));
        System.out.println(String.format("Learned Something?: %s", (theyKnow) ? "yes" : "no"));
        System.out.println(String.format("Thoughts: %n%s", theirResponse));
        System.out.println("-----");

        System.out.println("Thank you for checking this out!");

    }

}