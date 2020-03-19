package com.example.tentativa;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class InfoClass {

    static String ACCOUNT_NAME = "VoidInfo";
    static String ACCOUNT_EMAIL = "VoidInfo";
    static String ACCOUNT_ID = "VoidInfo";
    static Uri ACCOUNT_PHOTO = null;
    static GoogleSignInAccount acc = null;
    public static boolean SEND = false;
    static List<String> USERS;
    static boolean LOG_OUT;

    public static String title = "";
    public static String boby = "";
    public static String signature = "";


    public static List<TextStructure> LISTA;

    public static String getAccountName() {
        return ACCOUNT_NAME;
    }

    public static void setAccountName(String accountName) {
        ACCOUNT_NAME = accountName;
    }

    public static String getAccountEmail() {
        return ACCOUNT_EMAIL;
    }

    public static void setAccountEmail(String accountEmail) {
        ACCOUNT_EMAIL = accountEmail;
    }

    public static String getAccountId() {
        return ACCOUNT_ID;
    }

    public static void setAccountId(String accountId) {
        ACCOUNT_ID = accountId;
    }

    public static Uri getAccountPhoto() {
        return ACCOUNT_PHOTO;
    }

    public static void setAccountPhoto(Uri accountPhoto) {
        ACCOUNT_PHOTO = accountPhoto;
    }

    public GoogleSignInAccount getAcc(){
        return this.acc;
    }


}
