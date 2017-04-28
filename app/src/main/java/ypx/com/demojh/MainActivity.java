package ypx.com.demojh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import ypx.com.demojh.bean.News;
import ypx.com.demojh.common.Common;
import ypx.com.demojh.common.ServerConfig;

public class MainActivity extends AppCompatActivity {
    private ListView lv_news;
    private List<News> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        lv_news= (ListView) findViewById(R.id.lv_news);
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkHttpUtils
                .post()
                .url(ServerConfig.BASE_URL)
                .addParams("key", Common.API_NEWS_KEY)
                .addParams("type", "top")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }
}
