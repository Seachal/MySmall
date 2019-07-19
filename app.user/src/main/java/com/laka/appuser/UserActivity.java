package com.laka.appuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                break;
        }
    }
}
