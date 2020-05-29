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
    public void setToken(int[] token) {
        this.token = token;
        this.tokenAmount = this.token.length;
    }

    @Override
    public boolean equals(Object object) {

        boolean isEqual = false;

        if(object instanceof Token) {
            Token token = (Token) object;
            if(this.locationName.equals(token.locationName)
            && this.locationAddress.equals(token.locationAddress)
            && this.tokenAmount == token.tokenAmount
            && this.tokenSymbol.equals(token.tokenSymbol)
            && this.tokenName.equals(token.tokenName)) {
                isEqual = true;
            }
        }
        return isEqual;
    }
}
