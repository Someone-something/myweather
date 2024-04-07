package com.gaofh.lovehym;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsContentFragment extends Fragment {
    private TextView news_title;
    private TextView news_content;
    private View view;
    private NewsAdapter newsAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        news_title=(TextView) getActivity().findViewById(R.id.fragment_news_title);
//        news_content=(TextView) getActivity().findViewById(R.id.fragment_news_content);
        view=inflater.inflate(R.layout.fragment_news_content,container,false);
        return view;
    }
    public void refresh(String newsTitle,String newsContent){
//        View viewVisibility=view.findViewById(R.id.visibility_layout);
//        viewVisibility.setVisibility(View.VISIBLE);
        TextView news_title=(TextView) view.findViewById(R.id.fragment_news_title);
        TextView news_content=(TextView) view.findViewById(R.id.fragment_news_content);
        news_title.setText(newsTitle);
        news_content.setText(newsContent);
    }

}
