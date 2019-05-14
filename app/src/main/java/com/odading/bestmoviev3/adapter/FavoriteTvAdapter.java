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
import com.odading.bestmoviev3.db.TvHelper;
import com.odading.bestmoviev3.prcelable.TvModel;

import java.util.ArrayList;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.FavoriteTvViewHolder> {
    TvHelper tvHelper;
    private ArrayList<TvModel> listFavoriteTv = new ArrayList<>();
    private Activity activity;

    public FavoriteTvAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<TvModel> getListFavoriteTv() {
        return listFavoriteTv;
    }

    public void setListFavoriteTv(ArrayList<TvModel> listFavoriteTv) {
        if (listFavoriteTv.size() > 0) {
            this.listFavoriteTv.clear();
        }
        this.listFavoriteTv.addAll(listFavoriteTv);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.listFavoriteTv.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listFavoriteTv.size());
    }

    @NonNull
    @Override
    public FavoriteTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_movie, parent, false);
        return new FavoriteTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvViewHolder holder, int position) {
        holder.tvTitle.setText(listFavoriteTv.get(position).getTvTitles());
        holder.tvOverview.setText(listFavoriteTv.get(position).getTvOverview());
        Glide.with(activity)
                .load(listFavoriteTv.get(position).getTvPhoto())
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
                                tvHelper = TvHelper.getInstance(activity.getApplicationContext());
                                int delete = tvHelper.deleteFavoriteTv(listFavoriteTv.get(position).getId());
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
        return listFavoriteTv.size();
    }

    public class FavoriteTvViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview;
        ImageView imageView;
        FloatingActionButton fabDelete;

        public FavoriteTvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvOverview = itemView.findViewById(R.id.tv_item_detail);
            imageView = itemView.findViewById(R.id.img_item_movie);
            fabDelete = itemView.findViewById(R.id.fab_delete);

        }
    }

}
