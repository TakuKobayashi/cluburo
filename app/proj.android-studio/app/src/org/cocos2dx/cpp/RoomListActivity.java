package org.cocos2dx.cpp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.cocos2dx.cluburo.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomListActivity extends Activity {
    private RoomCellAdapter mRoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_list_view);
        ListView roomListView = (ListView) findViewById(R.id.roomListView);

        HashMap<String, Bitmap> imageHash = new HashMap<String, Bitmap>();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        AssetManager mngr = this.getAssets();
        try {
            String[] imageImages = mngr.list("roomlistscene");
            for (String name : imageImages) {
                InputStream is = mngr.open("roomlistscene/" + name);
                Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
                imageHash.put(name, ExtraLayout.getInstance(ExtraLayout.class).resizeBaseBitmap(bitmap));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        mRoomAdapter = new RoomCellAdapter(this, imageHash);
        roomListView.setAdapter(mRoomAdapter);

        HashMap<String, String> params = new HashMap<String, String>();
        httpRequest(Request.Method.GET, Config.ROOT_URL + "/api/rooms", params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ArrayList<RoomModel> list = new ArrayList<RoomModel>();
                RoomModel[] rooms = gson.fromJson(response, RoomModel[].class);
                for(RoomModel room : rooms){
                    list.add(room);
                }
                mRoomAdapter.addRooms(list);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRoomAdapter.release();
    }

    private void httpRequest(int method, String url , final Map<String, String> params, Response.Listener response){
        Log.d(Config.TAG, "url:" + url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(method ,url, response, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d(Config.TAG, "error:" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };
        queue.add(request);
    }
}
