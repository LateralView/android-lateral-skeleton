package co.lateralview.androidskeleton.ui.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SystemUtils
{

	public static void hideSoftKeyboard(Activity activity)
	{
		if (activity.getCurrentFocus() != null)
		{
			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
	}

	public static void hideSoftKeyboard(Activity activity, View view)
	{
		if (activity.getCurrentFocus() != null)
		{
			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public static void showSoftKeyboard(Context context)
	{
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	public static boolean checkNetworkConnection(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();

		if (netinfo != null && netinfo.isConnectedOrConnecting())
		{
			int networkType = netinfo.getType();
			if (networkType == ConnectivityManager.TYPE_WIFI || networkType == ConnectivityManager.TYPE_MOBILE)
			{
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	public static int getScreenWidth(Activity activity)
	{
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.x;
	}

}
