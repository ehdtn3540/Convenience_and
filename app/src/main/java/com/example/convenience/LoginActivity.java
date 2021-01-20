package com.example.convenience;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv;
    EditText etUser;
    EditText etPw;
    Button btJoin;
    Button btLogin;

    String loginId, loginPw; //자동로그인, 로그인유지
    private Context mContext;

    String ip = "121.165.116.253";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv = findViewById(R.id.tv_user);
        etUser = findViewById(R.id.et_user);
        etPw = findViewById(R.id.et_pw);
        btJoin = findViewById(R.id.bt_join);
        btLogin = findViewById(R.id.bt_login);

        //자동로그인, 로그인유지
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        loginId = auto.getString("inputId",null);
        loginPw = auto.getString("inputPw",null);

        if(loginId !=null && loginPw != null) {
//            if(loginId.equals("admin") && loginPw.equals("1")) {
            Toast.makeText(LoginActivity.this, loginId +"님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, com.example.convenience.MainFragment.class);
            startActivity(intent);
            finish();
//            }
        }

        btLogin.setOnClickListener(this);
        btJoin.setOnClickListener(this);
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("kkk", response);
            if(response.equals("로그인실패")){
                Toast.makeText(getApplicationContext(), "로그인 실패했습니다 이자식아!", Toast.LENGTH_SHORT).show();
            }else{
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autoLogin = auto.edit();
                autoLogin.putString("inputId", etUser.getText().toString());
                autoLogin.putString("inputPw", etPw.getText().toString());
                autoLogin.commit();
                Toast.makeText(LoginActivity.this, etUser.getText().toString()+"님 환영합니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, com.example.convenience.MainFragment.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bt_join){
            //회원가입으로 이동(가려진상태)
//            Intent intent = new Intent(this, com.example.convenience.JoinActivity.class);
//            startActivity(intent);
        }else if(view.getId() == R.id.bt_login){
            final String id = etUser.getText().toString().trim();
            final String pass = etPw.getText().toString().trim();

            /** post **/
            RequestQueue stringRequest = Volley.newRequestQueue(this);
//            String url = "http://119.194.157.61:8080/oop/login.do";
            String url = "http://" + ip + ":8080/Convenience/Login?";
            StringRequest myReq = new StringRequest(Request.Method.POST, url,
                    successListener, errorListener) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", id);
                    params.put("pass", pass);
                    return params;
                }
            };
            myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
            stringRequest.add(myReq);
        }
    }
}