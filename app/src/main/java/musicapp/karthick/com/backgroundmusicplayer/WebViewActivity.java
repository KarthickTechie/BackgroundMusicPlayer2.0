package musicapp.karthick.com.backgroundmusicplayer;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    //playerIntent.putExtra(,"maplink");
    private String mUrl;
    private WebView webView;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        int code=getIntent().getExtras().getInt("requestCode");
        getUrl(code);
        //Toast.makeText(this,"request Code is :"+code,Toast.LENGTH_SHORT).show();
        webView= (WebView) findViewById(R.id.webView);
        mProgress= (ProgressBar) findViewById(R.id.id_progressBar);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(mUrl);
    }

    public void getUrl(int num){
        switch (num){

            case 100:
                mUrl=getIntent().getStringExtra("AboutSaiAarthi");

                break;

            case 101:
                mUrl=getIntent().getStringExtra("AboutSaiBaba");
                break;
            case 102:
                mUrl=getIntent().getStringExtra("AboutThursdayPooja");
                break;
            case 103:
                mUrl=getIntent().getStringExtra("AboutSpecialEvent");
                break;
            case 104:
                mUrl=getIntent().getStringExtra("AboutAnnathanam");
                break;
            case 105:
                mUrl=getIntent().getStringExtra("AboutUpahara");
                break;
            case 106:
                mUrl=getIntent().getStringExtra("AboutBalaVikas");
                break;
            case 107:

                break;
            case 108:

                break;
            case 109:

                break;
            case 110:
                mUrl=getIntent().getStringExtra("AboutDeveloper");
                break;
            case 111:

                break;
            case 112:
                mUrl=getIntent().getStringExtra("AboutFaceBookShare");
                break;

            case 113:
                mUrl=getIntent().getStringExtra("AboutDeveloper");
                break;
        }


    }

    public class MyWebViewClient extends WebViewClient{

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgress.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack())
        {

            webView.goBack();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
}

