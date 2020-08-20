package com.example.offlinedatabaseapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private DataInterface mDataInterface;

    public DataInterface getCoustomerService() {
        if (mDataInterface == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mDataInterface = retrofit.create(DataInterface.class);
        }
        return mDataInterface;
    }
}
