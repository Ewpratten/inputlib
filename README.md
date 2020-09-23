# InputLib

InputLib is a small and easy-to-use Java library for working with text-based user inputs in a terminal. This library was originally built to drastically speed up my development time when working on those annoying "whats your name?" programs everyone seems to do in intro Computer Science courses. This repo contains "version 2", which is set up to handle more datatypes, has a faster parser (the old parser was ANTLR4-based), and provides a very simple interface.

## Installation

Using this library with your program is quite simple. Here is a basic example for Gradle:


*Stap 1.* Declare this maven server the end of your repositories block in *build.gradle*

```groovy
repositories {
    ...
    maven { url 'https://ultralight.retrylife.ca:/maven' }
}
```

*Step 2.* Add this artifact as a dependency

```groovy
dependencies {
    implementation 'ca.retrylife:ewgui:<LATEST_VERSION>'
}
```

See [ultralight.retrylife.ca]() for up-to-date examples in all major buildsystems.

## Usage