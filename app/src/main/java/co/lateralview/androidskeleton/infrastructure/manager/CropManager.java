package co.lateralview.androidskeleton.infrastructure.manager;

import android.app.Activity;
import android.net.Uri;

import com.android.camera.CropImageIntentBuilder;

public class CropManager
{
	public static final float CROP_Y_RATIO = 0.5f;

	protected Activity mCallerActivity;

	protected int mRequestId;

	public CropManager(Activity activity, int requestId)
	{
		mCallerActivity = activity;

		mRequestId = requestId;
	}

	public boolean requestCrop(Uri imagePath)
	{
		Uri croppedImage = new FileManager().createPhotoUri();

		if (croppedImage != null)
		{
			CropImageIntentBuilder cropImage = new CropImageIntentBuilder(100, (int) (CROP_Y_RATIO * 100), ImageManager.MAX_IMAGE_WIDTH, (int) (ImageManager.MAX_IMAGE_HEIGHT * CROP_Y_RATIO), croppedImage)
					.setOutlineColor(0xFF03A9F4)
					.setSourceImage(imagePath);

			mCallerActivity.startActivityForResult(cropImage.getIntent(mCallerActivity), mRequestId);

			return true;
		}

		return false;
	}
}
