package co.lateralview.androidskeleton.infrastructure.manager;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ShareCompat;
import android.util.Base64;
import android.util.Log;

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

	public void shareData(Activity activity, String text, Bitmap image)
	{
		Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
				.setType("image/jpeg")
				.setText(text)
				.setStream(Uri.parse(saveToInternalSorage(image)))
				.getIntent();

		activity.startActivity(shareIntent);
	}

	public String saveToInternalSorage(Bitmap bitmapImage)
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

		ContextWrapper cw = new ContextWrapper(mContext);

		// path to /data/data/yourapp/app_data/imageDir
		//File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

		// Create imageDir
		// File myPath = new File(directory, "temporaryFile.jpg");
		File myPath = new File(Environment.getExternalStorageDirectory() + File.separator + "temporaryFile.jpg");

		try
		{
			myPath.createNewFile();
			FileOutputStream fo = new FileOutputStream(myPath);
			fo.write(bytes.toByteArray());
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return myPath.toURI().toString();
	}
}
