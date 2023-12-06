package com.bestbenefits.takoyaki.config.properties.oauth;

public enum OAuthSocialType{
    KAKAO("Kakao"),
    GOOGLE("Google"),
    NAVER("Naver");

    final private String pascalName;
    public String getPascalName() {
        return pascalName;
    }
    OAuthSocialType(String pascalName){
        this.pascalName = pascalName;
    }

    // same as valueOf(), but this throws custom exception
    public static OAuthSocialType fromValue(String socialName) {
        for (OAuthSocialType oAuthSocialType : OAuthSocialType.values()) {
            if (oAuthSocialType.name().equals(socialName))
                return oAuthSocialType;
        }
        throw new IllegalArgumentException("Invalid social platform name.");
    }

}
