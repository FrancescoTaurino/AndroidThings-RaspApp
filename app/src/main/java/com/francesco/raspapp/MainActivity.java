package com.francesco.raspapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.francesco.raspapp.ui.MyChildEventListener;
import com.francesco.raspapp.ui.MyObserver;
import com.francesco.raspapp.ui.MyOnButtonEventListener;
import com.francesco.raspapp.viewmodel.MainActivityVM;
import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("messages");

    private MainActivityVM mainActivityVM;

    private Gpio ledRed;
    private Gpio ledGreen;
    private Gpio ledBlue;

    private Button buttonA;
    private Button buttonB;
    private Button buttonC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ledRed = RainbowHat.openLedRed();
            ledGreen = RainbowHat.openLedGreen();
            ledBlue = RainbowHat.openLedBlue();

            ledRed.setValue(false);
            ledGreen.setValue(false);
            ledBlue.setValue(false);

            buttonA = RainbowHat.openButtonA();
            buttonB = RainbowHat.openButtonB();
            buttonC = RainbowHat.openButtonC();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to open Gpio led or Button");
        }

        mainActivityVM = ViewModelProviders.of(this).get(MainActivityVM.class);
        mainActivityVM.getLiveDataSlot(0).observe(this, new MyObserver(ledRed));
        mainActivityVM.getLiveDataSlot(1).observe(this, new MyObserver(ledGreen));
        mainActivityVM.getLiveDataSlot(2).observe(this, new MyObserver(ledBlue));

        buttonA.setOnButtonEventListener(new MyOnButtonEventListener(mainActivityVM, dbRef, 0));
        buttonB.setOnButtonEventListener(new MyOnButtonEventListener(mainActivityVM, dbRef, 1));
        buttonC.setOnButtonEventListener(new MyOnButtonEventListener(mainActivityVM, dbRef, 2));

        dbRef.addChildEventListener(new MyChildEventListener(mainActivityVM));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (ledRed != null) {
                ledRed.setValue(false);
                ledRed.close();
            }

            if (ledGreen != null) {
                ledGreen.setValue(false);
                ledGreen.close();
            }

            if (ledBlue != null) {
                ledBlue.setValue(false);
                ledBlue.close();
            }

            if (buttonA != null)
                buttonA.close();

            if (buttonB != null)
                buttonB.close();

            if (buttonC != null)
                buttonC.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to close Gpio led or Button");
        }
    }
}
