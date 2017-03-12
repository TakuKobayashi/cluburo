package org.cocos2dx.cpp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import org.cocos2dx.cluburo.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class TitleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(ExtraLayout.getInstance(ExtraLayout.class).getParenetView(R.layout.title_view));

        HashMap<String, Bitmap> imageHash = new HashMap<String, Bitmap>();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        AssetManager mngr = this.getAssets();
        try {
            String[] imageImages = mngr.list("startscene");
            for (String name : imageImages) {
               Log.d(Config.TAG, name);
               InputStream is = mngr.open("startscene/" + name);
               Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
               imageHash.put(name, ExtraLayout.getInstance(ExtraLayout.class).resizeBaseBitmap(bitmap));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ImageView bg = (ImageView) findViewById(R.id.titleBg);
        bg.setImageBitmap(imageHash.get("back_color_black.png"));
        ImageView titleLogo = (ImageView) findViewById(R.id.titleLogoView);
        titleLogo.setImageBitmap(imageHash.get("logo.png"));
        ImageView startLogo = (ImageView) findViewById(R.id.startLogoView);
        startLogo.setImageBitmap(imageHash.get("background_start.png"));
        ImageView startButton = (ImageView) findViewById(R.id.startButtonView);
        startButton.setImageBitmap(imageHash.get("button_start.png"));
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, RoomListActivity.class);
                startActivity(intent);
            }
        });
        ImageView footerIcon = (ImageView) findViewById(R.id.footerIconView);
        footerIcon.setImageBitmap(imageHash.get("icon.png"));
    }
}
