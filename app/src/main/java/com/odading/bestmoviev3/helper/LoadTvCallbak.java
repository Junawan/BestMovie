package com.odading.bestmoviev3.helper;

import android.database.Cursor;

public interface LoadTvCallbak {
    void preExecute();

    void postExecute(Cursor movie);
}
