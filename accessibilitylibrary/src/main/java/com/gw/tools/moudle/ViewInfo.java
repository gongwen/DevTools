package com.gw.tools.moudle;

import android.text.TextUtils;

public class ViewInfo {

    private String viewType;
    private String viewBounds;
    private String viewText;
    private String viewIdResourceName;

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getViewBounds() {
        return viewBounds;
    }

    public void setViewBounds(String viewBounds) {
        this.viewBounds = viewBounds;
    }

    public String getViewText() {
        return viewText;
    }

    public void setViewText(String viewText) {
        this.viewText = viewText;
    }

    public String getViewIdResourceName() {
        return viewIdResourceName;
    }

    public void setViewIdResourceName(String viewIdResourceName) {
        this.viewIdResourceName = viewIdResourceName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(viewType);
        sb.append(" ");
        sb.append(viewBounds);
        if (!TextUtils.isEmpty(viewIdResourceName)) {
            sb.append(", id=");
            sb.append(viewIdResourceName);
        }
        if (!TextUtils.isEmpty(viewText)) {
            sb.append(", viewText=");
            sb.append(viewText);
        }
        return sb.toString();
    }
}