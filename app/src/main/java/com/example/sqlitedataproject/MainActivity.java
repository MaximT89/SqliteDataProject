package com.example.sqlitedataproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtnAddUser;
    private Button mBtnGoSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        
        DataHelper db = new DataHelper(this);
        Log.d("TAG", "db: " + db.hashCode());

        mBtnAddUser.setOnClickListener(v -> {
            db.insertUser(new User("Tom", 25, "Tokio"));
        });

        mBtnGoSecond.setOnClickListener(v ->
                startActivity(new Intent(this, SecondActivity.class))
        );
    }

    private void initView() {
        mBtnAddUser = findViewById(R.id.btn_add_user);
        mBtnGoSecond = findViewById(R.id.btn_go_second);
    }
}