package co.lateralview.androidskeleton.infrastructure.manager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import co.lateralview.androidskeleton.R;


public class MailManager
{
	private Activity mActivity;

	public MailManager(Activity activity)
	{
		mActivity = activity;
	}

	public void sendSupportMail()
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append("mailto:")
				.append("help.support@app.com")
				.append("?subject=")
				.append(mActivity.getString(R.string.app_name) + ": needs help.")
				.append("&body=");

		String uriString = buffer.toString().replace(" ", "%20");

		mActivity.startActivity(Intent.createChooser(new Intent(Intent.ACTION_SENDTO, Uri.parse(uriString)), mActivity.getString(R.string.app_name) + " help mail"));
	}
}
