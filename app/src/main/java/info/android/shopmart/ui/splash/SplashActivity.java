package info.android.shopmart.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import java.util.List;

import info.android.shopmart.R;
import info.android.shopmart.db.AppDatabase;
import info.android.shopmart.networking.model.AppConfig;
import info.android.shopmart.networking.model.Product;
import info.android.shopmart.ui.base.BaseActivity;
import info.android.shopmart.ui.main.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.activity_splash);
        changeStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        hideToolbar();

        // checking for auth token in prefs
   //     if (TextUtils.isEmpty(AppPref.getInstance().getAuthToken())) {
            // user token is not present, take him to login screen
         //   Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
         //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         //   startActivity(intent);
     //       finish();
     //       return;
  //      }
       // fetchAppConfig();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    /**
     * Fetching app configuration from server
     * This will get PayTM configuration required for payment related operations
     */
    private void fetchAppConfig() {
        Call<AppConfig> call = getApi().getAppConfig();
        call.enqueue(new Callback<AppConfig>() {
            @Override
            public void onResponse(Call<AppConfig> call, Response<AppConfig> response) {
                if (!response.isSuccessful()) {
                    handleError(response.errorBody());
                    return;
                }

                // save app config to db
                AppDatabase.saveAppConfig(response.body());

                // fetch products
                fetchProducts();
            }

            @Override
            public void onFailure(Call<AppConfig> call, Throwable t) {
                handleError(t);
            }
        });
    }

    /**
     * Fetching products on splash screen before loading home screen
     */
    private void fetchProducts() {
        Call<List<Product>> call = getApi().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    handleError(response.errorBody());
                    return;
                }

                // store products in db
                AppDatabase.saveProducts(response.body());

                // start home screen
                launchHomeScreen();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                handleError(t);
            }
        });
    }

    private void launchHomeScreen() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
