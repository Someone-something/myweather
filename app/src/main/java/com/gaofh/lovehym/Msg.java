package com.gaofh.lovehym;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Msg implements Parcelable {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SEND=1;
    private String content;
    private int type;
    public  Msg(String content,int type){
        this.content=content;
        this.type=type;
    }
    public Msg(){

    }

    protected Msg(Parcel in) {
        content = in.readString();
        type = in.readInt();
    }

    public static final Creator<Msg> CREATOR = new Creator<Msg>() {
        @Override
        public Msg createFromParcel(Parcel in) {
            Msg msg=new Msg();
            msg.content=in.readString();
            msg.type=in.readInt();
            return msg;
        }

        @Override
        public Msg[] newArray(int size) {
            return new Msg[size];
        }
    };

    public String getContent(){
        return content;
    }
    public int getType(){
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
          parcel.writeString(content);
          parcel.writeInt(type);
    }
}
