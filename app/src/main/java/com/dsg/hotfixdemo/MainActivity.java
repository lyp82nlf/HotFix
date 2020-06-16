package com.dsg.hotfixdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int a = 1;
        a++;
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "错误", Toast.LENGTH_LONG).show();
                fixCode();
            }
        });
    }

    private void fixCode() {
        Toast.makeText(MainActivity.this, "修复完成,正确", Toast.LENGTH_LONG).show();
        Log.d("main1", "aaa");
        int a = 1;
        a++;
        System.out.println(a);
    }
}
