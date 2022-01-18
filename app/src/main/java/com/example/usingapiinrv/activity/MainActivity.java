package com.example.usingapiinrv.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.usingapiinrv.R;
import com.example.usingapiinrv.adapter.PlayerAdapter;
import com.example.usingapiinrv.helper.ApiClient;
import com.example.usingapiinrv.helper.ApiInterface;
import com.example.usingapiinrv.helper.ServerUrl;
import com.example.usingapiinrv.model.Player;
import com.example.usingapiinrv.model.PlayerData;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.FuelKt;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements ServerUrl {

    private Context context;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // apiUsingAsyncHttp();
        // apiUsingFuel();
        // apiUsingVolley();
        // apiUsingOkHttp();
         apiUsingRetrofit();

    }


    private void initView() {
        context = this;
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
    }


    private void refreshAdapter(List<Player> players) {
        PlayerAdapter adapter = new PlayerAdapter(context, players);
        recyclerView.setAdapter(adapter);
    }


    private void processWithResponse(final PlayerData playerData) {
        progressBar.setVisibility(View.GONE);

        String message = playerData.getMessage();
        fireToast(message);
        List<Player> players = playerData.getData();
        refreshAdapter(players);
    }


    private void fireToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    private void apiUsingAsyncHttp() {
        progressBar.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();

        client.post(SERVER_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("@@@", "@@@ onFailure: " + throwable);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("@@@", "@@@ onSuccess: " + statusCode);
                PlayerData playerData = new Gson().fromJson(responseString, PlayerData.class);
                processWithResponse(playerData);
            }
        });
    }


    private void apiUsingFuel() {
        progressBar.setVisibility(View.VISIBLE);

    }


    private void apiUsingVolley() {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest strReq = new StringRequest(SERVER_URL, (response -> {
            Log.d("@@@", "@@@ onResponse: " + response);

            PlayerData playerData = new Gson().fromJson(response, PlayerData.class);
            processWithResponse(playerData);
        }), (error -> {
            Log.d("@@@", "@@@ onError: " + error);

            progressBar.setVisibility(View.INVISIBLE);
        }));

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(strReq);
    }


    private void apiUsingOkHttp() {
        progressBar.setVisibility(View.VISIBLE);

        Request request = new Request.Builder().url(SERVER_URL).build();
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("@@@", "@@@ onFailure: " + e.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resp = response.body().string();

                runOnUiThread(() -> {
                    Log.d("@@@", "@@@ onResponse: " + resp);
                    PlayerData playerData = new Gson().fromJson(resp, PlayerData.class);
                    processWithResponse(playerData);
                });
            }
        });

    }


    private void apiUsingRetrofit() {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);

        retrofit2.Call<PlayerData> call = apiInterface.loadData();
        call.enqueue(new retrofit2.Callback<PlayerData>() {
            @Override
            public void onResponse(retrofit2.Call<PlayerData> call, retrofit2.Response<PlayerData> response) {
                Log.d("@@@", "@@@ onResponse:" + response.message());
                processWithResponse(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<PlayerData> call, Throwable t) {
                Log.d("@@@", "@@@ onFailure:" + t.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}