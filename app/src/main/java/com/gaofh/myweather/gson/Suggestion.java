package com.gaofh.myweather.gson;

import com.google.gson.annotations.SerializedName;

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;
    @SerializedName("cw")
    public CarWash carWash;
    public Sport sport;
    public class Comfort{
        @SerializedName("comf")
        public String info;
    }
    public class CarWash{
        @SerializedName("cw")
        public String info;
    }
    public class Sport{
        @SerializedName("sport")
        public String info;
    }
}
