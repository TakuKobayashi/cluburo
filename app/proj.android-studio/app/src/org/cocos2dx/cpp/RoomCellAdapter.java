package org.cocos2dx.cpp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.cocos2dx.cluburo.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomCellAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<RoomModel> mRooms = new ArrayList<RoomModel>();
    private HashMap<String, Bitmap> mImages = new HashMap<String,Bitmap>();

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------

    public RoomCellAdapter(Activity activity, HashMap<String, Bitmap> images) {
        mActivity = activity;
        mImages = images;
    }

    public void addRooms(Collection<RoomModel> rooms){
        mRooms.addAll(rooms);
        this.notifyDataSetChanged();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------

    public int getCount() {
        return mRooms.size();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------

    public Object getItem(int position) {
        return mRooms.get(position);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mActivity.getLayoutInflater().inflate(R.layout.room_cell_view,null);
        }
        Bitmap bgImage = mImages.get("base_color01.png");

        ImageView bg = (ImageView) convertView.findViewById(R.id.roomCellBg);
        bg.setImageBitmap(bgImage);

        ImageView roomIcon = (ImageView) convertView.findViewById(R.id.roomIconView);
        roomIcon.setImageBitmap(mImages.get("thumbnail01.png"));

        ImageView liveIcon = (ImageView) convertView.findViewById(R.id.roomLiveIcon);
        liveIcon.setImageBitmap(mImages.get("icon_live.png"));

        TextView title = (TextView) convertView.findViewById(R.id.roomTitleText);
        title.setText(mRooms.get(position).name);

        TextView djText = (TextView) convertView.findViewById(R.id.roomDjText);
        djText.setText(mRooms.get(position).description);

        ImageView roomDetailButton = (ImageView) convertView.findViewById(R.id.roomDetailButton);
        roomDetailButton.setImageBitmap(mImages.get("button_.png"));
        roomDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, RoomDetailActivity.class);
                mActivity.startActivity(intent);
            }
        });

        ImageView roomJoinButton = (ImageView) convertView.findViewById(R.id.roomJoinButton);
        roomJoinButton.setImageBitmap(mImages.get("button_entry.png"));
        roomJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AppActivity.class);
                mActivity.startActivity(intent);
            }
        });
        return convertView;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void release(){
        for(Map.Entry<String, Bitmap> image : mImages.entrySet()){
            image.getValue().recycle();
        }
        mImages.clear();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------
}