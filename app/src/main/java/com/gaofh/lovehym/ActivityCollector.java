package com.gaofh.lovehym;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activityList=new ArrayList<>();
    public static Activity mCurrentActivity;
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }
    public static void finishActivityAll(){
         for (Activity activity:activityList){
             if (!activity.isFinishing()){
                 activity.finish();
             }
         }
    }
    public static void setCurrentActivity(Activity activity){
        mCurrentActivity=activity;
    }
    public static Activity getmCurrentActivity(){
        return mCurrentActivity;
    }
}
