package com.jkb.slidemenulayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.slidemenulayout.R;

/**
 * TabTwo的数据适配器
 * Created by yangjing on 17-6-8.
 */

public class TabTwoAdapter extends RecyclerView.Adapter<TabTwoAdapter.ViewHolder> {

    private Context context;

    public TabTwoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab_two, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvFriend.setText("Tab two item" + position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFriend;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFriend = (TextView) itemView.findViewById(R.id.if_tv);
        }
    }
}
