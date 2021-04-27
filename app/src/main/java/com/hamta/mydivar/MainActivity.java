package com.hamta.mydivar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.ferfalk.simplesearchview.utils.DimensUtils;
import com.hamta.mydivar.Model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerRefreshLayout refreshLayout;
    private MyAdapter adapter;
    private boolean searchFlag;
    private List<ListItem> listItems;
    private List<ListItem> listItemsPre = new ArrayList<>();

    private LottieAnimationView progressBar;
    private SimpleSearchView searchView;
    public static final int EXTRA_REVEAL_CENTER_PADDING = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.searchView);
        refreshLayout = findViewById(R.id.refresh_layout);
        progressBar = (LottieAnimationView) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("Testt", listItems.get(position).getDate());
                        String myTitle = listItems.get(position).getTitle();
                        String myDesc = listItems.get(position).getDesc();
                        String myUrl = listItems.get(position).getUrl();
                        String myPrice = listItems.get(position).getPrice();
                        String myDate = listItems.get(position).getDate();
                        String myCity = listItems.get(position).getCity();

                        List<String> myImagesList = listItems.get(position).getWebImagesUrlList();

                        Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                        i.putExtra("Title", myTitle);
                        i.putExtra("Desc", myDesc);
                        i.putExtra("URL", myUrl);
                        i.putExtra("Price", myPrice);
                        i.putExtra("Date", myDate);
                        i.putExtra("City", myCity);
                        i.putExtra("latitude", listItems.get(position).getLatitude());
                        i.putExtra("longitude", listItems.get(position).getLongitude());
                        i.putStringArrayListExtra("webImagesUrlList", (ArrayList<String>) myImagesList);


                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Log.i("TestLong", String.valueOf(position));
                    }
                })
        );

        listItems = new ArrayList<>();

        adapter = new MyAdapter(listItems, this, getAssets());
        recyclerView.setAdapter(adapter);

        getJasonData();

        searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SimpleSearchView", "Submit:" + query);
                if(listItems.size() != 0){
                    List<ListItem> searchedItems = new ArrayList<>();
                    for(int k=0; k < listItems.size(); k++){
                        if (listItems.get(k).getTitle().toLowerCase().contains(query.toLowerCase())){
                            searchedItems.add(listItems.get(k));
                        }
                    }
                    if(searchedItems.size() != 0){
                        searchFlag = true;
                        listItems.clear();
                        listItems.addAll(searchedItems);
                        adapter.notifyDataSetChanged();
                    }
//                    else {
//
//                    }

                } else{
                    Toast.makeText(getApplicationContext(), "Not Found!",
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SimpleSearchView", "Text changed:" + newText);
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                Log.d("SimpleSearchView", "Text cleared");
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJasonData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        setupSearchView(menu);
        return true;
    }

    private void setupSearchView(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        Point revealCenter = searchView.getRevealAnimationCenter();
        revealCenter.x -= DimensUtils.convertDpToPx(EXTRA_REVEAL_CENTER_PADDING, this);
    }

    @Override
    public void onBackPressed() {
        if (searchView.onBackPressed()) {
            return;
        }
        if (searchFlag){
            searchFlag = false;
            listItems.clear();
            listItems.addAll(listItemsPre);
            adapter.notifyDataSetChanged();
            return;
        }

        super.onBackPressed();
    }

    public void getJasonData(){
        AndroidNetworking.get("http://185.208.172.104:5000/")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listItems.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject a = (JSONObject) response.get(i);

                                ListItem alb = new ListItem(
                                        a.getString("title").split("\\|")[0], a.getString("description"));

                                try {
                                    alb.setUrl(a.getString("thumbnail"));
                                } catch (Exception ex) {
                                }
                                try {
                                    alb.setCity(a.getString("city"));
                                } catch (Exception ex) {
                                }
                                try {
                                    alb.setPrice(a.getString("price"));
                                } catch (Exception ex) {
                                }
                                try {
                                    alb.setDate(a.getString("date"));
                                } catch (Exception ex) {
                                }

                                try {
                                    alb.setLatitude(a.getString("latitude"));
                                    alb.setLongitude(a.getString("longitude"));
                                } catch (Exception ex) {
                                }

                                try {
                                    JSONArray b = (JSONArray) a.getJSONArray("web_images");
                                    List<String> webImagesList = new ArrayList<>();
                                    for (int j = 0; j < b.length(); j++) {
                                        JSONArray c = (JSONArray) b.getJSONArray(j);
                                        for (int k = 0; k < c.length(); k++) {
                                            if (k == 1) {
                                                JSONObject d = (JSONObject) c.getJSONObject(k);
                                                webImagesList.add(d.getString("src"));
                                            }
                                        }
                                    }
                                    alb.setWebImagesUrlList(webImagesList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                listItems.add(alb);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listItemsPre.addAll(listItems);
                        progressBar.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.i("TestError", error.toString());
                        refreshLayout.setRefreshing(false);
                        // handle error
                    }
                });
    }

}