package org.cocos2dx.cpp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TitleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        AssetManager mngr = this.getAssets();
        try {
            String[] imagePathes = mngr.list("startscene");
            for (String path : imagePathes) {
                Log.d(Config.TAG, path);
               //InputStream is = mngr.open(e.getValue() + "/" + path);
               //loadingImages.add(BitmapFactory.decodeStream(is, null, options));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
