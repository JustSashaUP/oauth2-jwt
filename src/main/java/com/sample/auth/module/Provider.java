package com.sample.auth.module;

public enum Provider {
    GOOGLE("google");

    private final String provider;

    Provider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
}
