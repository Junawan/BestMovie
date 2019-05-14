package com.odading.bestmoviev3.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.odading.bestmoviev3.db.TvHelper;
import com.odading.bestmoviev3.prcelable.MovieParcelable;

import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.CONTENT_URI_TV;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TABLE_TV;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TVDESCRIPTION;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TVPHOTO;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TVTITLE;

public class DetailSearchTv extends AppCompatActivity {
    public static final String EXTRA_SEARCHTV = "extra_searchtv";
    private TvHelper tvHelper;
    SQLiteDatabase databasetv;
    DatabaseHelper databaseHelpertv = new DatabaseHelper(this);

    ImageView imgBanneTvr;
    TextView tvshowTitle, tvshowOverview, tvshowPopularity, tvshowLanguage, firsAirDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        tvshowTitle = findViewById(R.id.tv_title_tvshow);
        tvshowOverview = findViewById(R.id.tv_overview_movie);
        tvshowLanguage = findViewById(R.id.tv_language_movie);
        tvshowPopularity = findViewById(R.id.tv_popularity_movie);
        firsAirDate = findViewById(R.id.tv_release_date);
        imgBanneTvr = findViewById(R.id.banner_detail_tvshow);

        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();
        databasetv = databaseHelpertv.getWritableDatabase();

        MovieParcelable tvshow = getIntent().getParcelableExtra(EXTRA_SEARCHTV);
        String title = tvshow.getTvshowTites();
        String detail = tvshow.getTvshowOverview();
        String release = tvshow.getFirstAirDate();
        String popularity = tvshow.getTvshowPopularity();
        String language = tvshow.getTvshowLanguage();
        String photo = tvshow.getTvPhoto();

        tvshowTitle.setText(title);
        tvshowOverview.setText(detail);
        tvshowPopularity.setText(popularity);
        tvshowLanguage.setText(language);
        firsAirDate.setText(release);
        Glide.with(this)
                .load(photo)
                .apply(new RequestOptions().override(200, 300))
                .into(imgBanneTvr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fav, menu);
        MenuItem fav = menu.findItem(R.id.action_favorite);
        MovieParcelable data = getIntent().getParcelableExtra(EXTRA_SEARCHTV);
        String title = data.getTvshowTites();
        Cursor mCursor = databasetv.rawQuery("SELECT * FROM " + TABLE_TV + " WHERE tvtitles=?", new String[]{title});

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

                MovieParcelable data = getIntent().getParcelableExtra(EXTRA_SEARCHTV);
                String title = data.getTvshowTites();
                String detail = data.getTvshowOverview();
                String photo = data.getTvPhoto();

                ContentValues values = new ContentValues();
                values.put(TVTITLE, title);
                values.put(TVDESCRIPTION, detail);
                values.put(TVPHOTO, photo);

                databasetv = databaseHelpertv.getReadableDatabase();

                Cursor mCursortv = databasetv.rawQuery("SELECT * FROM " + TABLE_TV + " WHERE tvtitles=? AND tvdescriptions=?", new String[]{title,detail});

                if (mCursortv.moveToFirst())
                {
                    Toast.makeText(this, "Film sudah terdaftar dalam list favorite", Toast.LENGTH_SHORT).show();
                    finish();
                    /* record exist */
                }
                else
                {
                    getContentResolver().insert(CONTENT_URI_TV, values);
                    Toast.makeText(this, "Menambahkan ke favorit", Toast.LENGTH_SHORT).show();
                    /* record not exist */
                }
                mCursortv.close();

                item.setIcon(R.drawable.ic_favorite_red_24dp);
        }
        return super.onOptionsItemSelected(item);
    }
}
