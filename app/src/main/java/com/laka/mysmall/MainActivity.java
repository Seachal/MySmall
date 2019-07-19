package com.laka.mysmall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
/**
 * @Author:summer
 * @Date:2019/7/11
 * @Description:宿主app入口
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:

                break;
            case R.id.btn2:

                break;
            case R.id.btn3:

                break;
        }
    }
}
