package com.hamta.mydivar;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hamta.mydivar.Model.ListItem;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;
    //    private List<Typeface> tf = new ArrayList<>();
    private Typeface tf;
//    private Typeface[] tf1 = new Typeface[2];

    public MyAdapter(List<ListItem> listItems, Context context, AssetManager assetManager) {
        this.listItems = listItems;
        this.context = context;
        this.tf = Typeface.createFromAsset(assetManager,
                "fonts/IRANSansBold.ttf");
//        this.tf.add(Typeface.createFromAsset(assetManager,
//                "fonts/IRANSansRegular.ttf"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);
        holder.textViewHead.setText(listItem.getTitle());
        if (!listItem.getPrice().equals("null")) {
            holder.textViewPrice.setText(listItem.getPrice() + " تومان ");
        } else {
            holder.textViewPrice.setText("");
        }
        holder.textViewCity.setText(listItem.getCity());
        holder.textViewDate.setText(listItem.getDate());
        holder.textViewHead.setTypeface(tf);
//        holder.textViewDesc.setText(listItem.getDesc());
//        holder.textViewDesc.setTypeface(tf.get(1));

        if (listItem.getUrl() != null) {
            Glide.with(context).load(listItem.getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.imageViewUrl);
        } else{
            Glide.with(context).load(R.drawable.preview).into(holder.imageViewUrl);
            holder.progressBar.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return listItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textViewPrice;
        public TextView textViewCity;
        public TextView textViewDate;
        public ImageView imageViewUrl;
        public ProgressBar progressBar;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.txt_title);
            textViewDesc = (TextView) itemView.findViewById(R.id.txt_detail_desc);
            textViewPrice = (TextView) itemView.findViewById(R.id.txt_price);
            textViewCity = (TextView) itemView.findViewById(R.id.txt_city);
            textViewDate = (TextView) itemView.findViewById(R.id.txt_date);
            imageViewUrl = (ImageView) itemView.findViewById(R.id.img_View);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar2);
        }
    }
}
