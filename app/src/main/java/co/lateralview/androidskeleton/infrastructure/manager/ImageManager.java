package co.lateralview.androidskeleton.infrastructure.manager;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageManager
{
	public static final int MAX_IMAGE_WIDTH = 1024;
	public static final int MAX_IMAGE_HEIGHT = 1024;

	public static Bitmap compressImage(Bitmap bitmap, File file)
	{
		OutputStream outputStream = null;

		try
		{
			outputStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (outputStream != null)
				{
					outputStream.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return bitmap;
	}
}
