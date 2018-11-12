package com.sachin.ecom.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sachin.ecom.Model.ProductDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Utilities.TypeFaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListRecyclerAdapter extends RecyclerView.Adapter<ProductListRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final Picasso picasso;
    private final Typeface typeFace_FA, typeFace_Bold, typeFace_Light;
    private ArrayList<ProductDetails> productProductDetails;

    public ProductListRecyclerAdapter(Context act, ArrayList<ProductDetails> details) {

        this.context = act;
        this.productProductDetails = details;
        this.picasso = Picasso.get();
        this.typeFace_FA = TypeFaceHelper.getInstance(context).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME);
        this.typeFace_Bold = TypeFaceHelper.getInstance(context).getStyleTypeFace(TypeFaceHelper.FiraSansBold);
        this.typeFace_Light = TypeFaceHelper.getInstance(context).getStyleTypeFace(TypeFaceHelper.FiraSansLight);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rec_item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.rec_item_title.setText(productProductDetails.get(position).getP_name().toUpperCase());
        holder.rec_item_title.setTypeface(typeFace_Bold);
    }

    @Override
    public int getItemCount() {
        return productProductDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rec_parent_rel;
        private final TextView rec_item_title;

        public ViewHolder(View itemView) {
            super(itemView);

            rec_item_title = (TextView) itemView.findViewById(R.id.rec_item_title);
            rec_parent_rel = (RelativeLayout) itemView.findViewById(R.id.rec_parent_rel);
        }
    }
}