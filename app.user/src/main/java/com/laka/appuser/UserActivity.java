package com.laka.appuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        onTest();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                break;
        }
    }


    private void onTest() {
        String str1 = "123456789";
        StringBuilder str2 = new StringBuilder(str1);
        str2.append("123456");

    }
}
