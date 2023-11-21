package com.example.caipiao;
import android.app.Application;

public class MyApplication extends Application {
    public static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }
}
