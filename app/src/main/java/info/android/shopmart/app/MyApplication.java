package info.android.shopmart.app;

import android.app.Application;

import info.android.shopmart.BuildConfig;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

import static info.android.shopmart.app.Constants.DB_NAME;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        plantTimber();
        initRealm();
    }

    private void plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration defaultRealmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build();
        Realm.setDefaultConfiguration(defaultRealmConfiguration);
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
