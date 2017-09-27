package com.gw.tools.lib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gw.tools.lib.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected List<T> mList;
    protected final Context mContext;
    protected OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(Context mContext) {
        this(mContext, new ArrayList<T>());
    }

    public RecyclerViewAdapter(Context mContext, List<T> list) {
        this.mContext = mContext;
        this.mList = list != null ? list : new ArrayList<T>();
    }

    protected abstract int getLayoutId();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(getLayoutId(), parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.getItemView().setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(holder.getItemView(), holder.getLayoutPosition());
            }
        });
        onBindView(holder, position);
    }

    protected abstract void onBindView(ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setData(List<T> dataLists) {
        mList.clear();
        mList.addAll(dataLists);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        mList.add(data);
        notifyDataSetChanged();
    }

    public void addData(int position, T data) {
        mList.add(position, data);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }
}