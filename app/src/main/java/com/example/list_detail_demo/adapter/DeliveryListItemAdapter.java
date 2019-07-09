package com.example.list_detail_demo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.list_detail_demo.R;
import com.example.list_detail_demo.listener.DeliveryListItemOnclickListener;
import com.example.list_detail_demo.model.DeliveryDetailModel;

import java.util.List;

public class DeliveryListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DeliveryDetailModel> listData;
    private DeliveryListItemOnclickListener itemOnclickListener;

    public DeliveryListItemAdapter(List<DeliveryDetailModel> listData, DeliveryListItemOnclickListener itemOnclickListener) {
        this.listData = listData;
        this.itemOnclickListener = itemOnclickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_list_item_layout, parent, false);
        return new DeliveryListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DeliveryDetailModel itemModel = listData.get(position);

        DeliveryListItemViewHolder viewHolder = (DeliveryListItemViewHolder) holder;
        viewHolder.bindView(itemModel);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public long getItemId(int position) {
        Object listItem = listData.get(position);
        return listItem.hashCode();
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    protected class DeliveryListItemViewHolder extends RecyclerView.ViewHolder {

        public CardView cardViewListItem;
        protected ImageView imageViewImage;

        protected TextView textViewContent;


        protected DeliveryListItemViewHolder(View itemView) {
            super(itemView);
            cardViewListItem = (CardView) itemView.findViewById(R.id.cardView_delivery_list_item);
            imageViewImage = (ImageView) itemView.findViewById(R.id.imageView_image);
            textViewContent = (TextView) itemView.findViewById(R.id.textView_list_item_content);
        }

        public void bindView(final DeliveryDetailModel deliveryContent) {
            //handle Image
            Glide.with(itemView.getContext()).load(deliveryContent.getImageUrl()).into(imageViewImage);
            Log.e("adapter", deliveryContent.getImageUrl());
            //handle content
            textViewContent.setText(deliveryContent.getDescription() + " at " + deliveryContent.getLocation().getAddress());
            Log.e("adapter",deliveryContent.getDescription() + " at " + deliveryContent.getLocation().getAddress());
            //handle OnClick

            cardViewListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemOnclickListener.onClick(view, deliveryContent);
                }
            });

        }
    }
}
