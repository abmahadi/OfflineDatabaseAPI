package com.example.offlinedatabaseapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static android.content.ContentValues.TAG;

public class CoustomerDatabase extends SQLiteOpenHelper {
    public CoustomerDatabase(@Nullable Context context) {
        super(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY);
        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL(Constants.DATABASE.DROP_QUERY);
        this.onCreate(db);

    }

    public void addCoustomer(Coustomer coustomer) {
        Log.d(TAG, "Values Got " + coustomer.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.DATABASE.COUSTOMER_ID, coustomer.getId());

        values.put(Constants.DATABASE.NAME, coustomer.getName());
        values.put(Constants.DATABASE.MOBILE, coustomer.getMobile());


        try {
            db.insert(Constants.DATABASE.TABLE_NAME, null, values);
        } catch (Exception e) {

        }
        db.close();
    }

    public void fetchCoustomer(CoustomerFetchListener listener) {
        CoustomerFetcher fetcher = new CoustomerFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }

    public class CoustomerFetcher extends Thread {

        private final CoustomerFetchListener mListener;
        private final SQLiteDatabase mDb;

        public CoustomerFetcher(CoustomerFetchListener listener, SQLiteDatabase db) {
            mListener = listener;
            mDb = db;
        }


        @Override
        public void run() {
            Cursor cursor = mDb.rawQuery(Constants.DATABASE.GET_FLOWERS_QUERY, null);

            final List<Coustomer> coustomerList = new ArrayList<>();

            if (cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    do {
                        Coustomer coustomer = new Coustomer();
                       //coustomer.setFromDatabase(true);
                        coustomer.setName(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.NAME)));
                        coustomer.setMobile(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.MOBILE))));

                        coustomerList.add(coustomer);
                        publishCoustomer(coustomer);

                    } while (cursor.moveToNext());
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverAllCoustomer(coustomerList);
                    mListener.onHideDialog();
                }
            });
        }

        public void publishCoustomer(final Coustomer coustomer) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverCoustomer(coustomer);
                }
            });
        }
    }
    }


}
