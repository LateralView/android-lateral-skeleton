package co.lateralview.androidskeleton.infrastructure.manager;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager
{
	private static final String FILE_TEMP_PREFIX = "SKELETON";

	public static File createPhotoFile()
	{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = FILE_TEMP_PREFIX + "_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		storageDir.mkdirs();

		try
		{
			return File.createTempFile(imageFileName, ".jpg", storageDir);
		} catch (IOException e)
		{
			return null;
		}
	}

	public Uri createPhotoUri()
	{
		File file = createPhotoFile();

		return null != file ? Uri.fromFile(file) : null;
	}

	public boolean writeFile(File f, byte[] bytes)
	{
		return writeFile(f.getAbsolutePath(), bytes);
	}

	public boolean writeFile(String path, byte[] bytes)
	{
		try
		{
			FileOutputStream stream = new FileOutputStream(path);

			try
			{
				stream.write(bytes);
			} finally
			{
				stream.close();
			}

			return true;
		} catch (Exception e)
		{
			return false;
		}
	}
}
