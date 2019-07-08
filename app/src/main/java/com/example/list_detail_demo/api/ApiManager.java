package com.example.list_detail_demo.api;

import com.example.list_detail_demo.model.DeliveryDetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static ApiManager ourInstance = new ApiManager();

    private Retrofit deliveryRetrofit;
    private DeliveryService deliverService;

    public static ApiManager getInstance() {
        return ourInstance;
    }

    private ApiManager() {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://mock-api-mobile.dev.lalamove.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         deliverService = retrofit.create(DeliveryService.class);
    }

    //Delivery: listing: from = 0, size = 10
    public void getDeliveryList(int offset, int limit, Callback<List<DeliveryDetailModel>> callback) {
        Call<List<DeliveryDetailModel>> listCallback = deliverService.getDeliveryList(offset, limit);
        listCallback.enqueue(callback);
    }

}
