package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.myapplication.kongjian.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView m_recyclerView;
    private List<String> m_Datas;
    private List<String> m_http;
    private HomeAdapter m_adapter;
    private int m_demoMode = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        m_recyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        m_recyclerView.setAdapter(m_adapter = new HomeAdapter());
        m_adapter.setOnItemClickLitener(new OnItemClickLitener()
        {

            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(MainActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position)
            {
                Toast.makeText(MainActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
                m_adapter.removeData(position);
            }
        });
        switch (m_demoMode)
        {
            case 1:
                m_recyclerView.setLayoutManager(new LinearLayoutManager(this));
                m_recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
                break;
            case 2:
                m_recyclerView.setLayoutManager(new GridLayoutManager(this,4));
                m_recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
                break;
            case 3:
                m_recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                m_recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
                break;
        }
        // 设置item动画
        m_recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.id_action_add:
                m_adapter.addData(1);
                break;
            case R.id.id_action_delete:
                m_adapter.removeData(1);
                break;
        }
        return true;
    }
    protected void initData()
    {
        m_Datas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            m_Datas.add("" + (char) i);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {
        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
        {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_home,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            switch (m_demoMode)
            {
                case 3:
                    holder.setHeight((int)(Math.random()*(100+position)+200));
            }
            holder.setString(m_Datas.get(position));
//            holder.setImage(R.drawable.timg);
            // 如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null)
            {
                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return m_Datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView text;
            View v;
            CircleImageView im;
            public MyViewHolder(View view)
            {
                super(view);
                v = view;
                text = (TextView)view.findViewById(R.id.id_num);
                im = (CircleImageView)view.findViewById(R.id.id_im);
                if (text==null)
                    Log.e("show", "text is null");

            }
            public void setString(String str)
            {
                if (text != null)
                    text.setText(str);
            }

            public void setHeight(int height)
            {
                ViewGroup.LayoutParams p =  v.getLayoutParams();
                p.height = height;
                v.setLayoutParams(p);
                text.setHeight(height);
            }

            public void setImage(int r)
            {
                im.setImageResource(r);
            }
        }
        public void addData(int position) {
            m_Datas.add(position, "Insert One");
            notifyItemInserted(position);
        }

        public void removeData(int position) {
            m_Datas.remove(position);
            notifyItemRemoved(position);
        }
    }
}


