package net.ifis.proofofvisitclient.model;

import net.ifis.proofofvisitclient.activities.MainActivity;

public class Token {

    private String locationAddress;
    private String locationName;
    private String tokenName;
    private String tokenSymbol;
    private int tokenAmount;
    private int[] token;

    public Token(String locationAddress, String locationName, String tokenName, String tokenSymbol, int tokenAmount, int[] token) {
        this.locationAddress = locationAddress;
        this.locationName = locationName;
        this.tokenName = tokenName;
        this.tokenSymbol = tokenSymbol;
        this.tokenAmount = tokenAmount;
        this.token = token;
    }

    public String getLocationAddress() {
        return this.locationAddress;
    }
    public String getLocationName() {
        return this.locationName;
    }
    public String getTokenName() {
        return this.tokenName;
    }
    public String getTokenSymbol() {
        return this.tokenSymbol;
    }
    public int getTokenAmount() {
        return this.tokenAmount;
    }
    public int[] getToken() {
        return this.token;
    }
    public int getTokenOfIndex(int index) {
        return this.token[index];
    }
}
