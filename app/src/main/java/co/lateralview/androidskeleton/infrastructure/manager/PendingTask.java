package co.lateralview.androidskeleton.infrastructure.manager;

public class PendingTask
{
	public interface ITasksListener
	{
		void callPendingTask();
	}

	private String mTag;
	private ITasksListener mListener;

	public PendingTask(String mTag, ITasksListener mListener)
	{
		this.mTag = mTag;
		this.mListener = mListener;
	}

	public void callPendingTask()
	{
		mListener.callPendingTask();
	}

	public String getTag()
	{
		return mTag;
	}
}