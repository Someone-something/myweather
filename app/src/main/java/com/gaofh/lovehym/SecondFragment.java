package com.gaofh.lovehym;

//import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.fragment.NavHostFragment;

import com.gaofh.lovehym.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    public static final String RightFragment="右边的布局";
    private Button button;
    private IntentFilter intentFilter;
    private LocalBroadcastReceiver localBroadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(RightFragment,"这是onAttach方法");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(RightFragment,"这是onCreate方法");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        FragmentTestActivity fragmentTestActivity=(FragmentTestActivity) getActivity();
        View view=inflater.inflate(R.layout.fragment_second,container,false);
        button=(Button)view.findViewById(R.id.button_second);
        localBroadcastReceiver=new LocalBroadcastReceiver();
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.gaofh.lovehym.LocalBroadcastReceiver");
        localBroadcastManager=LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(localBroadcastReceiver,intentFilter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent("com.gaofh.loveHym.MyBroadcastReceiver");
//                /**
//                 * 安卓8以上，发送广播，必须要设置包名才能接收；
//                 */
//                intent.setPackage("com.gaofh.lovehym");
////                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
//                getActivity().sendBroadcast(intent);
                Intent intent=new Intent("com.gaofh.lovehym.LocalBroadcastReceiver");
                intent.setPackage("com.gaofh.lovehym");
                localBroadcastManager.sendBroadcast(intent);
                Log.d("gaofh","点了发送广播的按钮");
            }
        });
        Log.d(RightFragment,"这是onCreateView方法");
        return view;
    }
    public class LocalBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            Toast.makeText(context, "我收到了一条本地广播", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(RightFragment,"这是onActivityCreated方法");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(RightFragment,"这是onStart方法");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(RightFragment,"这是onResume方法");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(RightFragment,"这是onPause方法");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(RightFragment,"这是onStop方法");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(RightFragment,"这是onDestroyView方法");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(RightFragment,"这是onDestroy方法");
        localBroadcastManager.unregisterReceiver(localBroadcastReceiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(RightFragment,"这是onDetach方法");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

}