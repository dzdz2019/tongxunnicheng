package tongxun.zhy.dz.com.tongxun.thread;

import android.app.Dialog;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tongxun.zhy.dz.com.tongxun.tools.Consts;
import tongxun.zhy.dz.com.tongxun.tools.IDialog;
import tongxun.zhy.dz.com.tongxun.tools.IocContainer;

public class ThreadWorker {
	static ExecutorService executorService;

	/**
	 * 
	 * @param runnable
	 * @return
	 */
	public static Future<?> executeRunalle(Runnable runnable) {
		if (executorService == null) {
			executorService = Executors.newFixedThreadPool(Consts.net_pool_size);
		}
		return executorService.submit(runnable);
	}

	/**
	 * 
	 *            -display or hide the progress diaglog when task is busy.
	 * @return Future<?>
	 */
	public static Future<?> execuse(boolean dialog, final Task task) {
		if (dialog) {
			IDialog diagloer = IocContainer.getIocContainer().get(IDialog.class);
			Dialog pd = diagloer.showProgressDialog(task.mContext);
			pd.setCancelable(false);
			task.dialog = pd;
		}
		Future<?> future = executeRunalle(new Runnable() {
			@Override
			public void run() {
				try {
					task.doInBackground();
				} catch (Exception e) {
					task.transfer(null, Task.TRANSFER_DOERROR);
					return;
				}
				task.transfer(null, Task.TRANSFER_DOUI);
			}
		});
		return future;
	}

}
