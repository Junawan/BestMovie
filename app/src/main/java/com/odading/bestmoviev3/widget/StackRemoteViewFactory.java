package com.odading.bestmoviev3.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.item.WidgetItem;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.PHOTO;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<WidgetItem> widgetItems = new ArrayList<>();
    private Context context;
    public static final String EXTRA_WIDGET = "extra_widget";
    private Cursor cursor;
    private int mCount = 0;

    private int mAppWidgetId;

    private int mID;
    private String mPicture;

    StackRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        Uri uri = CONTENT_URI;
        String[] projection = {_ID, PHOTO};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            mID = cursor.getInt(cursor.getColumnIndex(_ID));
            mPicture = cursor.getString(cursor.getColumnIndex(PHOTO));

            widgetItems.add(new WidgetItem(mID, mPicture));
            mCount = mCount + 1;

        }

        cursor.close();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        widgetItems.clear();
        cursor.close();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bundle extras = new Bundle();

        extras.putInt(_ID, widgetItems.get(position).id);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.stack_widget_layout, fillInIntent);

        InputStream is;
        try {
            System.out.println("Loading view " + position);

            Bitmap bit = Picasso.get().load(widgetItems.get(position).photo).get();

            rv.setImageViewBitmap(R.id.imageView, bit);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
