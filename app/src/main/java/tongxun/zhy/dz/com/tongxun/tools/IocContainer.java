package tongxun.zhy.dz.com.tongxun.tools;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;



import java.util.HashMap;
import java.util.Map;

/***
 * 实例容器
 * 
 */
@SuppressWarnings("rawtypes")
public class IocContainer {

	private static IocContainer iocContainer;
	// 托管的实例
	Map<String, Instance> instanceByName;
	// 托管的实例
	Map<Class, Instance> instanceByClazz;

	Application application;

	public IocContainer() {
		if (iocContainer == null) {
			iocContainer = this;
		}
		this.instanceByClazz = new HashMap<Class, Instance>();
		this.instanceByName = new HashMap<String, Instance>();
	}

	public static IocContainer getIocContainer() {
		if (iocContainer == null) {
			iocContainer = new IocContainer();
		}
		return iocContainer;
	}

	/**
	 * 
	 * @param application
	 */
	public void initApplication(Application application) {
		this.application = application;
	}

	@SuppressWarnings("unchecked")
	public <T extends Application> T getApplication() {
		return (T) application;
	}

	/**
	 * 通过 别名获取
	 * 
	 * @param name
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) instanceByName.get(name).get(application.getApplicationContext());
	}

	public Context getApplicationContext() {
		return application.getApplicationContext();
	}

	/**
	 * 通过接口获取
	 * 
	 * @param clazz
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
		T t = getSysService(clazz);
		if (t != null)
			return t;
		Instance instance = instanceByClazz.get(clazz);
		if (instance != null) {
			return (T) instance.get(getApplicationContext());
			// 接口过滤
		} else if (!clazz.isInterface()) {
			bind(clazz).to(clazz).scope(Instance.InstanceScope.SCOPE_SINGLETON);
			Instance newins = instanceByClazz.get(clazz);
			if (newins != null) {
				return (T) newins.get(getApplicationContext());
			}
		}
		return null;
	}

	/**
	 * 获取一个 tag相同的对象 如果不存在就创建,存在就获取
	 * 
	 * @param clazz
	 * @param tag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, String tag) {
		Instance instance = instanceByClazz.get(clazz);
		if (instance != null) {
			return (T) instance.get(getApplicationContext(), tag);
		} else {
			bind(clazz).to(clazz).scope(Instance.InstanceScope.SCOPE_SINGLETON);
			instance = instanceByClazz.get(clazz);
			return (T) instance.get(getApplicationContext(), tag);
		}
	}

	/**
	 * 对象配置
	 * 
	 * @param clazz
	 * @return
	 */
	public Instance bind(Class clazz) {
		Instance instance = new Instance(clazz);
		instance.setAsAlians(new Instance.AsAlians() {
			public void as(Instance ins, String name, Class toClazz) {
				if (name != null) {
					if (instanceByName.containsKey(name)) {
						instanceByName.remove(name);
					}
					instanceByName.put(name, ins);
				}
				if (toClazz != null) {
					if (instanceByClazz.containsKey(toClazz)) {
						instanceByClazz.remove(toClazz);
					}
					instanceByClazz.put(toClazz, ins);
				}
			}
		});
		return instance;
	}

	public Instance getInstance(Class clazz) {
		return instanceByClazz.get(clazz);
	}

	@SuppressLint("ServiceCast")
	@SuppressWarnings("unchecked")
	public <T> T getSysService(Class<T> clazz) {
		T t = null;
		if (clazz == NotificationManager.class) {
			t = (T) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		} else if (clazz == ActivityManager.class) {
			t = (T) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		} else if (clazz == PackageManager.class) {
			t = (T) getApplicationContext().getPackageManager();
		} else if (clazz == AssetManager.class) {
			t = (T) getApplicationContext().getAssets();
		}
		return t;

	}

}
