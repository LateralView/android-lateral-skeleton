package co.lateralview.androidskeleton.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import co.lateralview.androidskeleton.infrastructure.manager.InternetManager;

public class InternetReceiver extends BroadcastReceiver
{
	public InternetReceiver(InternetReceiverListener handler)
	{
		mReceiverHandler = handler;
	}

	InternetReceiverListener mReceiverHandler;

	public interface InternetReceiverListener
	{
		void onInternetServiceEnabled();

		void onInternetServiceDisabled();
	}

	@Override
	public void onReceive(final Context context, final Intent intent)
	{
		InternetManager internetService = new InternetManager(context);
		if (internetService.isOnline())
		{
			mReceiverHandler.onInternetServiceEnabled();
		} else
		{
			mReceiverHandler.onInternetServiceDisabled();
		}
	}

	public IntentFilter getIntentFilter()
	{
		IntentFilter locationServicesChangeFilter = new IntentFilter();

		locationServicesChangeFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

		return locationServicesChangeFilter;
	}


}
