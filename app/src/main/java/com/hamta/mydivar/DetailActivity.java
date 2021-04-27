package com.hamta.mydivar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hamta.mydivar.Model.SliderItem;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView title;
    TextView desc;
    TextView price;
    TextView date;
    TextView city;
    LinearLayout linearMap;
    ScrollView sv;
    Typeface[] tf = new Typeface[2];

    private MapView mMapView;
    private MapboxMap mMapboxMap;

    SliderView sliderView;
    private SliderAdapterExample adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getResources().getString(R.string.access_token));

        setContentView(R.layout.activity_detail);
        title = (TextView) findViewById(R.id.txt_detail_title);
        desc = (TextView) findViewById(R.id.txt_detail_desc);
        price = (TextView) findViewById(R.id.txt_detail_price);
        date = (TextView) findViewById(R.id.txt_detail_date);
        city = (TextView) findViewById(R.id.txt_detail_city);
        sliderView = (SliderView) findViewById(R.id.imageSlider);
        linearMap = (LinearLayout) findViewById(R.id.ln_detail_mapView);
        sv = (ScrollView) findViewById(R.id.sv_detail);


        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
            }
        });

        tf[0] = Typeface.createFromAsset(getAssets(),
                "fonts/IRANSansBold.ttf");

        tf[1] = Typeface.createFromAsset(getAssets(),
                "fonts/IRANSansRegular.ttf");

        Intent intent = getIntent();
        String mytitle = intent.getExtras().getString("Title");
        String mydesc = intent.getExtras().getString("Desc");
        String myprice = intent.getExtras().getString("Price");
        String mydate = intent.getExtras().getString("Date");
        String mycity = intent.getExtras().getString("City");
        String mylatitude = intent.getExtras().getString("latitude");
        String mylongitude = intent.getExtras().getString("longitude");
//        String url = intent.getExtras().getString("URL");
        List<String> myImagesList = intent.getStringArrayListExtra("webImagesUrlList");

        title.setTypeface(tf[0]);
        desc.setTypeface(tf[1]);
        title.setText(mytitle);
        desc.setText(mydesc);
        if(!myprice.equals("null")){
            price.setText(myprice);
        }else{
            price.setText("");
        }

        if(mycity != null){
            city.setText(mycity);
        } else{
            city.setText("");
        }
        if(mydate != null) {
            date.setText(mydate);
        }

        List<SliderItem> mSliderItem = new ArrayList<>();
        for(int i=0; i<myImagesList.size(); i++){
            SliderItem sliderItem = new SliderItem();
            sliderItem.setImageUrl(myImagesList.get(i));
            mSliderItem.add(sliderItem);
        }
        adapter.renewItems(mSliderItem);

        if (mylatitude != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(Double.parseDouble(mylatitude), Double.parseDouble(mylongitude)))
                    .zoom(16)
                    .build();

            mMapView = findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);
            mMapView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            sv.requestDisallowInterceptTouchEvent(true);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            sv.requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                    return mMapView.onTouchEvent(event);
                }
            });
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                    mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

                            // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                            mMapboxMap = mapboxMap;

                            mMapboxMap.setCameraPosition(cameraPosition);

                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(mylatitude), Double.parseDouble(mylongitude)))
                            );
                        }
                    });
                }
            });

        } else{
            linearMap.setVisibility(View.GONE);
        }

    }
}
