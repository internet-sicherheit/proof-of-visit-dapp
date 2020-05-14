package net.ifis.proofofvisitclient.constants;

import android.content.SharedPreferences;

public class SharedPref {

    public static final String SHAREDPREFERENCES_FILENAME = "SharedPrefs";
    public static final String SHAREDPREFERENCES_DEFAULT_VALUE = "undefined";
    public static final String SHAREDPREFERENCES_WALLET_ADDRESS = "wallet_address";
    public static final String SHAREDPREFERENCES_WALLET_PASSWORD = "wallet_password";

    private SharedPreferences sharedPref;

    public SharedPref(SharedPreferences sharedPreferences) {
        sharedPref = sharedPreferences;
    }

    public void add(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPref.getString(key, SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE);
    }
}
