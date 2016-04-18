package co.lateralview.androidskeleton.infrastructure.manager;

import java.util.HashMap;
import java.util.Map;

public class TaskManager
{
	private HashMap<String, PendingTask> mPendingTasksQueue = new HashMap<>();

	public void callPendingTasks()
	{
		if (!mPendingTasksQueue.isEmpty())
		{
			for (Map.Entry<String, PendingTask> entry : mPendingTasksQueue.entrySet())
			{
				PendingTask pendingTask = entry.getValue();

				pendingTask.callPendingTask();
			}

			clearQueue();
		}
	}

	public void addTask(PendingTask task)
	{
		if (mPendingTasksQueue != null)
		{
			if (!mPendingTasksQueue.containsKey(task.getTag()))
			{
				mPendingTasksQueue.put(task.getTag(), task);
			}
		}
	}

	public void removeTask(String tag)
	{
		if (mPendingTasksQueue != null)
		{
			mPendingTasksQueue.remove(tag);
		}
	}

	public void clearQueue()
	{
		mPendingTasksQueue.clear();
	}

}
