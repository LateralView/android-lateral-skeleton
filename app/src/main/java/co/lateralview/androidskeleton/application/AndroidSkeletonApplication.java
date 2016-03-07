package co.lateralview.androidskeleton.application;

import android.app.Application;

public class AndroidSkeletonApplication extends Application
{
	protected static AndroidSkeletonApplication sInstance;

	@Override
	public void onCreate()
	{
		super.onCreate();
		sInstance = this;
	}

	public static AndroidSkeletonApplication getInstance()
	{
		return sInstance;
	}

}
