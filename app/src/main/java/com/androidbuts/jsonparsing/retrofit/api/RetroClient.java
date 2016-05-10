package com.androidbuts.jsonparsing.retrofit.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author Pratik Butani
 */
public class RetroClient {

    /********
     * URLS
     *******/
    private static final String ROOT_URL = "http://pratikbutani.x10.mx";

    public RetroClient() {

    }

    /**
     * Get Table Booking Charge
     *
     * @return JSON Object
     */
    public static Retrofit getDataFromWeb() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
