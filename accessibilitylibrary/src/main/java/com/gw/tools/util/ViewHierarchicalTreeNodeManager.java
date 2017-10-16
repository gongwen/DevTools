package com.gw.tools.util;

import android.view.accessibility.AccessibilityNodeInfo;

import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by GongWen on 17/10/13.
 */
// TODO: 17/10/16 优化性能，点击时在解析
public class ViewHierarchicalTreeNodeManager {
    private TreeNode treeNode;

    private static class SingletonHolder {
        private static final ViewHierarchicalTreeNodeManager INSTANCE = new ViewHierarchicalTreeNodeManager();
    }

    private ViewHierarchicalTreeNodeManager() {
    }

    public static ViewHierarchicalTreeNodeManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public TreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(AccessibilityNodeInfo nodeInfo) {
        treeNode = Util.transformer(nodeInfo);
    }
}
