package com.gaofh.myweather.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gaofh.myweather.R;
import com.gaofh.myweather.db.City;
import com.gaofh.myweather.db.County;
import com.gaofh.myweather.db.Province;
import com.gaofh.myweather.util.HttpUtil;
import com.gaofh.myweather.util.LogUtil;
import com.gaofh.myweather.util.UrlUtil;
import com.gaofh.myweather.util.Utility;

import org.json.JSONException;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList=new ArrayList<>();
    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<County> countyList;
    /**
     * 选中的省份
     */
     private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 当前选择的级别
     */
    private int currentLevel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.choose_area_layout,container);
        titleText=(TextView) view.findViewById(R.id.title_text);
        backButton=(Button) view.findViewById(R.id.back_button);
        listView=(ListView) view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(i);
                    //执行查询当前选中的省份对应的城市列表
                   queryCities();
                }else if(currentLevel==LEVEL_CITY){
                    selectedCity=cityList.get(i);
                    //执行查询当前选中的城市对应的县、区列表
                    queryCounties();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel==LEVEL_COUNTY){
                    //查询市列表
                 queryCities();
                }else if(currentLevel==LEVEL_CITY){
                    //查询省列表
                   queryProvinces();
                }
            }
        });
        //执行查询省的列表
        queryProvinces();
    }
    /**
     * 查询全国的省份，数据库有从数据库查，没有从服务器取
     */
    public void queryProvinces(){
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList= DataSupport.findAll(Province.class);
        if(provinceList.size()>0){
            dataList.clear();
           for(Province province:provinceList){
               dataList.add(province.getProvinceName());
           }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_PROVINCE;
        }else {
            //执行从服务器查询的方法
            queryFromServer(UrlUtil.provinceUrl,"province");
        }
    }
    /**
     * 查询选中省份的所有城市，数据库有从数据库查，没有从服务器取
     */
    public void queryCities(){
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList=DataSupport.where("provinceid=?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size()>0){
            for(City city:cityList){
                dataList.clear();
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_CITY;
        }else {
            int provinceCode=selectedProvince.getProvinceCode();
            String address= UrlUtil.provinceUrl+"/"+provinceCode;
            //执行从服务器查询的方法
            queryFromServer(address,"city");
        }
    }
    /**
     * 查询选中省份的所有城市，数据库有从数据库查，没有从服务器取
     */
    public void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList=DataSupport.where("cityid=?",String.valueOf(selectedCity.getId())).find(County.class);
        if(countyList.size()>0){
            for (County county:countyList){
                dataList.clear();
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_COUNTY;
        }else {
            int provinceCode=selectedProvince.getProvinceCode();
            int cityCode=selectedCity.getCityCode();
            String address=UrlUtil.provinceUrl+"/"+provinceCode+"/"+cityCode;
            //执行从服务器查询的方法
             queryFromServer(address,"county");
        }
    }
    /**
     * 根据传入的地址，从服务器查询省市县的数据
     */
    public void queryFromServer(String address,final String type){
        Log.d("GAO----查询服务器列表",address);
        //这里要放一个加载进度框
          showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //执行关掉加载框的方法
                         closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              String responseText=response.body().string();
              boolean result=false;
              if("province".equals(type)){
                  try {
                      result= Utility.handleProvinceResponse(responseText);
                  } catch (JSONException e) {
                      throw new RuntimeException(e);
                  }
              }else if ("city".equals(type)){
                       result=Utility.handleCityResponse(responseText,selectedProvince.getId());
              }else if ("county".equals(type)){
                     result=Utility.handleCountyResponse(responseText,selectedCity.getId());
                  LogUtil.d("GAO----查询服务器列表","结果是"+result);
              }
              if (result){
                  getActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          //执行关掉加载框的方法
                           closeProgressDialog();
                          if("province".equals(type)){
                              queryProvinces();
                          }else if("city".equals(type)){
                              queryCities();
                          }else if("county".equals(type)){
                              queryCounties();
                          }
                      }
                  });
              }
            }
        });
    }
    /**
     * 显示加载进度框
     */
    public void showProgressDialog(){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("数据加载中");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭加载进度框
     */
    public void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
