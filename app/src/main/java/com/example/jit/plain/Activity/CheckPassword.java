package com.example.jit.plain.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jit.api.HttpEngine;
import com.example.jit.plain.R;
import com.example.jit.plain.Utils.StreamUtils;
import com.example.jit.plain.Utils.UiUtils;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckPassword extends AppCompatActivity {

    private ImageView img;
    private TextView et_password;
    private String MD5Password;
    private String mURL;
    private boolean isCorrect = false;

    protected static final int IS_CORRECT = 0;
    protected static final int CODE_NET_ERROR = 1;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IS_CORRECT:
                    if (isCorrect){
                        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                        enterHome();
                    }else {
                        Toast.makeText(CheckPassword.this,"密码错误,请重新输入",Toast.LENGTH_SHORT).show();
                        et_password.setText("");
                    }
                    break;
                case CODE_NET_ERROR:
                    Toast.makeText(CheckPassword.this, "网络错误", Toast.LENGTH_SHORT)
                            .show();
                    break;
                default:
                    break;
            }
        };
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);
        img = (ImageView)findViewById(R.id.login_img);
        et_password = (TextView)findViewById(R.id.et_password);
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),25);
            }
        };
        img.setOutlineProvider(viewOutlineProvider);
        img.setClipToOutline(true);
    }

    public void login(View v){
        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(password.trim())){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            MD5Password = UiUtils.md5(password);
            mURL = HttpEngine.SERVER_URL + "/admin_version/accessLogin?password="+MD5Password;
        }
        checkIsCorrect();
    }
    protected void checkIsCorrect() {
        new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                HttpURLConnection conn = null;
                try {

                    // 本机地址用localhost, 但是如果用模拟器加载本机的地址时,可以用ip(10.0.2.2)来替换
                    URL url = new URL(mURL);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");// 设置请求方法
                    conn.setConnectTimeout(5000);// 设置连接超时
                    conn.setReadTimeout(5000);// 设置响应超时, 连接上了,但服务器迟迟不给响应
                    conn.connect();// 连接服务器

                    int responseCode = conn.getResponseCode();// 获取响应码
                    if (responseCode == 200) {
                        InputStream inputStream = conn.getInputStream();
                        String result = StreamUtils.readFromStream(inputStream);
                        // System.out.println("网络返回:" + result);

                        // 解析json
                        JSONObject jo = new JSONObject(result);
                        //mVersionName = jo.getString("versionName");
                        isCorrect = jo.getBoolean("canAccess");

                        msg.what = IS_CORRECT;
                    }
                } catch (Exception e) {
                    // url错误的异常
                    msg.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } finally {
                    mHandler.sendMessage(msg);
                    if (conn != null) {
                        conn.disconnect();// 关闭网络连接
                    }
                }
            }
        }.start();
    }
    private void enterHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
