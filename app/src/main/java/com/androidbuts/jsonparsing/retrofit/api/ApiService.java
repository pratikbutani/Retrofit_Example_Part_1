package com.androidbuts.jsonparsing.retrofit.api;

import com.androidbuts.jsonparsing.model.Contacts;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Pratik Butani on 23/4/16.
 */
public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of Contacts
    */
    @GET("/json_data.json")
    Call<Contacts> getMyJSON();



}
