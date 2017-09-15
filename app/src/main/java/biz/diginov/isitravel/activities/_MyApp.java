package biz.diginov.isitravel.activities;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class _MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
