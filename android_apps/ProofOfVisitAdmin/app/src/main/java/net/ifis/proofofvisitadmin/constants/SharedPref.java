package net.ifis.proofofvisitadmin.constants;

import android.content.SharedPreferences;

public class SharedPref {

    public static final String SHAREDPREFERENCES_FILENAME = "SharedPrefs";
    public static final String SHAREDPREFERENCES_DEFAULT_VALUE = "undefined";
    public static final String SHAREDPREFERENCES_WALLET_ADDRESS = "wallet_address";
    public static final String SHAREDPREFERENCES_WALLET_PASSWORD = "wallet_password";
    public static final String SHAREDPREFERENCES_RECEIVING_ADDRESS = "receiving_address";
    public static final String SHAREDPREFERENCES_LOCATION_NANME = "location_name";
    public static final String SHAREDPREFERENCES_TOKEN_NAME = "token_name";
    public static final String SHAREDPREFERENCES_TOKEN_SYMBOL = "token_symbol";

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
