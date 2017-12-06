Any demo programs should go into this directory. You don't need to use Gradle
to create the demo programs. (They should be small examples of library use.)
Just use the Java compiler and VM as normal.

To compile and run any demo program, you should do so against the built JAR file in the `build/libs` directory.

`javac -cp .:../build/libs/argparse-<version>.jar MyDemoProgram1.java`
`java -cp .:../build/libs/argparse-<version>.jar MyDemoProgram`

