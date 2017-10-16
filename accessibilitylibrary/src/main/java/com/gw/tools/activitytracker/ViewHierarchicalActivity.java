package com.gw.tools.activitytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gw.tools.util.ViewHierarchicalTreeNodeManager;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by GongWen on 17/10/13.
 */

public class ViewHierarchicalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TreeNode root = ViewHierarchicalTreeNodeManager.getInstance().getTreeNode();
        AndroidTreeView tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyle);
        setContentView(tView.getView());
    }
}
