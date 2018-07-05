package com.francesco.raspapp.ui;

import android.os.AsyncTask;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

public class MyAsyncTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... texts) {
        String text = texts[0];

        try {
            AlphanumericDisplay display = RainbowHat.openDisplay();
            display.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
            display.setEnabled(true);

            display.display(text);
            Thread.sleep(2000);

            if (text.length() > 4) {
                for (int i = 0; i < text.length() - 4; i++) {
                    display.display(text.substring(i+1));
                    Thread.sleep(1000);
                }
            }

            Thread.sleep(2000);
            display.clear();
            display.setEnabled(false);
            display.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to show message on screen");
        }
        return null;
    }
}
