package com.gaofh.myweather.model;

public class Weather {
    private String status="";   //代表请求的状态，ok表示成功
    private String basic="";    //包含城市的一些基本信息
    private String api="";   //包含当前空气质量情况
    private String now="";   //包含当前的天气信息
    private String suggestions="";  //包含一些天气相关的生活建议
    private String daily_forecast="";   //包含未来几天的天气信息
    public void setStatus(String status){
        this.status=status;
    }
    public void setBasic(String basic){
        this.basic=basic;
    }
    public void setApi(String api){
        this.api=api;
    }
    public void setNow(String now){
        this.now=now;
    }
    public void setSuggestions(String suggestions){
        this.suggestions=suggestions;
    }
    public void setDaily_forecast(String daily_forecast){
        this.daily_forecast=daily_forecast;
    }
    public String getStatus(){
        return status;
    }
    public String getBasic(){
        return basic;
    }
    public String getApi(){
        return api;
    }
    public String getNow(){
        return now;
    }
    public String getSuggestions(){
        return suggestions;
    }
    public String getDaily_forecast(){
        return daily_forecast;
    }
}
