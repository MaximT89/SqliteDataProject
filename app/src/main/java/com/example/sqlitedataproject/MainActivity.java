package com.example.sqlitedataproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEditName, mEditAge, mEditCity;
    private Button mBtnSaveUser;
    private DataHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mBtnSaveUser.setOnClickListener(v -> {

//            DataHelper.getInstance(this).dropTable();

            if (!TextUtils.isEmpty(mEditName.getText()) && !TextUtils.isEmpty(mEditAge.getText()) && !TextUtils.isEmpty(mEditCity.getText())) {

                String name = mEditName.getText().toString();
                int age = Integer.parseInt(mEditAge.getText().toString());
                String city = mEditCity.getText().toString();

                User user = new User(name, age, city);

                if (db.insertUser(user)) {
                    Toast.makeText(this, "Пользователь успешно добавлен в базу данных", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Ошибка добавления пользователя", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void initView() {
        mEditName = findViewById(R.id.edit_name);
        mEditAge = findViewById(R.id.edit_age);
        mEditCity = findViewById(R.id.edit_city);

        mBtnSaveUser = findViewById(R.id.btn_save_user);

        db = DataHelper.getInstance(this);
    }
}