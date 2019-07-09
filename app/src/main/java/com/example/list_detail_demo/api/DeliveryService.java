package com.example.list_detail_demo.api;

import com.example.list_detail_demo.model.DeliveryDetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeliveryService {

    @GET("/deliveries")
    Call<List<DeliveryDetailModel>> getDeliveryList(@Query("offset") int offset,
                                                   @Query("limit") int limit);
}
