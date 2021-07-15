package com.example.sqlitedataproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private EditText mEditName, mEditAge, mEditCity, mEditSearch, mEditUpdateCity, mEditUpdateUser,
            mEditUserDelete;
    private Button mBtnSaveUser, mBtnSearch, mBtnUpdate, mBtnDelete;
    private DataHelper db;
    private TextView mTextSearchResult;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mBtnSaveUser.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(mEditName.getText()) && !TextUtils.isEmpty(mEditAge.getText()) && !TextUtils.isEmpty(mEditCity.getText())) {

                String name = mEditName.getText().toString();
                int age = Integer.parseInt(mEditAge.getText().toString());
                String city = mEditCity.getText().toString();

                User user = new User(name, age, city);

                if (db.insertUser(user)) {
                    Log.d("TAG", "Пользователь успешно добавлен в базу данных");
                } else {
                    Log.d("TAG", "Произошла ошибка при добавлении пользователя в базу данных");
                }

            } else {
                makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }

        });

        mBtnSearch.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(mEditSearch.getText().toString())) {

//                List<User> users = db.getUsers(mEditSearch.getText().toString());
                List<User> users = db.getUsersSecond(mEditSearch.getText().toString());


                StringBuilder result = new StringBuilder();

                for (User user : users) {
                    result.append(user.toString()).append("\n");
                }

                if (users.size() == 0) {
                    mTextSearchResult.setText("Пользователи с таким именем не найдены");
                }

                mTextSearchResult.setText(result);

            } else {
                mTextSearchResult.setText("Введите имя пользователя для поиска");
            }

        });

        mBtnUpdate.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mEditUpdateCity.getText().toString())
                    && !TextUtils.isEmpty(mEditUpdateUser.getText().toString())) {

//                db.updateUser(mEditUpdateUser.getText().toString(),
//                        mEditUpdateCity.getText().toString());

                db.updateUserSecond(mEditUpdateUser.getText().toString(),
                        mEditUpdateCity.getText().toString());

            } else {
                mEditUpdateUser.setHint("Введите имя пользователя");
                mEditUpdateUser.setHintTextColor(Color.RED);

                mEditUpdateCity.setHint("Введите новый город пользователя");
                mEditUpdateCity.setHintTextColor(Color.RED);
            }
        });

        mBtnDelete.setOnClickListener(v -> {

            if(!TextUtils.isEmpty(mEditUserDelete.getText().toString())){

//                db.deleteUser(mEditUserDelete.getText().toString());
                db.deleteUserSecond(mEditUserDelete.getText().toString());

            }else {
                mEditUserDelete.setHint("Введите имя пользователя");
                mEditUserDelete.setHintTextColor(Color.RED);
            }

        });
    }

    private void initView() {
        mEditName = findViewById(R.id.edit_name);
        mEditAge = findViewById(R.id.edit_age);
        mEditCity = findViewById(R.id.edit_city);
        mEditSearch = findViewById(R.id.edit_search);
        mEditUpdateCity = findViewById(R.id.edit_new_city);
        mEditUpdateUser = findViewById(R.id.edit_name_user_update);
        mEditUserDelete = findViewById(R.id.edit_name_delete);

        mBtnSaveUser = findViewById(R.id.btn_save_user);
        mBtnSearch = findViewById(R.id.btn_search);
        mBtnUpdate = findViewById(R.id.btn_update_user);
        mBtnDelete = findViewById(R.id.btn_delete_user);

        mTextSearchResult = findViewById(R.id.txt_users);

        db = DataHelper.getInstance(this);
    }
}