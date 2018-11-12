package com.sachin.ecom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sachin.ecom.Activities.MainActivity;
import com.sachin.ecom.Model.CategoryDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Utilities.TypeFaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final Picasso picasso;
    private final Typeface typeFace_FA, typeFace_Bold, typeFace_Light;
    private ArrayList<CategoryDetails> productCategoryDetails;

    public CategoryRecyclerAdapter(Context act, ArrayList<CategoryDetails> details) {

        this.context = act;
        this.productCategoryDetails = details;
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
        holder.rec_item_title.setText(productCategoryDetails.get(position).getName().toUpperCase());
        holder.rec_item_title.setTypeface(typeFace_Bold);
        holder.rec_item_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productCategoryDetails.get(position).getChildCategories() != null &&
                        productCategoryDetails.get(position).getChildCategories().length() > 0) {
                    Intent intent = new Intent("category");
                    intent.putExtra("id",productCategoryDetails.get(position).getChildCategories().toString());
//                    context.sendBroadcast(intent);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }else{
                    ((MainActivity) context).loadSingleDetailFragment(Integer.toString(productCategoryDetails.get(position).getId()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productCategoryDetails.size();
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