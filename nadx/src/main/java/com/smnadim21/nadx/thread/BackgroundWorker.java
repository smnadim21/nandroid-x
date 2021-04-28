package com.smnadim21.nadx.thread;


import android.app.Activity;
import android.view.View;
import com.smnadim21.nadx.tools.NandX;

import org.json.JSONException;

public abstract class BackgroundWorker {
    //private final Future<?> longRunningTaskFuture;
    // private final Runnable longRunningTask;
    // private ExecutorService executorService;
    Activity activity;
    Thread thread;


    public BackgroundWorker(Activity activity) {
        this.activity = activity;
        thread = startBackground();
    }

    private Thread startBackground() {
        return new Thread(new Runnable() {
            public void run() {
                NandX.print("Thread working from " + activity.getClass().getSimpleName());
                try {
                    workInBackground();
                } catch (final JSONException e) {
                    e.printStackTrace();
                    if (activity != null)
                        NandX.snackOK(
                                activity,
                                e.getMessage(),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        NandX.showError(activity, NandX.getPrettyJson(e));
                                    }
                                }
                        );

                }

                if (activity != null)
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            NandX.print("UI Thread working from " + activity.getClass().getSimpleName());
                            onAfterExecute();
                            die();
                        }
                    });
            }
        });
    }


    public BackgroundWorker execute() {
        if (thread != null)
            thread.start();
        return this;
    }

    public void die() {
        if (thread != null)
            thread.interrupt();
    }

    public Thread me() {
        if (thread.isAlive()) {
            return thread;
        }
        return null;
    }

    public boolean working() {
        return thread.isAlive();
    }

    public abstract void workInBackground() throws JSONException;

    public abstract void onAfterExecute();


}
