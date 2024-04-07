package com.gaofh.lovehym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;


public class MsgAdapter extends ArrayAdapter<Msg> {
   private int resourceId;
   public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects){
      super(context,textViewResourceId,objects);
      resourceId=textViewResourceId;
   }
   public View getView(int position, View convertView, ViewGroup parent) {
      Msg msg = getItem(position);
      View view;
      ViewHolder viewHolder;
      if (convertView == null) {
         view = LayoutInflater.from(getContext()).inflate(resourceId, null);
         viewHolder=new ViewHolder();
         viewHolder.leftLayout=(LinearLayout) view.findViewById(R.id.left_layout);
         viewHolder.rightLayout=(LinearLayout) view.findViewById(R.id.right_layout);
         viewHolder.leftMsg=(TextView) view.findViewById(R.id.left_msg);
         viewHolder.rightMsg=(TextView) view.findViewById(R.id.right_msg);
         view.setTag(viewHolder);
      }else {
         view=convertView;
         viewHolder=(ViewHolder) view.getTag();
      }
      if(msg.getType()==Msg.TYPE_RECEIVED){
         //如果是收到消息，则显示左边的布局，右边的布局隐藏
         viewHolder.leftLayout.setVisibility(View.VISIBLE);
         viewHolder.rightLayout.setVisibility(View.GONE);
         viewHolder.leftMsg.setText(msg.getContent());
      }else {
         //如果是收到消息，则显示右边的布局，左边的布局隐藏
         viewHolder.leftLayout.setVisibility(View.GONE);
         viewHolder.rightLayout.setVisibility(View.VISIBLE);
         viewHolder.rightMsg.setText(msg.getContent());
      }
return view;
   }
     class ViewHolder{
      LinearLayout leftLayout;
      LinearLayout rightLayout;
      TextView leftMsg;
      TextView rightMsg;
}
}
