package com.NikhilProject.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtConstant {
    public static final String SECRET_KEY = "shjaskvnaup94er382bnafnakvs8hrowirvanbkjfndsoaih";
//    public static Key SECRET_KEY= Keys.secretKeyFor(SignatureAlgorithm.ES256);
    public static String JWT_HEADER ="Authorization";
}
