package com.odading.bestmoviev3.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.db.DatabaseHelper;
import com.odading.bestmoviev3.db.FavoriteHelper;
import com.odading.bestmoviev3.prcelable.MovieParcelable;
import com.odading.bestmoviev3.widget.StackRemoteViewFactory;

import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.PHOTO;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TABLE_NAME;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TITLE;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    private FavoriteHelper favoriteHelper;
    ImageView imgBanner;
    TextView tvTitle, tvOverview, tvRelease, tvPopularity, tvLanguage;
    SQLiteDatabase database;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tv_title_movie);
        tvOverview = findViewById(R.id.tv_overview_movie);
        tvRelease = findViewById(R.id.tv_release_date);
        tvPopularity = findViewById(R.id.tv_popularity_movie);
        tvLanguage = findViewById(R.id.tv_language_movie);
        imgBanner = findViewById(R.id.banner_detail_movie);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        database = databaseHelper.getWritableDatabase();

        MovieParcelable movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
            String title = movies.getTitles();
            String detail = movies.getOverview();
            String release = movies.getRelease();
            String popularity = movies.getPopularity();
            String language = movies.getLanguage();
            String photo = movies.getPhoto();

            tvTitle.setText(title);
            tvOverview.setText(detail);
            tvRelease.setText(release);
            tvPopularity.setText(popularity);
            tvLanguage.setText(language);
            Glide.with(this)
                    .load(photo)
                    .apply(new RequestOptions().override(200, 300))
                    .into(imgBanner);

            Bundle bundle = new Bundle();
            bundle.putString(StackRemoteViewFactory.EXTRA_WIDGET, photo);
            Intent intent = new Intent();
            intent.putExtras(bundle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav, menu);
        MenuItem fav = menu.findItem(R.id.action_favorite);
        MovieParcelable data = getIntent().getParcelableExtra(EXTRA_MOVIE);
            String title = data.getTitles();
            Cursor mCursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE title=?", new String[]{title});

            if (mCursor.moveToFirst())
            {
                fav.setIcon(R.drawable.ic_favorite_red_24dp);
            }
            else
            {
                fav.setIcon(R.drawable.ic_favorite_black_24dp);
            }
            mCursor.close();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_favorite:
                    MovieParcelable data = getIntent().getParcelableExtra(EXTRA_MOVIE);

                        String title = data.getTitles();
                        String detail = data.getOverview();
                        String photo = data.getPhoto();

                        ContentValues values = new ContentValues();
                        values.put(TITLE, title);
                        values.put(DESCRIPTION, detail);
                        values.put(PHOTO, photo);
                        database = databaseHelper.getReadableDatabase();

                        Cursor mCursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE title=? AND description=?", new String[]{title,detail});

                        if (mCursor.moveToFirst())
                        {
                            Toast.makeText(this, "Film sudah terdaftar dalam list favorite", Toast.LENGTH_SHORT).show();
                            finish();
                            /* record exist */
                        }
                        else
                        {
                            getContentResolver().insert(CONTENT_URI, values);
                            Toast.makeText(this, "Menambahkan ke favorit", Toast.LENGTH_SHORT).show();
                            /* record not exist */
                        }
                        mCursor.close();

                        item.setIcon(R.drawable.ic_favorite_red_24dp);
            }
            return super.onOptionsItemSelected(item);
        }
}
