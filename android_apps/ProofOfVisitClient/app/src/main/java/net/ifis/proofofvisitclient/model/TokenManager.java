package net.ifis.proofofvisitclient.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class TokenManager {

    private ArrayList<Token> tokenList;

    public TokenManager(String jsonObjectString) {

        this.tokenList = new ArrayList<>();

        String locationAddress = null;
        String locationName = null;
        String tokenName = null;
        String tokenSymbol = null;
        int tokenAmount;
        String tokenString;
        int[] token = null;

        try {
             JSONArray jsonArray = new JSONArray(jsonObjectString);

             for(int i = 0; i < jsonArray.length(); i++) {

                 JSONObject jsonObject = jsonArray.getJSONObject(i);

                 tokenAmount = 0;

                 locationAddress = jsonObject.get("locationaddress").toString();
                 locationName = jsonObject.get("locationname").toString();
                 tokenName = jsonObject.get("tokenname").toString();
                 tokenSymbol = jsonObject.get("tokensymbol").toString();
                 tokenAmount++;
                 tokenString = jsonObject.get("token").toString() + ",";

                 for (int j = 0; j < jsonArray.length(); j++) {

                     if (i == j) {
                         j++;
                     }

                     if (j == jsonArray.length()) {
                         break;
                     }


                     JSONObject json = jsonArray.getJSONObject(j);

                     if (locationName.equals(json.get("locationname"))) {
                         tokenAmount++;
                         tokenString = tokenString + json.get("token").toString() + ",";
                     }
                 }

                 token = new int[tokenAmount];
                 StringTokenizer st = new StringTokenizer(tokenString, ",");
                 int tempIndex = 0;
                 while (st.hasMoreTokens() && tempIndex != tokenAmount) {
                     token[tempIndex] = Integer.parseInt(st.nextToken());
                     tempIndex++;
                 }

                 Token newToken = new Token(locationAddress, locationName, tokenName, tokenSymbol, tokenAmount, token);
                 if(!this.tokenList.contains(newToken)) {
                     this.tokenList.add(newToken);
                 }

             }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Token> getTokenList() {
        return this.tokenList;
    }

    public int getTokenListSize() {
        return this.tokenList.size();
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
