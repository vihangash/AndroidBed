package com.app.androidbed.MedecineInfo;

public class Single_Remider_Item {

    private int mImageResource;
    private String mText1;
    private String mtext2;
    private String reminderId;

    public Single_Remider_Item(String remindId,int imageResource, String text1, String text2){
        reminderId = remindId;
        mImageResource = imageResource;
        mText1 = text1;
        mtext2 = text2;
    }

    public String getReminderId() {
        return reminderId;
    }

    public int getmImageResource(){
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getMtext2() {
        return mtext2;
    }
}
