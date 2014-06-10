package com.example.qrpoll.test;

import android.util.Log;

public final class Debug{
    private Debug (){}

    public static void out (Object msg){
        Log.d ("zzzzzz", msg.toString ());
       // Log.i ("zzzzzz", msg.toString ());
    }
}