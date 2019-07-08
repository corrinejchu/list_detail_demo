package com.example.list_detail_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.list_detail_demo.model.DeliveryDetailModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.AppBarLayout;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String INTENT_DELIVERY_DETAIL_KEY = "Intent_Delivery_Detail_Key";
    private DeliveryDetailModel deliveryDetailModel;
    private GoogleMap mMap;
    private CardView cardViewListItem;
    private ImageView imageViewImage;
    private TextView textViewContent;
    private Toolbar toolbar;
    private AppBarLayout appbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        deliveryDetailModel = (DeliveryDetailModel) bundle.getSerializable(INTENT_DELIVERY_DETAIL_KEY);
        bindview();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(deliveryDetailModel.getLocation() == null){
            return;
        }

        LatLng address = new LatLng(deliveryDetailModel.getLocation().getLat(), deliveryDetailModel.getLocation().getLng());
        mMap.addMarker(new MarkerOptions().position(address).title(deliveryDetailModel.getLocation().getAddress()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 16));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void bindview(){

        cardViewListItem = (CardView) findViewById(R.id.cardView_delivery_list_item);
        imageViewImage = (ImageView) findViewById(R.id.imageView_image);
        textViewContent = (TextView) findViewById(R.id.textView_list_item_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar_delivery_detail);

        setupToolBarLayout();
        setupDeliveryDetailView();
    }

    private void setupDeliveryDetailView(){

        if(deliveryDetailModel == null) {
            return;
        }

        //handle Image
        Glide.with(this).load(deliveryDetailModel.getImageUrl()).into(imageViewImage);
        Log.e("adapter", deliveryDetailModel.getImageUrl());
        //handle content
        textViewContent.setText(deliveryDetailModel.getDescription() + " at " + deliveryDetailModel.getLocation().getAddress());
        Log.e("adapter",deliveryDetailModel.getDescription() + " at " + deliveryDetailModel.getLocation().getAddress());
        //handle OnClick
    }

    private void setupToolBarLayout(){

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


}
