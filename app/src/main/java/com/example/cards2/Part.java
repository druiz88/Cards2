package com.example.cards2;

public class Part {

    private int id, score, matches_id;
    private String part, hand;

    public Part(int id, String part, String hand, int score, int matches_id) {
        this.id = id;
        this.score = score;
        this.matches_id = matches_id;
        this.part = part;
        this.hand = hand;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getMatches_id() {
        return matches_id;
    }

    public String getPart() {
        return part;
    }

    public String getHand() {
        return hand;
    }



}
