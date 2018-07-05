package com.francesco.raspapp.ui;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.francesco.raspapp.viewmodel.Message;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

public class MyObserver implements Observer<Pair<String, Message>> {
    private final Gpio led;

    public MyObserver(Gpio led) {
        this.led = led;
    }

    @Override
    public void onChanged(@Nullable Pair<String, Message> stringMessagePair) {
        try {
            led.setValue(stringMessagePair != null);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to turn on/off Gpio led");
        }
    }
}
