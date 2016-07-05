package com.raymond.retrofittest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.raymond.retrofittest.MyApplication;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.TokenKeeper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.login)
    public void fakeLogin(){
        TokenKeeper tk = new TokenKeeper(MyApplication.getAppContext());
        tk.createUserLoginSession("01", "some", "phone", "peter", 0,0);
        tk.getUser();
        Intent intent = new Intent(LoginActivity.this, MyNewDaysActivity.class);
        startActivity(intent);
        finish();
    }
}
