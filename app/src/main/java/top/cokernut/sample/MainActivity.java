package top.cokernut.sample;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mInstallTV;
    private TextView mFinishTV;
    private TextView mStartTV;
    private TextView mStopTV;

    private Button mInstallBT;
    private Button mNextBT;

    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInstallTV      = (TextView) findViewById(R.id.tv_install);
        mStartTV        = (TextView) findViewById(R.id.tv_start);
        mStopTV         = (TextView) findViewById(R.id.tv_stop);
        mFinishTV       = (TextView) findViewById(R.id.tv_finish);
        mInstallBT      = (Button) findViewById(R.id.bt_install);
        mNextBT         = (Button) findViewById(R.id.bt_next);
        mInstallTV      .setOnClickListener(this);
        mFinishTV       .setOnClickListener(this);
        mInstallBT      .setOnClickListener(this);
        mNextBT         .setOnClickListener(this);
        mStartTV        .setOnClickListener(this);
        mStopTV         .setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        num = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                if (serviceIsRunning()) {
                    showToast("服务已经在运行！");
                } else {
                    startAccessibilityService();
                }
                break;
            case R.id.tv_stop:
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                break;
            case R.id.tv_install:
                showToast(((TextView)v).getText().toString());
                break;
            case R.id.tv_finish:
                showToast(((TextView)v).getText().toString());
                num++;
                if (num > 5) {
                    startActivity(new Intent("top.cokernut.sample.SecondActivity"));
                }
                break;
            case R.id.bt_install:
                showToast(((Button)v).getText().toString());
                break;
            case R.id.bt_next:
                showToast(((Button)v).getText().toString());
                break;
            default:
                showToast("未知按钮");
                break;
        }
    }

    private void showToast(final String text) {
        //AccessibilityService触发事件是异步的，要回到UI线程改变UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "点击了" + text, Toast.LENGTH_LONG).show();
                Log.d("text=====", num + "=====" + text);
            }
        });
    }

    /**
     * 判断自己的应用的AccessibilityService是否在运行
     *
     * @return
     */
    private boolean serviceIsRunning() {
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Short.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo info : services) {
            if (info.service.getClassName().equals(getPackageName() + ".MyService")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 前往设置界面开启服务
     */
    private void startAccessibilityService() {
        new AlertDialog.Builder(this)
                .setTitle("开启辅助功能")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("使用此项功能需要您开启辅助功能")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 隐式调用系统设置界面
                                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                startActivity(intent);
                            }
                        }).create().show();
    }
}
