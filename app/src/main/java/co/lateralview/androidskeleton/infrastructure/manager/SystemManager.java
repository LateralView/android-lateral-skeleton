package co.lateralview.androidskeleton.infrastructure.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.ShareCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SystemManager
{
	private Context mContext;

	public SystemManager(Context context)
	{
		mContext = context;
	}

	/**
	 * Shown in console the KeyHash application.
	 */
	public void showKeyHash()
	{
		try
		{
			PackageInfo info = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(),
					PackageManager.GET_SIGNATURES);

			for (Signature signature : info.signatures)
			{
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (PackageManager.NameNotFoundException e)
		{

		} catch (NoSuchAlgorithmException e)
		{

		}
	}

	public void showDeviceInfo()
	{
		switch (mContext.getResources().getDisplayMetrics().densityDpi)
		{
			case DisplayMetrics.DENSITY_LOW:
				Log.i("DeviceScreenDensity", "ldpi");
				break;
			case DisplayMetrics.DENSITY_MEDIUM:
				Log.i("DeviceScreenDensity", "mdpi");
				break;
			case DisplayMetrics.DENSITY_HIGH:
				Log.i("DeviceScreenDensity", "hdpi");
				break;
			case DisplayMetrics.DENSITY_XHIGH:
				Log.i("DeviceScreenDensity", "xhdpi");
				break;
			case DisplayMetrics.DENSITY_XXHIGH:
				Log.i("DeviceScreenDensity", "xxhdpi");
				break;
			case DisplayMetrics.DENSITY_XXXHIGH:
				Log.i("DeviceScreenDensity", "xxxhdpi");
				break;
		}
	}

	public void shareData(Activity activity, String text)
	{
		Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
				.setType("text/plain")
				.setText(text)
				.getIntent();

		activity.startActivity(shareIntent);
	}

	public void shareData(Activity activity, String text, Bitmap image)
	{
		Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
				.setType("image/jpeg")
				.setText(text)
				.setStream(Uri.parse(saveToInternalStorage(image)))
				.getIntent();

		activity.startActivity(shareIntent);
	}

	public String saveToInternalStorage(Bitmap bitmapImage)
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

		File myPath = new File(Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis() + "tempFile.jpg");

		FileOutputStream fo = null;

		try
		{
			myPath.createNewFile();
			fo = new FileOutputStream(myPath);
			fo.write(bytes.toByteArray());
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (fo != null)
				{
					fo.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return myPath.toURI().toString();
	}

	public void vibrate(int millis)
	{
		((Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(millis);
	}

	public void keepScreenOn()
	{
		((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public void fullScreenMode()
	{
		// Hide both the navigation bar and the status bar.
		// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
		// a general rule, you should design your app to hide the status bar whenever you
		// hide the navigation bar.
		int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

		((Activity) mContext).getWindow().getDecorView().setSystemUiVisibility(uiOptions);
	}

	public void hideKeyboard()
	{
		if (isKeyboardVisible())
		{
			View view = ((Activity) mContext).getCurrentFocus();
			if (view != null)
			{
				InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}
		}
	}

	public void showKeyboard()
	{
		if (!isKeyboardVisible())
		{
			InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			((Activity) mContext).getWindow().getDecorView().requestFocus();
			inputMethodManager.showSoftInput(((Activity) mContext).getWindow().getDecorView(), 0);
		}
	}

	public boolean isKeyboardVisible()
	{
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		return imm.isAcceptingText();
	}
}
