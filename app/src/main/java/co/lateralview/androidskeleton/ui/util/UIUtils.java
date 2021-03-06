package co.lateralview.androidskeleton.ui.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

public class UIUtils
{
	public static void setGroupViewTreeEnable(ViewGroup view, boolean enable)
	{
		view.setEnabled(enable);
		for (int i = 0; i < view.getChildCount(); i++)
		{
			View child = view.getChildAt(i);
			child.setEnabled(enable);
			if (child instanceof ViewGroup && ((ViewGroup) child).getChildCount() > 0)
			{
				setGroupViewTreeEnable((ViewGroup) child, enable);
			} else
			{
				child.setEnabled(enable);
			}
		}
	}

	public static void setGroupViewTreeActived(ViewGroup view, boolean actived)
	{
		view.setActivated(actived);
		for (int i = 0; i < view.getChildCount(); i++)
		{
			View child = view.getChildAt(i);
			child.setActivated(actived);
			if (child instanceof ViewGroup && ((ViewGroup) child).getChildCount() > 0)
			{
				setGroupViewTreeActived((ViewGroup) child, actived);
			} else
			{
				child.setActivated(actived);
			}
		}
	}

	public static void setGroupViewTreeSelected(ViewGroup view, boolean selected)
	{
		view.setSelected(selected);
		for (int i = 0; i < view.getChildCount(); i++)
		{
			View child = view.getChildAt(i);
			child.setSelected(selected);
			if (child instanceof ViewGroup && ((ViewGroup) child).getChildCount() > 0)
			{
				setGroupViewTreeSelected((ViewGroup) child, selected);
			} else
			{
				child.setSelected(selected);
			}
		}
	}

	public static void setGroupViewTreeChecked(ViewGroup view, boolean checked)
	{
		for (int i = 0; i < view.getChildCount(); i++)
		{
			View child = view.getChildAt(i);
			child.setSelected(checked);
			if (child instanceof ViewGroup && ((ViewGroup) child).getChildCount() > 0)
			{
				setGroupViewTreeChecked((ViewGroup) child, checked);
			} else
			{
				if (child instanceof CheckBox)
				{
					((CheckBox) child).setChecked(checked);
				}
			}
		}
	}


	public static Drawable getDrawable(Context context, int id)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			return context.getResources().getDrawable(id, context.getTheme());
		} else
		{
			return context.getResources().getDrawable(id);
		}
	}

	public static int convertDipToPixels(Context context, int dip)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
	}

	public static void setBackground(View view, Context context, int id)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			view.setBackground(ContextCompat.getDrawable(context, id));
		} else
		{
			view.setBackgroundDrawable(ContextCompat.getDrawable(context, id));
		}
	}

	public static void setGradientBackground(View view, String color1, String color2)
	{
		GradientDrawable gd = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT,
				new int[]{Color.parseColor(color1), Color.parseColor(color2)});

		gd.setCornerRadius(0f);

		gd.setAlpha(204);

		view.setBackground(gd);
	}

	public static void setGradientBackground(View view, Integer color1, Integer color2)
	{
		GradientDrawable gd = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT,
				new int[]{color1, color2});

		gd.setCornerRadius(0f);

		gd.setAlpha(204);

		view.setBackground(gd);
	}

	public static int getColorWithAlpha(int color, float ratio)
	{
		int newColor = 0;
		int alpha = Math.round(Color.alpha(color) * ratio);
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);
		newColor = Color.argb(alpha, r, g, b);
		return newColor;
	}

	public static SpannableString setTextWithMultipleStyles(Context context, int[] styles, String[] texts)
	{
		if (styles == null || texts == null || styles.length != texts.length)
		{
			return null;
		}

		String allText = null;

		for (String text : texts)
		{
			allText = allText == null ? text : allText + " " + text;
		}

		SpannableString spannableString = new SpannableString(allText);

		int startIndex = 0;

		for (int i = 0; i < texts.length; i++)
		{
			spannableString.setSpan(new TextAppearanceSpan(context, styles[i]), startIndex, startIndex + texts[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			startIndex = startIndex + texts[i].length() + 1; // +1 for the with space
		}

		return spannableString;
	}

	public static int getColorFromRes(Context context, int resColor)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			return context.getColor(resColor);
		} else
		{
			return context.getResources().getColor(resColor);
		}
	}

	public static Bitmap getBitmapFromURL(String src)
	{
		try
		{
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e)
		{
			// Log exception
			return null;
		}
	}

	public static void setSpinnerHeight(Spinner spinner, int height)
	{
		try
		{
			Field popup = Spinner.class.getDeclaredField("mPopup");
			popup.setAccessible(true);

			// Get private mPopup member variable and try cast to ListPopupWindow
			android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

			// Set popupWindow height to 500px
			popupWindow.setHeight(height);
		} catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e)
		{
			// silently fail...
		}
	}

	public static void setNumberPickerDividerColor(NumberPicker picker, int color)
	{
		Field[] pickerFields = NumberPicker.class.getDeclaredFields();
		for (Field pf : pickerFields)
		{
			if (pf.getName().equals("mSelectionDivider"))
			{
				pf.setAccessible(true);
				try
				{
					ColorDrawable colorDrawable = new ColorDrawable(color);
					pf.set(picker, colorDrawable);
				} catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				} catch (Resources.NotFoundException e)
				{
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
