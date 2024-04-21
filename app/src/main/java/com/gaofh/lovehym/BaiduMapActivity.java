package com.gaofh.lovehym;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.fragment.app.FragmentActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.map.MapController;

public class BaiduMapActivity extends Activity {
    private MapView mapView;
    private BaiduMapOptions options;
    private BaiduMap baiduMap;
    private MapView leftView;
    private MapView rightView;
    private BaiduMap leftBaiduMap;
    private LocationClient locationClient;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        options=new BaiduMapOptions();
//        options.mapType(BaiduMap.MAP_TYPE_SATELLITE);
//        mapView=new MapView(this,options);
//        baiduMap=mapView.getMap();
        MapStatus.Builder builder=new MapStatus.Builder();
        builder.zoom(22.0f);
//        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        setContentView(R.layout.baidu_map_layout);

        LatLng GEO_SHENZHEN=new LatLng(22.529597,114.034355);
        LatLng GEO_GANZHOU=new LatLng(25.83,114.93);
        //深圳在地图中心，log在左上角
        MapStatusUpdate leftStatus=MapStatusUpdateFactory.newLatLng(GEO_SHENZHEN);
       MapFragment leftMap=(MapFragment)getFragmentManager().findFragmentById(R.id.left_map);
       leftBaiduMap=leftMap.getBaiduMap();
       leftMap.getBaiduMap().setMapStatus(leftStatus);
       leftMap.getBaiduMap().setTrafficEnabled(true);
       leftMap.getBaiduMap().setCustomTrafficColor("#ffba0101", "#fff33131", "#ffff9e19", "#00000000");
       leftStatus=MapStatusUpdateFactory.zoomTo(13);
       leftMap.getBaiduMap().animateMapStatus(leftStatus);
       leftMap.getBaiduMap().setBaiduHeatMapEnabled(true);
       leftMap.getBaiduMap().setMyLocationEnabled(true);
       leftMap.getBaiduMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
       leftView=leftMap.getMapView();
       leftView.setLogoPosition(LogoPosition.logoPostionleftBottom);
       MyLocationConfiguration configuration=new MyLocationConfiguration(
               MyLocationConfiguration.LocationMode.NORMAL
               ,true
               , null
               ,0xAAFFFF88
               ,0xAA00FF00
       );
       configuration.setAnimation(true);
       leftBaiduMap.setMyLocationConfiguration(configuration);
       leftBaiduMap.setIndoorEnable(true);
        try {
            locationClient=new LocationClient(this);
            LocationClientOption option=new LocationClientOption();
            option.setOpenGps(true);
            option.setCoorType("bd09ll");
            option.setScanSpan(1000);
            locationClient.setLocOption(option);
            MyLocationListener listener=new MyLocationListener();
            locationClient.registerLocationListener(listener);
            locationClient.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       //赣州在地图中心，log在右上角
        MapStatusUpdate rightStatus=MapStatusUpdateFactory.newLatLng(GEO_GANZHOU);
        MapFragment rightMap=(MapFragment) getFragmentManager().findFragmentById(R.id.right_map);
        rightMap.getBaiduMap().setMapStatus(rightStatus);
        rightMap.getBaiduMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        rightView=rightMap.getMapView();
        rightView.setLogoPosition(LogoPosition.logoPostionRightBottom);
    }
    @Override
   public void onResume(){
        super.onResume();
        rightView.onResume();
        leftView.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
        rightView.onPause();
        leftView.onPause();
      }
    public void onDestroy(){
        locationClient.stop();
        leftBaiduMap.setMyLocationEnabled(false);
        try {
            rightView.onDestroy();
            leftView.onDestroy();
            leftView=null;
        }catch (Exception e){
         e.printStackTrace();
        }
        super.onDestroy();

    }
    public class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation==null||leftView==null){
                return;
            }else {
                MyLocationData data=new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())
                        .direction(bdLocation.getDirection())
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude()).build();
                leftBaiduMap.setMyLocationData(data);
            }
        }
    }
    }
