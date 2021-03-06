package ru.alexbykov.permissionssample;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ru.alexbykov.nopermission.PermissionHelper;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;
    private PermissionHelper permissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        setupPermissionHelper();
        setupUX();
    }


    private void setupUX() {
        findViewById(R.id.btnAskPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askLocationPermission();
            }
        });
    }

    private void askLocationPermission() {
        permissionHelper.check(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_COARSE_LOCATION)
                .onSuccess(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) findViewById(R.id.tvResult)).setText("Location success");
                    }
                })
                .onFailure(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) findViewById(R.id.tvResult)).setText("Location failure");
                    }
                })
                .onNeverAskAgain(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) findViewById(R.id.tvResult)).setText("Location newer ask again");
                    }
                })
                .run();
    }

    private void setupPermissionHelper() {
        permissionHelper = new PermissionHelper(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        permissionHelper.unsubscribe();
        super.onDestroy();
    }
}
