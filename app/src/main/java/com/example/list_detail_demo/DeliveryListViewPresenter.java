package com.example.list_detail_demo;

import android.content.Context;

import com.example.list_detail_demo.api.ApiManager;
import com.example.list_detail_demo.model.DeliveryDetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryListViewPresenter implements DeliveryListContract.DeliveryListPresenter {

    private int offset = 0;
    private int limit = 20;

    private DeliveryListContract.DeliveryListView deliveryListView;

    public DeliveryListViewPresenter(DeliveryListContract.DeliveryListView deliveryListView) {
        this.deliveryListView = deliveryListView;
    }

    @Override
    public void loadDeliveryListData(Context context) {
        ApiManager.getInstance().getDeliveryList(offset, limit, new Callback<List<DeliveryDetailModel>>(){

            @Override
            public void onResponse(Call<List<DeliveryDetailModel>> call, Response<List<DeliveryDetailModel>> response) {
                List<DeliveryDetailModel> listData = response.body();

                //Update View...
                if (listData != null) {
                    deliveryListView.updateDeliveryListView(listData);
                }
            }

            @Override
            public void onFailure(Call<List<DeliveryDetailModel>> call, Throwable t) {
                //Handle ErrorView
                deliveryListView.displayErrorView();
            }
        });

    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void increaseOffset() {
        offset += limit;
    }

    @Override
    public void resetOffset() { offset = 0; }
}

