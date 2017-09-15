package biz.diginov.isitravel;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

public class SessionManager {

    private static SessionManager INSTANCE = null;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    private SessionManager(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized SessionManager getInstance(SharedPreferences prefs) {
        if (INSTANCE == null) {
            INSTANCE = new SessionManager(prefs);
        }
        return INSTANCE;
    }

    public void startSession(String username) {
        editor.putString("USER_NAME", username).commit();
    }

    public void endSession() {
        editor.remove("USER_NAME").commit();
    }

    public String getSession() {
        return prefs.getString("USER_NAME", null);
    }
}
