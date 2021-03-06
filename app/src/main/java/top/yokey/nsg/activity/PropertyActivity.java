package top.yokey.nsg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import top.yokey.nsg.R;

/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：我的财产
*
*/

public class PropertyActivity extends AppCompatActivity {

    private Activity mActivity;
    private NcApplication mApplication;

    private ImageView backImageView;
    private TextView titleTextView;

    private LinearLayout preDepositLinearLayout;
    private LinearLayout rechargeCardLinearLayout;
    private LinearLayout vouchersLinearLayout;
    private LinearLayout redPacketsLinearLayout;
    private LinearLayout pointsLinearLayout;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            returnActivity();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_property);
        initView();
        initData();
        initEven();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //初始化控件
    private void initView() {

        backImageView = (ImageView) findViewById(R.id.backImageView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);

        preDepositLinearLayout = (LinearLayout) findViewById(R.id.preDepositLinearLayout);
        rechargeCardLinearLayout = (LinearLayout) findViewById(R.id.rechargeCardLinearLayout);
        vouchersLinearLayout = (LinearLayout) findViewById(R.id.vouchersLinearLayout);
        redPacketsLinearLayout = (LinearLayout) findViewById(R.id.redPacketsLinearLayout);
        pointsLinearLayout = (LinearLayout) findViewById(R.id.pointsLinearLayout);

    }

    //初始化数据
    private void initData() {

        mActivity = this;
        mApplication = (NcApplication) getApplication();

        titleTextView.setText("我的财产");

    }

    //初始化事件
    private void initEven() {

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnActivity();
            }
        });

        preDepositLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApplication.startActivityLoginSuccess(mActivity, new Intent(mActivity, PreDepositActivity.class));
            }
        });

        rechargeCardLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApplication.startActivityLoginSuccess(mActivity, new Intent(mActivity, RechargeCardActivity.class));
            }
        });

        vouchersLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApplication.startActivityLoginSuccess(mActivity, new Intent(mActivity, VouchersActivity.class));
            }
        });

        redPacketsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApplication.startActivityLoginSuccess(mActivity, new Intent(mActivity, RedPacketActivity.class));
            }
        });

        pointsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApplication.startActivityLoginSuccess(mActivity, new Intent(mActivity, PointsActivity.class));
            }
        });

    }

    //返回&销毁Activity
    private void returnActivity() {

        mApplication.finishActivity(mActivity);

    }

}
