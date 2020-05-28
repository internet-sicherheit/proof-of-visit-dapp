package net.ifis.proofofvisitclient.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TokenManager {

    private ArrayList<Token> tokenList;

    public TokenManager(String jsonObjectString) {

        this.tokenList = new ArrayList<>();

        String locationAddress = null;
        String locationName = null;
        String tokenName = null;
        String tokenSymbol = null;
        int tokenAmount = 0;
        int[] token = null;

        try {
            JSONArray jsonArray = new JSONArray(jsonObjectString);

            for(int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                locationAddress = jsonObject.get("locationaddress").toString();
                locationName = jsonObject.get("locationname").toString();
                tokenName = jsonObject.get("tokenname").toString();
                tokenSymbol = jsonObject.get("tokensymbol").toString();
                tokenAmount = (int) jsonObject.get("tokenamount");

                JSONArray tokenArray = (JSONArray) jsonObject.get("token");
                token = new int[tokenArray.length()];

                for(int j = 0; j < tokenArray.length(); j++) {
                    token[j] = (int) tokenArray.get(j);
                }

                this.tokenList.add(new Token(locationAddress, locationName, tokenName, tokenSymbol, tokenAmount, token));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Token> getTokenList() {
        return this.tokenList;
    }

    public void showTokens() {
        for (int i = 0; i < this.tokenList.size(); i++) {
            Token t = this.tokenList.get(i);

            Log.d("tokenList", "-----------------------" + i);

            Log.d("tokenList", t.getLocationAddress());
            Log.d("tokenList", t.getLocationName());
            Log.d("tokenList", t.getTokenName());
            Log.d("tokenList", t.getTokenSymbol());
            Log.d("tokenList", "" + t.getTokenAmount());

            int[] array = t.getToken();

            for(int j = 0; j < array.length; j++) {
                Log.d("tokenList", "" + array[j]);
            }


        }
    }
}
