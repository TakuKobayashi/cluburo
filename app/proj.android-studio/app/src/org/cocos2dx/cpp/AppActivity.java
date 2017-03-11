/****************************************************************************
Copyright (c) 2015 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import org.cocos2dx.lib.Cocos2dxActivity;

import java.io.IOException;

public class AppActivity extends Cocos2dxActivity {
    private static int PERMISSION_REQUEST_CODE = 1;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        ApplicationHelper.requestPermissions(this, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if(ApplicationHelper.hasSelfPermission(this, Manifest.permission.CAMERA)){
                startCamera();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ApplicationHelper.hasSelfPermission(this, Manifest.permission.CAMERA)){
            startCamera();
        }
    }

    private void startCamera(){
        if(mCamera == null) {
            mCamera = Camera.open();
            Camera.Parameters p = mCamera.getParameters();
            // Lollipop以降はこうしないとフラッシュライトが炊けないらしい
            if(Build.VERSION.SDK_INT >= 21) {
                SurfaceTexture preview = new SurfaceTexture(0);
                try {
                    mCamera.setPreviewTexture(preview);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(p);
            mCamera.startPreview();
        }
    }

    private void stopCamera(){
        if(mCamera != null) {
            Camera.Parameters p = mCamera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(p);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCamera();
    }
}
