package com.example.list_detail_demo;

import android.content.Context;

import com.example.list_detail_demo.model.DeliveryDetailModel;

import java.util.List;

public interface DeliveryListContract {

    interface DeliveryListView {

        void reloadDeliveryList();

        void updateDeliveryListView(List<DeliveryDetailModel> listData);

        void displayErrorView();
    }

    interface DeliveryListPresenter {

        void loadDeliveryListData(Context context);

        int getOffset();

        void increaseOffset();

        void resetOffset();
    }
}
