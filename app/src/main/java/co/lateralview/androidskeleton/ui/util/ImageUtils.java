package co.lateralview.androidskeleton.ui.util;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ImageUtils
{

	public static int getMainColor(Bitmap bitmap)
	{
		long redBucket = 0;
		long greenBucket = 0;
		long blueBucket = 0;
		long pixelCount = 0;

		for (int y = 0; y < bitmap.getHeight(); y++)
		{
			for (int x = 0; x < bitmap.getWidth(); x++)
			{
				int c = bitmap.getPixel(x, y);

				pixelCount++;
				redBucket += Color.red(c);
				greenBucket += Color.green(c);
				blueBucket += Color.blue(c);
			}
		}

		return Color.rgb((int) (redBucket / pixelCount),
				(int) (greenBucket / pixelCount),
				(int) (blueBucket / pixelCount));
	}

}
