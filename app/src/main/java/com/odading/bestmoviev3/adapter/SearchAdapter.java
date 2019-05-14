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
import com.odading.bestmoviev3.activity.DetailSearchMovie;
import com.odading.bestmoviev3.item.SearchItem;
import com.odading.bestmoviev3.prcelable.MovieParcelable;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<SearchItem> mData = new ArrayList<>();
    private Context context;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<SearchItem> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.titles.setText(mData.get(position).getTitles());
        holder.detail.setText(mData.get(position).getDetail());
        Glide.with(context)
                .load(mData.get(position).getPhoto())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView titles, detail;
        ImageView photo;
        public SearchViewHolder(@NonNull View itemView) {
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
                    Intent intent = new Intent(context.getApplicationContext(), DetailSearchMovie.class);
                    intent.putExtra(DetailSearchMovie.EXTRA_SEARCHMOVIE, movieParcelable);
                    context.startActivity(intent);
                    Toast.makeText(context, mData.get(position).getTitles(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
