package co.lateralview.androidskeleton.infrastructure.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import co.lateralview.androidskeleton.application.AndroidSkeletonApplication;

public class SharedPreferencesManager
{
	public static final String DEFAULT_FILE_NAME = AndroidSkeletonApplication.getInstance().getPackageName() + ".sharedPreferences";
	private final SharedPreferences mSharedPreferences;

	public SharedPreferencesManager(Context context, String fileName)
	{
		mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
	}

	public SharedPreferencesManager(Context context)
	{
		this(context, DEFAULT_FILE_NAME);
	}

	public void save(String key, boolean value)
	{
		mSharedPreferences.edit().putBoolean(key, value).apply();
	}

	public void save(String key, String value)
	{
		mSharedPreferences.edit().putString(key, value).apply();
	}

	public void save(String key, int value)
	{
		mSharedPreferences.edit().putInt(key, value).apply();
	}

	public boolean getBoolean(String key)
	{
		return mSharedPreferences.getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean defaultValue)
	{
		return mSharedPreferences.getBoolean(key, defaultValue);
	}

	public String getString(String key)
	{
		return getString(key, "");
	}

	public String getString(String key, String defaultValue)
	{
		return mSharedPreferences.getString(key, defaultValue);
	}

	public int getInt(String key)
	{
		return getInt(key, -1);
	}

	public int getInt(String key, int defaultValue)
	{
		return mSharedPreferences.getInt(key, defaultValue);
	}

	public <T> void save(String key, T model)
	{
		String json = new Gson().toJson(model);
		mSharedPreferences.edit().putString(key, json).apply();
	}

	public <T> T get(String key, Class<T> type)
	{
		String json = getString(key);
		return json != "" ? new Gson().fromJson(getString(key), type) : null;
	}

	public void clear()
	{
		mSharedPreferences.edit().clear().apply();
	}

	public void remove(String key)
	{
		mSharedPreferences.edit().remove(key).apply();
	}
}
