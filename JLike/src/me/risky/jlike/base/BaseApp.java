package me.risky.jlike.base;

import me.risky.jlike.R;
import me.risky.jlike.db.DaoMaster;
import me.risky.jlike.db.DaoSession;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 14-3-12.
 */
public class BaseApp extends Application{

	private final static String TAG = "BaseApp";
	
    private static BaseApp baseApp;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        if(baseApp == null)
            baseApp = this;
    }
    
    public static DaoMaster getDaoMaster(Context context){
        if(daoMaster == null){
        	String dbName = context.getResources().getString(R.string.db_name);
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
