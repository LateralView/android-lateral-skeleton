package co.lateralview.androidskeleton.infrastructure.manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class CameraManager
{
	private static final int REQUEST_TAKE_PHOTO = 2001;
	private static final int REQUEST_TAKE_PHOTO_CROP = 2002;
	private static final int REQUEST_CROP_IMAGE = 2003;

	protected Activity mCallerActivity;
	private ICameraServiceCallback mCameraServiceListener;
	private File mPhotoFile;

	public interface ICameraServiceCallback
	{
		void onPictureTaken(Bitmap picture, File file);
	}

	public CameraManager(Activity callerActivity, ICameraServiceCallback cameraServiceListener)
	{
		mCallerActivity = callerActivity;
		mCameraServiceListener = cameraServiceListener;
	}

	public void startCameraService(boolean cropIt)
	{
		mPhotoFile = dispatchTakePictureIntent(cropIt);
	}

	private File dispatchTakePictureIntent(boolean cropIt)
	{
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File photoFile = null;

		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(mCallerActivity.getPackageManager()) != null)
		{
			// Create the File where the photo should go
			photoFile = FileManager.createPhotoFile();

			// Continue only if the File was successfully created
			if (photoFile != null)
			{
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

				mCallerActivity.startActivityForResult(takePictureIntent, cropIt ? REQUEST_TAKE_PHOTO_CROP : REQUEST_TAKE_PHOTO);
			}
		}

		return photoFile;
	}

	private void onRequestTakePhotoSuccess()
	{
		Bitmap imageBitmap = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());

		if (mCameraServiceListener != null)
		{
			mCameraServiceListener.onPictureTaken(imageBitmap, mPhotoFile);
		}
	}

	private void onRequestTakePhotoCropSuccess(Intent data)
	{
		if (data != null)
		{
			final String path = data.getData() != null ? data.getData().getPath() : data.getAction().replace("file://", "");

			new PhotoDecodeTask(new PhotoDecodeTask.IPhotoDecodeTaskCallback()
			{
				@Override
				public void onPhotoDecodeTaskSuccess(final Bitmap photo)
				{

					mCallerActivity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							mCameraServiceListener.onPictureTaken(photo, new File(path));
						}
					});
				}
			}).execute(path);
		}
	}
}
