package com.github.annybudong;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.github.annybudong.slipview.SlipView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getName();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new MyAdapter(this, mockData()));
    }

    private List<String> mockData() {
        List<String> data = new ArrayList<>();
        String temp = " item";
        for(int i = 0; i < 50; i++) {
            data.add(i + temp);
        }

        return data;
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements SlipView.OnScrollListener {

        private Context ctx;
        private List<String> data;

        public MyAdapter(Context ctx, List<String> data) {
            this.ctx = ctx;
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // 绑定数据
            holder.contentTv.setText(data.get(position));
            holder.contentTv.setTag(position);
            holder.rooView.closeMenu();
            holder.rooView.setOnScrollListener(this);
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public void onScrollStart() {
            Log.d(TAG, "onScrollStart");
        }

        @Override
        public void onScrollEnd() {
            Log.e(TAG, "onScrollEnd");
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private boolean scrollable = true;
            SlipView rooView;
            TextView contentTv;
            TextView deleteMenu;
            TextView editMenu;

            public ViewHolder(final View itemView) {
                super(itemView);
                rooView = (SlipView) itemView;
                contentTv = (TextView) itemView.findViewById(R.id.content);
                deleteMenu = (TextView) itemView.findViewById(R.id.menu_delete);
                editMenu = (TextView) itemView.findViewById(R.id.menu_edit);
                contentTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scrollable = !scrollable;
                        rooView.enableScroll(scrollable);
                        Toast.makeText(ctx, "允许侧滑:" + scrollable, Toast.LENGTH_SHORT).show();
                    }
                });
                deleteMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ctx, "click delete.", Toast.LENGTH_SHORT).show();
                    }
                });
                editMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ctx, "click edit.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
