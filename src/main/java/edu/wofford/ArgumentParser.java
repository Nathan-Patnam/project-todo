package edu.wofford;

import java.util.*;

public class ArgumentParser {

    private String programName;
    //give this dictionary a better name
    private Map<String, String> dictionary;
    private List<String> argumentNames;
    private String programDescription;
    private Map<String, String> argDescriptions;
    private Map<String, String> argDataTypes;
    private Boolean help = false;
    private List<String> dataTypes;
    private ArrayList<ArrayList<String>> listBadDataTypes = new ArrayList<ArrayList<String>>();

    public ArgumentParser(String programName) {
        this.programName = programName;
        dictionary = new HashMap<>();
        argumentNames = new ArrayList<>();
        dataTypes = new ArrayList<>();
        dataTypes.add("string");
        dataTypes.add("float");
        dataTypes.add("int");
        dataTypes.add("boolean");
        argDescriptions = new HashMap<>();
        argDataTypes = new HashMap<>();
    }

    public ArgumentParser(String programName, String description) {
        this.programName = programName;
        this.programDescription = description;
        dictionary = new HashMap<>();
        argumentNames = new ArrayList<>();
        argDescriptions = new HashMap<>();
        argDataTypes = new HashMap<>();
        dataTypes = new ArrayList<>();

        dataTypes.add("string");
        dataTypes.add("float");
        dataTypes.add("int");
        dataTypes.add("boolean");
    }

    public String getProgramName() {
        return programName;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public int getNumArguments() {
        return argumentNames.size();
    }

    public String getDataType(String argname) {
        return argDataTypes.get(argname);
    }

    public void addArg(String argname) {
        argumentNames.add(argname);
        argDataTypes.put(argname, "string");
    }

    public void addArg(String argname, String param2) {
        argumentNames.add(argname);

        if (dataTypes.contains(param2.toLowerCase())) {
            argDataTypes.put(argname, param2.toLowerCase());
        } else {
            argDescriptions.put(argname, param2);
            argDataTypes.put(argname, "string");

        }
    }

    public void addArg(String argname, String description, String dataType) {
        argumentNames.add(argname);
        argDescriptions.put(argname, description);
        argDataTypes.put(argname, dataType.toLowerCase());
    }

    private String getParameterString() {
        String key_string = "";
        for (int i = 0; i < argumentNames.size(); i++) {
            key_string += " " + argumentNames.get(i);
        }
        return key_string;
    }

    public String getHelpMessage() {
        String message = "";
        if (this.help) {
            message = "usage: java " + programName + getParameterString() + "\n" + programDescription + "\n"
                    + "positional arguments:";
            for (int i = 0; i < argumentNames.size(); i++) {
                message += "\n   " + argumentNames.get(i) + " " + argDescriptions.get(argumentNames.get(i));
            }
        }
        return message;
    }

    private void checkIfHelpMessage(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-h")) {
                this.help = true;
                String message = getHelpMessage();
                throw new HelpException(message);
            }
        }
    }

    public String getTypeExceptionMessage(ArrayList<ArrayList<String>> listBadDataTypes, int sizeBadDataTypes) {
        String message = "";
        message = "usage: java " + programName + getParameterString() + "\n" + programName + ".java: error: ";
        if (sizeBadDataTypes == 1) {
            message += "argument " + listBadDataTypes.get(0).get(0) + ":" + " invalid " + listBadDataTypes.get(0).get(1)
                    + " value: " + listBadDataTypes.get(0).get(2);
        }
        //if there are more than bad data argument
        else {
            for (int i = 0; i < sizeBadDataTypes; i++) {
                message += "argument " + listBadDataTypes.get(i).get(0) + ":" + " invalid " + listBadDataTypes.get(i).get(1)
                        + " value: " + listBadDataTypes.get(i).get(2) + "\n";
            }
        }

        return message;
    }

    public void areThereWrongDataTypes(String[] args) {
        int sizeBadDataTypes = 0;

        for (int i = 0; i < args.length; i++) {
            if (argDataTypes.get(argumentNames.get(i)).equals("float")) {
                try {
                    float temp = Float.parseFloat(args[i]);
                } catch (NumberFormatException e) {

                    ArrayList<String> badDataType = new ArrayList<String>();
                    badDataType.add(argumentNames.get(i));
                    badDataType.add("float");
                    badDataType.add(args[i]);
                    listBadDataTypes.add(badDataType);
                    sizeBadDataTypes++;
                }
            } else if (argDataTypes.get(argumentNames.get(i)).equals("int")) {
                try {
                    int temp = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {

                    ArrayList<String> badDataType = new ArrayList<String>();
                    badDataType.add(argumentNames.get(i));
                    badDataType.add("int");
                    badDataType.add(args[i]);

                    listBadDataTypes.add(badDataType);
                    sizeBadDataTypes++;
                }
            } else if (argDataTypes.get(argumentNames.get(i)).equals("boolean")) {
                if (!(args[i].equals("true") || args[i].equals("false"))) {

                    ArrayList<String> badDataType = new ArrayList<String>();
                    badDataType.add(argumentNames.get(i));
                    badDataType.add("boolean");
                    badDataType.add(args[i]);

                    listBadDataTypes.add(badDataType);
                    sizeBadDataTypes++;
                }
            }
        }

        if (sizeBadDataTypes > 0) {
            String message = getTypeExceptionMessage(listBadDataTypes, sizeBadDataTypes);
            throw new HelpException(message);
        }
    }

    private void checkIfTooFewArguments(String[] args, String key_string) {
        if (args.length < argumentNames.size()) {
            String missingArguements = "";
            for (int i = args.length; i < argumentNames.size(); i++) {
                missingArguements += " " + argumentNames.get(i);
            }
            String message = "usage: java " + programName + key_string + "\n"
                    + programName + ".java: error: the following arguments are required:" + missingArguements;
            throw new TooFewArguments(message);
        }
    }


    private void checkIfTooManyArguments(String[] args, String key_string) {
        if (args.length > argumentNames.size()) {
            String tooManyArguments = "";
            for (int i = argumentNames.size(); i < args.length; i++) {
                tooManyArguments += " " + args[i];
            }
            String message = "usage: java " + programName + key_string + "\n"
                    + programName + ".java: error: unrecognized arguments:" + tooManyArguments;

            throw new TooManyArguments(message);
        }
    }

    public void parse(String[] args) {

        String key_string = "";
        for (int i = 0; i < argumentNames.size(); i++) {
            key_string += " " + argumentNames.get(i);
        }

        checkIfHelpMessage(args);

        checkIfTooFewArguments(args, key_string);

        checkIfTooManyArguments(args, key_string);

        areThereWrongDataTypes(args);

        for (int i = 0; i < argumentNames.size(); i++) {
            dictionary.put(argumentNames.get(i), args[i]);
        }
    }

    public String getArgValue(String argname) {
        return dictionary.get(argname);
    }
}
