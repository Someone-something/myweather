package com.gaofh.lovehym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    int resourceId;
    public  NewsAdapter(Context context, int textViewResourceId, List<News> object){
            super(context,textViewResourceId,object);
            resourceId=textViewResourceId;
    }
    public View getView(int position, View convertView,ViewGroup viewGroup){
        News news=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view=(View) LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView) view.findViewById(R.id.news_title);
            viewHolder.textView.setText(news.getTitle());
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
            viewHolder.textView.setText(news.getTitle());
        }
        return view;


    }
    class ViewHolder{
        TextView textView;

    }

}
