/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.modelController;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Islom
 */
public abstract class ChangeListener implements Runnable {

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            onChanged();
        }
    };

    List item;
    private List oldValue;

    public ChangeListener(List item) {
        this.item = item;
        oldValue = item;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        timer.schedule(task, 100);
    }

    public abstract void onChanged();

}
