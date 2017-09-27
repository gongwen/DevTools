package com.gw.tools.lib.util;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class ViewHolder<TH> extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;
    private View mItemView;
    private TH data;

    public ViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
        this.mItemView = itemView;
    }

    public View getItemView() {
        return mItemView;
    }

    public <T extends View> T getView(int viewId) {
        View mView = mViews.get(viewId);
        if (mView == null) {
            mView = mItemView.findViewById(viewId);
            mViews.put(viewId, mView);
        }
        return (T) mView;
    }

    public TH getData() {
        return data;
    }

    public ViewHolder setData(TH data) {
        this.data = data;
        return this;
    }

    public ViewHolder setText(int viewId, String text) {
        ((TextView) getView(viewId)).setText(text);
        return this;
    }
}