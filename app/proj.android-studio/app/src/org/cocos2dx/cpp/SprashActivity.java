package org.cocos2dx.cpp;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import org.cocos2dx.cluburo.R;

import java.io.IOException;
import java.io.InputStream;

public class SprashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(ExtraLayout.getInstance(ExtraLayout.class).getParenetView(R.layout.sprash_view));
        ImageView image = (ImageView) findViewById(R.id.sprashImage);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        AssetManager mngr = this.getAssets();
        try {
            InputStream is = mngr.open("sprash.png");
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            Bitmap newbitmap = ExtraLayout.getInstance(ExtraLayout.class).resizeBaseBitmap(bitmap);
            image.setImageBitmap(newbitmap);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        scesuleNextActivity();
    }

    private void scesuleNextActivity(){
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //次のactivityを実行
                Intent intent = new Intent(SprashActivity.this, TitleActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        });
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
