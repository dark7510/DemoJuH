package ypx.com.demojh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import ypx.com.demojh.adapter.NewsAdapter;
import ypx.com.demojh.bean.News;
import ypx.com.demojh.common.Common;
import ypx.com.demojh.common.ServerConfig;
import ypx.com.demojh.ui.activity.NewDetailActivity;

public class MainActivity extends AppCompatActivity {
    private ListView lv_news;
    private List<News> data=new ArrayList<>();
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setEvent();
    }

    private void setEvent() {
        newsAdapter = new NewsAdapter(data);
        lv_news.setAdapter(newsAdapter);
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this, NewDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("news",data.get(position));
                intent.putExtras(bundle);
                MainActivity.this.startActivity(intent);
            }
        });

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
                        Toast.makeText(MainActivity.this,"请求数据失败",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSONObject.parseObject(response);


                        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
                        //data = jsonArray.toJavaList(News.class);
                        data.addAll(JSONArray.parseArray(jsonArray.toJSONString(),News.class));
                        Log.i("News","数据大小"+data.size());
                        newsAdapter.notifyDataSetChanged();

                    }
                });
    }
}
