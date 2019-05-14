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
import com.odading.bestmoviev3.activity.DetailActivity;
import com.odading.bestmoviev3.item.MovieItems;
import com.odading.bestmoviev3.prcelable.MovieParcelable;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieviewHolder> {
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new MovieviewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieviewHolder movieviewHolder, int position) {
        movieviewHolder.titles.setText(mData.get(position).getTitles());
        movieviewHolder.detail.setText(mData.get(position).getDetail());
        Glide.with(context)
                .load(mData.get(position).getPhoto())
                .apply(new RequestOptions().override(55, 55))
                .into(movieviewHolder.photo);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieviewHolder extends RecyclerView.ViewHolder {
        TextView titles, detail;
        ImageView photo;
        public MovieviewHolder(@NonNull View itemView) {
            super(itemView);
            titles = itemView.findViewById(R.id.tv_item_title);
            detail = itemView.findViewById(R.id.tv_item_detail);
            photo = itemView.findViewById(R.id.img_item_movie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    MovieParcelable movieParcelable = new MovieParcelable();
                    movieParcelable.setTitles(mData.get(position).titles);
                    movieParcelable.setOverview(mData.get(position).detail);
                    movieParcelable.setRelease(mData.get(position).release);
                    movieParcelable.setPopularity(mData.get(position).popularity);
                    movieParcelable.setLanguage(mData.get(position).language);
                    movieParcelable.setPhoto(mData.get(position).photo);
                    Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, movieParcelable);
                    context.startActivity(intent);
                    Toast.makeText(context, mData.get(position).getTitles(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
