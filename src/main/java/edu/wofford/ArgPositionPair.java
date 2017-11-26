package edu.wofford;

class ArgPositionPair implements Comparable<ArgPositionPair> {
    int position;
    Arg argument;

    public ArgPositionPair(int position, Arg argument) {
        this.position = position;
        this.argument = argument;
    }

    public Arg getArg(){
        return this.argument;
    }

    @Override
    public int compareTo(ArgPositionPair o) {
        return position < o.position ? -1 : position > o.position ? 1 : 0;
    }
}