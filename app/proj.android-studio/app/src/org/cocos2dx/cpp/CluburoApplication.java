package org.cocos2dx.cpp;

import android.app.Application;

public class CluburoApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ExtraLayout.getInstance(ExtraLayout.class).init(this);
		ExtraLayout.getInstance(ExtraLayout.class).setBaseDisplaySize(1080, 1920);
		ExtraLayout.getInstance(ExtraLayout.class).setDisplayPolicy(ExtraLayout.DISPLAY_POLICY_EXACT_FIT);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
