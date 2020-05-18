package com.example.cards2;

public class Matches {

    private String start, end, result;
    private int id, nplayers;

    public Matches(int id, String start, String end, int nplayers, String result) {
        this.start = start;
        this.end = end;
        this.result = result;
        this.id = id;
        this.nplayers = nplayers;
    }

    public int getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getResult() {
        return result;
    }

    public int getNplayers() {
        return nplayers;
    }





}
