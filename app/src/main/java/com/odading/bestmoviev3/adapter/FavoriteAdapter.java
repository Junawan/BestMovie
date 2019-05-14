package com.odading.bestmoviev3.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.odading.bestmoviev3.helper.CustomOnItemClickListener;
import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.db.FavoriteHelper;
import com.odading.bestmoviev3.prcelable.MovieModel;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    FavoriteHelper favoriteHelper;

    private ArrayList<MovieModel> listFavorite = new ArrayList<>();
    private Activity activity;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieModel> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<MovieModel> listFavorite) {
        if (listFavorite.size() > 0) {
            this.listFavorite.clear();
        }
        this.listFavorite.addAll(listFavorite);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.listFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listFavorite.size());
    }


    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_movie, parent, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.tvTitle.setText(listFavorite.get(position).getTitles());
        holder.tvOverview.setText(listFavorite.get(position).getOverview());
        Glide.with(activity)
                .load(listFavorite.get(position).getPhoto())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imageView);
        holder.fabDelete.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, final int position) {
                String dialogTitle, dialogMessage;

                dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
                dialogTitle = "Hapus dari daftar favorit";

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle(dialogTitle);
                alertDialogBuilder
                        .setMessage(dialogMessage)
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                favoriteHelper = FavoriteHelper.getInstance(activity.getApplicationContext());
                                int delete = favoriteHelper.deleteFavorite(listFavorite.get(position).getId());
                                if (delete > 0)
                                    removeItem(position);
                                Toast.makeText(activity, "Menghapus dari list favorit", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        }));
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView imageView;
        FloatingActionButton fabDelete;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvOverview = itemView.findViewById(R.id.tv_item_detail);
            imageView = itemView.findViewById(R.id.img_item_movie);
            fabDelete = itemView.findViewById(R.id.fab_delete);
        }
    }
}
