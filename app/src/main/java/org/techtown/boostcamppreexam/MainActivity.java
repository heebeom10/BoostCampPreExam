package org.techtown.boostcamppreexam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private final String targetUrl = "https://openapi.naver.com/v1/search/movie.json?query=";
    private final String display = "&display=100";
    private MovieListApdater adapter;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.search_edit_text);
        Button searchButton = findViewById(R.id.search_btn);
        RecyclerView recyclerView = findViewById(R.id.movie_list_recycler_view);

        adapter = new MovieListApdater(MainActivity.this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adapter.clearItem();
                    adapter.notifyDataSetChanged();
                    String keyWord = searchEditText.getText().toString();   //검색어
                    keyWord = URLEncoder.encode(keyWord, "UTF-8");
                    String resultUrl = targetUrl + keyWord + display;      //요청할 url
                    new SearchForinfo(resultUrl).execute();

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        adapter.setItemClick(new OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                String link = adapter.getItem(position).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(link));

                startActivity(intent);
            }
        });

    }
    public class SearchForinfo extends AsyncTask<String, Void, JSONObject> {

        private String resultUrl;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        SearchForinfo(String resultUrl) {
            this.resultUrl = resultUrl;
        }

        @Override
        protected void onPreExecute() {
           progressDialog.setMessage("잠시만 기다려주세요...");
           progressDialog.show();
           super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... str) {
            try {
                URL url = new URL(resultUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                final String clientID = "biQShoR6d5dr2BKIimvI";
                httpURLConnection.setRequestProperty("X-Naver-Client-Id", clientID);
                final String secretKey = "tu93gFne_T";
                httpURLConnection.setRequestProperty("X-Naver-Client-Secret", secretKey);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new JSONObject(stringBuilder.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            try{
                JSONArray jsonArray = result.getJSONArray("items");
                if(jsonArray.length() == 0){
                    Toast.makeText(getApplicationContext(),"검색결과가 존재하지 않습니다.",Toast.LENGTH_LONG).show();
                }
                else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String title = jsonObject.getString("title");
                        String image = jsonObject.getString("image");
                        String link = jsonObject.getString("link");
                        String date = jsonObject.getString("pubDate");
                        String director = jsonObject.getString("director");
                        String actor = jsonObject.getString("actor");
                        int userRating = jsonObject.getInt("userRating");

                        adapter.addItem(new Movie(title, actor, director, userRating, link, image, date));
                    }
                }
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(searchEditText.getWindowToken(),0);
                progressDialog.dismiss();
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
