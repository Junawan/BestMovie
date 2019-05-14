package com.odading.bestmoviev3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.fragment.FavoriteMoviefragment;
import com.odading.bestmoviev3.fragment.FavoriteTvFragment;
import com.odading.bestmoviev3.fragment.ListMovieFragment;
import com.odading.bestmoviev3.fragment.ListTvShowFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private Fragment fragment;
    private FrameLayout frameLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    setActinBarTitle(getResources().getString(R.string.title_movie));
                    frameLayout.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                    fragment = new ListMovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout_list_movie, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_tv_show:
                    setActinBarTitle(getResources().getString(R.string.title_tv_show));
                    frameLayout.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                    fragment = new ListTvShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout_list_movie, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_favorite:
                    setActinBarTitle(getResources().getString(R.string.title_favorite));
                    fragment = new FavoriteMoviefragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout_list_movie, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    tabLayout.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.container_layout_list_movie);

        tabLayout = findViewById(R.id.tab_layout);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getPosition();
                tab.getText();
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FavoriteMoviefragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_layout_list_movie, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        break;
                    case 1:
                        fragment = new FavoriteTvFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_layout_list_movie, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setVisibility(View.GONE);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_movie);
        }
    }



    private void setActinBarTitle(String titleBar) {
        getSupportActionBar().setTitle(titleBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_language_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int type = 0;

        switch (item.getItemId()) {
            case R.id.action_change_settings:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.search_movie:
                Intent intent1 = new Intent(MainActivity.this, SearchMovieActivity.class);
                startActivity(intent1);
                break;
            case R.id.search_tvshow:
                Intent intent2 = new Intent(MainActivity.this, SearchTvActivity.class);
                startActivity(intent2);
                break;
            case R.id.reminder:
                Intent intent3 = new Intent(MainActivity.this, SettingReminderActivity.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
    }
}
