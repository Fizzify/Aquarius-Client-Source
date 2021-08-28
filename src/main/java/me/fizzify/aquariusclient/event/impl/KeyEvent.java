package me.fizzify.aquariusclient.event.impl;

import me.fizzify.aquariusclient.event.Event;

public class KeyEvent extends Event {
    private final int key;

    public KeyEvent (int key) {
        this.key = key;
    }

    public int getKey () {
        return key;
    }
}