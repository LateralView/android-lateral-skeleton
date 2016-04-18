package co.lateralview.androidskeleton.ui.util;

import java.text.DecimalFormat;

public class MathUtils
{

	public static String toRoundedString(float number)
	{
		if (number < 1000)
		{
			return "" + number;
		}
		int exp = (int) (Math.log(number) / Math.log(1000));
		return String.format("%.1f %c",
				number / Math.pow(1000, exp),
				"KMGTPE".charAt(exp - 1));
	}

	public static String formatFloatString(String str)
	{
		DecimalFormat formatter = new DecimalFormat("#,###.##");
		return formatter.format(Double.parseDouble(str));
	}

}
