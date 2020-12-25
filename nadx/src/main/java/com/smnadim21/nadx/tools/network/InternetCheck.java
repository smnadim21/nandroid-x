package com.smnadim21.nadx.tools.network;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by d3stR0y3r on 10/14/2018.
 */

 /*
 TODO: add this  permission to manifest
    <uses-permission android:name="android.permission.INTERNET" />
*/

public class InternetCheck extends AsyncTask<Void, Void, Boolean> {

    private Consumer mConsumer;

    public interface Consumer {
        void accept(Boolean internet);
    }

    public InternetCheck(Consumer consumer) {
        mConsumer = consumer;
        execute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
            sock.close();
            return true;
        } catch (IOException e) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("1.1.1.1", 53), 1000);
                sock.close();
                return true;
            } catch (IOException ex) {
                return false;
            }

        }
    }

    @Override
    protected void onPostExecute(Boolean internet) {
        mConsumer.accept(internet);
    }
}