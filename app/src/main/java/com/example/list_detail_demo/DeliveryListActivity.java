package com.example.list_detail_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.list_detail_demo.adapter.DeliveryListItemAdapter;
import com.example.list_detail_demo.listener.DeliveryListItemOnclickListener;
import com.example.list_detail_demo.model.DeliveryDetailModel;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeliveryListActivity extends AppCompatActivity implements DeliveryListContract.DeliveryListView, DeliveryListItemOnclickListener {

    private List<DeliveryDetailModel> deliveryList;
    private DeliveryListContract.DeliveryListPresenter deliveryPresenter;
    private SuperRecyclerView superRecyclerView;
    private DeliveryListItemAdapter listAdapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_list_activity_layout);

        deliveryList = new ArrayList<>();
        deliveryPresenter = new DeliveryListViewPresenter(this);
        bindview();

    }

    @Override
    public void displayErrorView() {
        Toast toast=Toast.makeText(this,"Oops! Something's gone wrong!!",Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void reloadDeliveryList() {
        superRecyclerView.setRefreshing(true);
        deliveryList.clear();
        deliveryPresenter.resetOffset();

        loadDeliverylistData();
        superRecyclerView.setLoadingMore(true);
    }

    @Override
    public void updateDeliveryListView(List<DeliveryDetailModel> apiListData) {
        superRecyclerView.setVisibility(View.VISIBLE);

        if (apiListData.size() > 0) {
            for (DeliveryDetailModel itemModel : apiListData) {
                if (itemModel != null) {
                    deliveryList.add(itemModel);
                }
            }
            listAdapter.notifyDataSetChanged();
            deliveryPresenter.increaseOffset();

        } else {
            superRecyclerView.hideMoreProgress();

            //handle no data
            if (deliveryList.size() <= 0) {
                displayErrorView();
            }
        }

        superRecyclerView.setRefreshing(false);
    }

    @Override
    public void onClick(View view, DeliveryDetailModel detailModel) {
        //handle onClick: start Activity...
        if (deliveryList == null && detailModel == null) {
            return;
        }

        Intent intent = new Intent(this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MapsActivity.INTENT_DELIVERY_DETAIL_KEY, (Serializable) detailModel);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void bindview() {
        superRecyclerView = (SuperRecyclerView) findViewById(R.id.recyclerViews_delivery_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar_delivery_list);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setupList();
    }

    private void setupList() {
        superRecyclerView.hideMoreProgress();
        superRecyclerView.setLoadingMore(false);

        listAdapter = new DeliveryListItemAdapter(deliveryList, this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        superRecyclerView.setLayoutManager(linearLayoutManager);
        superRecyclerView.setAdapter(listAdapter);


        superRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadDeliveryList();
            }
        });


        superRecyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                loadDeliverylistData();
            }
        }, 2);
        loadDeliverylistData();

    }

    private void loadDeliverylistData() {
        deliveryPresenter.loadDeliveryListData(this);
    }


}
