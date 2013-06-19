package com.kkalinski.pocketclient;

public class PocketAccessToken {
    private final String username;
    private final String accessToken;

    public PocketAccessToken(final String username, final String accessToken) {
        this.username = username;
        this.accessToken = accessToken;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }
}
