package com.gaofh.lovehym;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class ThirdFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstancestate){
        FragmentTestActivity fragmentTestActivity=(FragmentTestActivity) getActivity();
        View view=layoutInflater.inflate(R.layout.fragment_third,viewGroup,false);
        return view;
    }
}
