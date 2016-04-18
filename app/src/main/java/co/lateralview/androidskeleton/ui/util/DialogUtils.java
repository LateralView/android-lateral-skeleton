package co.lateralview.androidskeleton.ui.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogUtils
{

	public static void showSimpleDialog(final Activity activity, String title, String message, String positiveButtonText, final boolean terminate)
	{
		if (activity.hasWindowFocus())
		{
			new AlertDialog.Builder(activity)
					.setTitle(title)
					.setMessage(message)
					.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.dismiss();
							if (terminate)
							{
								activity.finish();
							}
						}
					})
					.show();
		}
	}

}
