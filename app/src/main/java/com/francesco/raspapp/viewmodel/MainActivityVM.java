package com.francesco.raspapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Pair;

public class MainActivityVM extends ViewModel {
    private MutableLiveData<Pair<String, Message>> slotA;
    private MutableLiveData<Pair<String, Message>> slotB;
    private MutableLiveData<Pair<String, Message>> slotC;

    public LiveData<Pair<String, Message>> getLiveDataSlot(int slot) {
        if (slot == 0) {
            if (slotA == null)
                slotA = new MutableLiveData<>();

            return slotA;
        }
        else if (slot == 1) {
            if (slotB == null)
                slotB = new MutableLiveData<>();

            return slotB;
        }
        else if (slot == 2) {
            if (slotC == null)
                slotC = new MutableLiveData<>();

            return slotC;
        }
        else
            throw new IllegalStateException("This exception should never happen");
    }

    public void addMessage(String key, Message message) {
        if (slotA.getValue() == null)
            slotA.setValue(new Pair<>(key, message));
        else if (slotB.getValue() == null)
            slotB.setValue(new Pair<>(key, message));
        else if (slotC.getValue() == null)
            slotC.setValue(new Pair<>(key, message));
        else
            throw new IllegalStateException("This exception should never happen");
    }

    public void removeMessage(String key) {
        if (slotA.getValue() != null && slotA.getValue().first.equals(key))
            slotA.setValue(null);
        else if (slotB.getValue() != null && slotB.getValue().first.equals(key))
            slotB.setValue(null);
        else if (slotC.getValue() != null && slotC.getValue().first.equals(key))
            slotC.setValue(null);
        else
            throw new IllegalStateException("This exception should never happen");
    }

    public boolean slotIsActive(int slot) {
        if (slot == 0)
            return slotA.getValue() != null;
        else if (slot == 1)
            return slotB.getValue() != null;
        else if (slot == 2)
            return slotC.getValue() != null;
        else
            throw new IllegalStateException("This exception should never happen");
    }
}
