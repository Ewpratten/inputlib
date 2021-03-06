# InputLib 
[![Documentation](https://img.shields.io/badge/-documentation-blue)](https://ewpratten.retrylife.ca/inputlib) ![Build library](https://github.com/Ewpratten/inputlib/workflows/Build%20library/badge.svg)

InputLib is a small and easy-to-use Java library for working with text-based user inputs in a terminal. This library was originally built to drastically speed up my development time when working on those annoying "whats your name?" programs everyone seems to do in intro Computer Science courses. This repo contains "version 2", which is set up to handle more datatypes, has a faster parser (the old parser was ANTLR4-based), and provides a very simple interface.

## Using in your project

**Step 1.** Add the RetryLife maven server to your `build.gradle` file:

```groovy
repositories {
    maven { 
        name 'retrylife-release'
        url 'https://release.maven.retrylife.ca/' 
    }
}
```

**Step 2.** Add this library as a dependency:

```groovy
dependencies {
    implementation 'ca.retrylife:inputlib:1.+'
    implementation 'ca.retrylife:inputlib:1.+:sources'
    implementation 'ca.retrylife:inputlib:1.+:javadoc'
}
```

## Usage

With InputLib loaded into your project, usage is fairly simple. The library is centered around the [`ca.retrylife.inputlib.Prompt`](https://ewpratten.retrylife.ca/inputlib/ca/retrylife/inputlib/Prompt.html) class.

```java
import ca.retrylife.inputlib.Prompt;

Prompt myPrompt = new Prompt();
```

InputLib supports the following basic datatypes: `String`, `char`, `int`, `double`, `float`, `boolean`. Each of these have their respective "prompt function". To use these, simply call them, and pass in a message you want the user to see.

```java
// Prompt a String
String s = myPrompt.promptString("message");
// Prompt a char
char c = myPrompt.promptCharacter("message");
// Prompt an int
int i = myPrompt.promptInteger("message");
// Prompt a double
double d = myPrompt.promptDouble("message");
// Prompt a float
float f = myPrompt.promptFloat("message");
// Prompt a boolean
boolean b = myPrompt.promptBoolean("message");
```

Some of these prompt functions support "fancy inputs". These are some quality-of-life additions to the InputLib parser I occasionally use:

 - [`promptInteger`](https://ewpratten.retrylife.ca/inputlib/ca/retrylife/inputlib/Prompt.html#promptInteger(java.lang.String)) will accept binary and hex strings in the following format:
   - Binary: `0b0101101`
   - Hexadecimal: `0xdeadbeef`
 - [`promptInteger`](https://ewpratten.retrylife.ca/inputlib/ca/retrylife/inputlib/Prompt.html#promptInteger(java.lang.String)), [`promptDouble`](https://ewpratten.retrylife.ca/inputlib/ca/retrylife/inputlib/Prompt.html#promptDouble(java.lang.String)), and [`promptFloat`](https://ewpratten.retrylife.ca/inputlib/ca/retrylife/inputlib/Prompt.html#promptFloat(java.lang.String)) all accept underscores as separators, just like Java does.
 - [`promptFloat`](https://ewpratten.retrylife.ca/inputlib/ca/retrylife/inputlib/Prompt.html#promptFloat(java.lang.String)) allows the user to add the letter `f` as a suffix, just like Java does.
 - [`promptBoolean`](https://ewpratten.retrylife.ca/inputlib/ca/retrylife/inputlib/Prompt.html#promptBoolean(java.lang.String)) accepts the following inputs, and converts them to booleans:
   - `yes`
   - `no`
   - `true`
   - `false`
   - `accept`
   - `deny`

A few other functions exist for allowing a user to make a selection from a list:

```java
// Make the user pick one of many characters
char selection = myPrompt.promptCharacterSelection("Pick one", 'a', 'b', 'c');
// Make the user pick a number between two other numbers
int selection = myPrompt.promptIntegerRangeSelection​("Pick between", 1, 10);
// Make the user pick one of many integers
int selection = myPrompt.promptIntegerSelection​("Pick one", 1, 10, 100, 120);
// Make the user pick an item from a list
String preference = myPrompt.promptList("Which is better", "cats", "dogs", "nerf guns");
```

Finally, the [`promptMultiLineString​`](https://ewpratten.retrylife.ca/inputlib/ca/retrylife/inputlib/Prompt.html#promptMultiLineString(java.lang.String)) function can be used to get a string from the user that spans multiple lines of input.

## How to push a release

Pushing a release is simple. Clone this repo, go to master, and run:

```sh
git tag -a <version> -m "<message>"
git push origin <version>
```
