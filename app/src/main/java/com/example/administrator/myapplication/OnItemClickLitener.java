package com.example.administrator.myapplication;

import android.view.View;

/**
 * Created by Administrator on 2017/2/9.
 */
public interface OnItemClickLitener
{
    void onItemClick(View view, int position);
    void onItemLongClick(View view , int position);
}