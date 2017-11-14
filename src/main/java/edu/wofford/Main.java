package edu.wofford;

public class Main {

	public static void main(String[] args) {

		System.out.println("This is not an executable JAR file.");
		System.out.println("To use this library, you need to add the JAR file to the");
		System.out.println("CLASSPATH when you compile and run code that uses it.");
		System.out.println();
		System.out.println("Example: javac -cp .:my/jars/path/thisjar.jar MyClientProgram.java");
		System.out.println("Example: java -cp .:my/jars/path/thisjar.jar MyClientProgram");
		System.out.println();
		System.out.println("You can add it to the CLASSPATH globally by modifying the environment");
		System.out.println("variable. Consult a Java reference for instructions.");
	}
}