package com.odading.bestmoviev3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.activity.DetailSearchTv;
import com.odading.bestmoviev3.item.SearchTvItem;
import com.odading.bestmoviev3.prcelable.MovieParcelable;

import java.util.ArrayList;

public class SearchTvAdapter extends RecyclerView.Adapter<SearchTvAdapter.SearchTvViewHolder> {
    private ArrayList<SearchTvItem> mData = new ArrayList<>();
    private Context context;

    public SearchTvAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<SearchTvItem> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
        return new SearchTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTvViewHolder holder, int position) {
        holder.tvTitles.setText(mData.get(position).getTvTitles());
        holder.tvOverview.setText(mData.get(position).getTvOverview());
        Glide.with(context)
                .load(mData.get(position).getTvPhoto())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.tvPhoto);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SearchTvViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitles, tvOverview;
        ImageView tvPhoto;
        public SearchTvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitles = itemView.findViewById(R.id.tv_item_title);
            tvOverview = itemView.findViewById(R.id.tv_item_detail);
            tvPhoto = itemView.findViewById(R.id.img_item_movie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postition = getAdapterPosition();
                    Toast.makeText(context, mData.get(postition).getTvTitles(), Toast.LENGTH_SHORT).show();
                    MovieParcelable parcelable = new MovieParcelable();
                    parcelable.setTvshowTites(mData.get(postition).tvTitles);
                    parcelable.setTvshowOverview(mData.get(postition).tvOverview);
                    parcelable.setTvshowLanguage(mData.get(postition).tvLanguage);
                    parcelable.setFirstAirDate(mData.get(postition).firsAirDate);
                    parcelable.setTvshowPopularity(mData.get(postition).tvPopularity);
                    parcelable.setTvPhoto(mData.get(postition).tvPhoto);

                    Intent intent = new Intent(context.getApplicationContext(), DetailSearchTv.class);
                    intent.putExtra(DetailSearchTv.EXTRA_SEARCHTV, parcelable);
                    context.startActivity(intent);
                }
            });
        }
    }
}
