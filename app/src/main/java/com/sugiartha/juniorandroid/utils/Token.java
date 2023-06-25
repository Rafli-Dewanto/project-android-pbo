package com.sugiartha.juniorandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import com.sugiartha.juniorandroid.BuildConfig;
import com.sugiartha.juniorandroid.LoginActivity;
import com.sugiartha.juniorandroid.MainActivity;
import com.sugiartha.juniorandroid.model.Auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Token {
    private static final String SECRET_KEY = BuildConfig.SECRET_KEY;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static String generateToken(Auth user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 900000);

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("fullname", user.getFullname())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return token;
    }
}
