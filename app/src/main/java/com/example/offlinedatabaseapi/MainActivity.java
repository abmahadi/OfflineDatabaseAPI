package com.example.offlinedatabaseapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayoutMain;
    private RecyclerView mRecyclerView;
    private ArrayList<Coustomer> coustomers;
    RestManager mManager = new RestManager();
    CoustomerDatabase mDatabase = new CoustomerDatabase(this);
    ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
    private Adaptar mCoustomerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        RestManager mManager = new RestManager();
        CoustomerDatabase mDatabase = new CoustomerDatabase(this);

        loadCoustomerFeed();

    }

    private void loadCoustomerFeed() {
        ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Loading Coustomer Data...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);

        mCoustomerAdapter.reset();

        mDialog.show();

        getFeed();


        getFeedFromDatabase();

    }

    private void getFeed() {

        Call<List<Coustomer>> listCall = mManager.getCoustomerService().getAllCoustomers();
        listCall.enqueue(new Callback<List<Coustomer>>() {
            @Override
            public void onResponse(Call<List<Coustomer>> call, Response<List<Coustomer>> response) {

                if (response.isSuccessful()) {
                    List<Coustomer> coustomerList = response.body();

                    for (int i = 0; i < coustomerList.size(); i++) {
                        Coustomer coustomer = coustomerList.get(i);

                        SaveIntoDatabase task = new SaveIntoDatabase();
                        task.execute(coustomer);

                        mCoustomerAdapter.addCoustomer(coustomer);
                    }
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Coustomer>> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    public class SaveIntoDatabase extends AsyncTask<Coustomer, Void, Void> {


        private final String TAG = SaveIntoDatabase.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Coustomer... params) {

            Coustomer coustomer = params[0];

            try {

                mDatabase.addCoustomer(coustomer);

            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }




    }
    private void init() {
        coustomers = new ArrayList<>();
        swipeRefreshLayoutMain = findViewById(R.id.swaipe_layout);
        mRecyclerView = findViewById(R.id.mainActivity_RV);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mCoustomerAdapter = new Adaptar(this, coustomers);

        mRecyclerView.setAdapter(mCoustomerAdapter);
    }
    private void getFeedFromDatabase() {
        mDatabase.fetchCoustomer((CoustomerFetchListener) this);
    }
}
