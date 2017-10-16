package com.gw.tools.util;

import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.gw.tools.moudle.ViewInfo;
import com.unnamed.b.atv.model.TreeNode;

public class Util {
    private Util() {
    }

    public static TreeNode transformer(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return null;
        }
        TreeNode treeNode = TreeNode.root();
        recursion(treeNode, nodeInfo);
        return treeNode;
    }

    private static void recursion(TreeNode parentTreeNode, AccessibilityNodeInfo nodeInfo) {
        TreeNode treeNode = new TreeNode(parse(nodeInfo));
        parentTreeNode.addChild(treeNode);
        if (nodeInfo.getChildCount() > 0) {
            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                if (nodeInfo.getChild(i) != null) {
                    recursion(treeNode, nodeInfo.getChild(i));
                }
            }
        }
    }

    private static ViewInfo parse(AccessibilityNodeInfo nodeInfo) {
        ViewInfo viewInfo = new ViewInfo();
        String viewType = nodeInfo.getClassName().toString();
        int lastIndex = viewType.lastIndexOf(".");
        viewInfo.setViewType(viewType.substring(lastIndex + 1, viewType.length()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            viewInfo.setViewIdResourceName(nodeInfo.getViewIdResourceName());
        }
        Rect rect = new Rect();
        nodeInfo.getBoundsInScreen(rect);
        StringBuilder sb = new StringBuilder(32);
        sb.append("[");
        sb.append(rect.left);
        sb.append(",");
        sb.append(rect.top);
        sb.append(",");
        sb.append(rect.right);
        sb.append(",");
        sb.append(rect.bottom);
        sb.append("]");
        viewInfo.setViewBounds(sb.toString());
        if (!TextUtils.isEmpty(nodeInfo.getText())) {
            viewInfo.setViewText(nodeInfo.getText().toString());
        }
        return viewInfo;
    }
}