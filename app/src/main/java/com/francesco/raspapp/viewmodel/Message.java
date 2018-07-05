package com.francesco.raspapp.viewmodel;

public class Message {
    private final String text;
    private final boolean seen;

    public Message() {
        this.text = "";
        this.seen = false;
    }

    public Message(String text, boolean seen) {
        this.text = text;
        this.seen = seen;
    }

    public String getText() {
        return this.text;
    }

    public boolean isSeen() {
        return this.seen;
    }
}