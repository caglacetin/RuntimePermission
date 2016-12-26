package com.example.cagla.runtimepermission;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .replace(R.id.runtime_container, new RuntimePermissionFragment())
                    .commit();
        }
    }
}
