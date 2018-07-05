package com.francesco.raspapp.ui;

import android.util.Pair;

import com.francesco.raspapp.viewmodel.MainActivityVM;
import com.francesco.raspapp.viewmodel.Message;
import com.google.android.things.contrib.driver.button.Button;
import com.google.firebase.database.DatabaseReference;

public class MyOnButtonEventListener implements Button.OnButtonEventListener {
    private final MainActivityVM mainActivityVM;
    private final DatabaseReference dbRef;
    private final int button;

    public MyOnButtonEventListener(MainActivityVM mainActivityVM, DatabaseReference dbRef, int button) {
        this.mainActivityVM = mainActivityVM;
        this.dbRef = dbRef;
        this.button = button;
    }

    @Override
    public void onButtonEvent(Button button, boolean pressed) {
        if (pressed && mainActivityVM.slotIsActive(this.button)) {
            Pair<String, Message> p = mainActivityVM.getLiveDataSlot(this.button).getValue();

            dbRef.child(String.format("%s/seen", p.first)).setValue(true);
            new MyAsyncTask().execute(p.second.getText());
        }
    }
}
