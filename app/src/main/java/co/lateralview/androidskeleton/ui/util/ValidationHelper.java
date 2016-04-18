package co.lateralview.androidskeleton.ui.util;

import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Pattern;

public class ValidationHelper
{
	public static final String TAG = ValidationHelper.class.getSimpleName();

	public static final String REQUIRED_MSG = "This field is required.";
	public static final String VALID_EMAIL_MSG = "Enter an email.";
	public static final String MIN_LENGTH_MSG = "Invalid length.";

	public static boolean hasText(EditText editText, boolean showError)
	{
		return hasText(editText, REQUIRED_MSG, showError);
	}

	public static boolean hasText(EditText editText)
	{
		return hasText(editText, REQUIRED_MSG, true);
	}

	public static boolean hasText(EditText editText, String errorMessage, boolean showError)
	{
		String text = editText.getText().toString().trim();

		cleanError(editText);

		if (text.length() == 0)
		{
			if (showError)
			{
				setError(editText, errorMessage);
			}

			return false;
		}

		return true;
	}


	public static boolean isEmail(EditText editText, boolean showError)
	{
		return isEmail(editText, VALID_EMAIL_MSG, showError);
	}

	public static boolean isEmail(EditText editText)
	{
		return isEmail(editText, VALID_EMAIL_MSG, true);
	}

	public static boolean isEmail(EditText editText, String errorMessage, boolean showError)
	{
		String text = editText.getText().toString().trim();

		cleanError(editText);

		Pattern pattern = Patterns.EMAIL_ADDRESS;

		if (!pattern.matcher(text).matches())
		{
			if (showError)
			{
				setError(editText, errorMessage);
			}

			return false;
		}
		return true;
	}

	public static boolean isValid(EditText editText, boolean customValidationResult, String errorMessage)
	{
		if (!customValidationResult && errorMessage != null)
		{
			setError(editText, errorMessage);
		}

		return customValidationResult;
	}

	public static boolean isMinLengthValid(EditText editText, int min, boolean showError)
	{
		return isMinLengthValid(editText, min, MIN_LENGTH_MSG, showError);
	}

	public static boolean isMinLengthValid(EditText editText, int min)
	{
		return isMinLengthValid(editText, min, MIN_LENGTH_MSG, true);
	}

	public static boolean isMinLengthValid(EditText editText, int min, String errorMessage, boolean showError)
	{
		String text = editText.getText().toString().trim();

		cleanError(editText);

		if (text.length() < min)
		{
			if (showError)
			{
				setError(editText, errorMessage);
			}

			return false;
		}
		return true;
	}

	public static void cleanError(EditText editText)
	{
		try
		{
			((TextInputLayout) editText.getParent()).setErrorEnabled(false);
		} catch (ClassCastException e)
		{
			editText.setError(null);
		}
	}

	public static void setError(EditText editText, String error)
	{
		try
		{
			((TextInputLayout) editText.getParent()).setError(error);
		} catch (ClassCastException e)
		{
			editText.setError(error);
		}
	}
}
