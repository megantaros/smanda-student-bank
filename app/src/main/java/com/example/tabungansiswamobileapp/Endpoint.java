package com.example.tabungansiswamobileapp;

public class Endpoint {
    private static final String BASE_URL = "http://192.168.1.16:8000/api";
    public static final String LOGIN = BASE_URL + "/login";
    public static final String TRANSAKSI = BASE_URL + "/transaksi";
    public static final String DETAIL_TRANSAKSI = BASE_URL + "/get-transaksi";
    public static final String LAST_TRANSAKSI = BASE_URL + "/get-last-transaksi";
    public static final String UPDATE_PROFIL = BASE_URL + "/update-profil";
}
