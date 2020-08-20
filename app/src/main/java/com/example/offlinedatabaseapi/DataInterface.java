package com.example.offlinedatabaseapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataInterface {
    @GET("project/coustomer.php")
    Call<List<Coustomer>> getAllCoustomers();
}
