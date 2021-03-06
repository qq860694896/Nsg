package top.yokey.nsg.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import net.tsz.afinal.http.AjaxCallBack;

import top.yokey.nsg.R;
import top.yokey.nsg.utility.ControlUtil;
import top.yokey.nsg.utility.DialogUtil;
import top.yokey.nsg.utility.TextUtil;

/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：网页浏览器 用做登录
*
*/

public class LoginWebActivity extends Activity {

    private Activity mActivity;
    private NcApplication mApplication;

    private String linkString;
    private String modelString;

    private ImageView backImageView;
    private TextView titleTextView;

    private WebView mWebView;
    private ProgressBar mProgressBar;

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
        setContentView(R.layout.activity_browser);
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

        //实例化 toolbar
        backImageView = (ImageView) findViewById(R.id.backImageView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);

        //实例化 WebView 跟 进度条
        mWebView = (WebView) findViewById(R.id.mainWebView);
        mProgressBar = (ProgressBar) findViewById(R.id.mainProgressBar);

    }

    //初始化数据
    private void initData() {

        mActivity = this;
        mApplication = (NcApplication) getApplication();

        //获取链接地址以及模式
        linkString = mActivity.getIntent().getStringExtra("link");
        modelString = mActivity.getIntent().getStringExtra("model");

        //设置默认标题内容
        titleTextView.setText("加载中...");

        //初始化 webView 以及加载链接
        mApplication.mCookieManager.removeAllCookie();
        mApplication.mCookieManager.removeSessionCookie();
        mApplication.mCookieManager.removeExpiredCookie();
        ControlUtil.setWebView(mWebView);

        if (modelString.equals("goods_introduce")) {
            titleTextView.setText("商品介绍");
            getJson();
            return;
        }

        if (modelString.equals("login")) {
            mWebView.loadUrl(linkString);
            return;
        }

        if (!linkString.contains("http")) {
            linkString = "http://" + linkString;
        }

        if (!linkString.substring(linkString.length() - 1, linkString.length()).equals("/")) {
            linkString = linkString + "/";
        }

        if (linkString.contains("html/")) {
            linkString = linkString.replace("html/", "html");
        }

        mWebView.loadUrl(linkString);

    }

    //初始化事件
    private void initEven() {

        //返回按钮
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnActivity();
            }
        });

        //webView 设置 client
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.contains("key=")) {
                    String key = url.substring(url.lastIndexOf("=") + 1, url.length());
                    Intent intent = new Intent();
                    intent.putExtra("key", key);
                    mActivity.setResult(RESULT_OK, intent);
                    mApplication.finishActivity(mActivity);
                    return;
                }
                String cookie = mApplication.mCookieManager.getCookie(url);
                if (cookie != null) {
                    if (cookie.contains("key=")) {
                        cookie = cookie.substring(cookie.indexOf("key=") + 4, cookie.length());
                        String key = cookie.substring(0, cookie.indexOf(";"));
                        Intent intent = new Intent();
                        intent.putExtra("key", key);
                        mActivity.setResult(RESULT_OK, intent);
                        mApplication.finishActivity(mActivity);
                        return;
                    }
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                linkString = url;
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    return super.shouldInterceptRequest(view, url);
                } else {
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                    return null;
                }
            }

        });

        //webView 设置 chromeClient
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleTextView.setText(title);
                super.onReceivedTitle(view, title);
            }

        });

    }

    //读取JSON数据
    private void getJson() {

        mApplication.mFinalHttp.get(linkString, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                mWebView.loadDataWithBaseURL(null, TextUtil.encodeHtml(o.toString()), "text/html", "UTF-8", null);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                getJsonFailure();
            }
        });

    }

    //读取JSON数据失败
    private void getJsonFailure() {

        DialogUtil.query(mActivity, "是否重试?", "读取数据失败", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.cancel();
                getJson();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApplication.finishActivity(mActivity);
                DialogUtil.cancel();
            }
        });

    }

    //返回&销毁Activity
    private void returnActivity() {


        mWebView.clearHistory();
        mWebView.clearMatches();
        mWebView.clearFormData();
        mWebView.clearCache(true);
        mWebView.clearSslPreferences();

        mApplication.mCookieManager.removeAllCookie();
        mApplication.mCookieManager.removeSessionCookie();
        mApplication.mCookieManager.removeExpiredCookie();

        switch (modelString) {
            case "normal":
                mApplication.finishActivity(mActivity);
                break;
            default:
                mApplication.finishActivity(mActivity);
                break;
        }

    }

}