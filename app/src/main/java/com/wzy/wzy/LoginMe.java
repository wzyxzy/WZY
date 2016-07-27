package com.wzy.wzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginMe extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView username;
    private EditText password;
    private Button signin;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_me);
        initView();
    }

    private void initView() {
        username = (AutoCompleteTextView) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        register = (Button) findViewById(R.id.register);

        signin.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:

                if ((username.getText().toString().equals("wzy")) && (password.getText().toString().equals("123"))) {
                    Intent intent = new Intent();
                    intent.setClass(this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"请检查用户名或者密码是否正确",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register:

                break;
        }
    }


}
