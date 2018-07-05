package com.francesco.raspapp.ui;

import android.util.Log;

import com.francesco.raspapp.viewmodel.MainActivityVM;
import com.francesco.raspapp.viewmodel.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class MyChildEventListener implements ChildEventListener {
    private static final String TAG = MyChildEventListener.class.getSimpleName();
    private final MainActivityVM mainActivityVM;

    public MyChildEventListener(MainActivityVM mainActivityVM) {
        this.mainActivityVM = mainActivityVM;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Message message = dataSnapshot.getValue(Message.class);

        if(message != null && !message.isSeen())
            mainActivityVM.addMessage(dataSnapshot.getKey(), message);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        // ATTENZIONE !! NON BISOGNA FARE MODIFICHE DALLA CONSOLE FIREBASE
        mainActivityVM.removeMessage(dataSnapshot.getKey());
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Message message = dataSnapshot.getValue(Message.class);

        if(message != null && !message.isSeen())
            mainActivityVM.removeMessage(dataSnapshot.getKey());
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG, "onChildAdded: " + dataSnapshot);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG, "onChildAdded: " + databaseError.getMessage());
    }
}
