package com.gw.tools.fragment.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gw.tools.R;
import com.gw.tools.lib.adapter.TextWatcherAdapter;
import com.gw.tools.lib.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.Unbinder;

import static com.gw.tools.lib.util.StringUtil.GB2312;
import static com.gw.tools.lib.util.StringUtil.GBK;
import static com.gw.tools.lib.util.StringUtil.ISO_8859_1;
import static com.gw.tools.lib.util.StringUtil.US_ASCII;
import static com.gw.tools.lib.util.StringUtil.UTF_16;
import static com.gw.tools.lib.util.StringUtil.UTF_16BE;
import static com.gw.tools.lib.util.StringUtil.UTF_16LE;
import static com.gw.tools.lib.util.StringUtil.UTF_8;

/**
 * Created by GongWen on 17/10/18.
 */

public class EncodeTransformerDialogFragment extends AppCompatDialogFragment {
    private Unbinder unbinder;
    @BindView(R.id.titleTv)
    TextView titleTv;

    @BindView(R.id.originSpinner)
    Spinner originSpinner;
    @BindView(R.id.originEt)
    AppCompatEditText originEt;

    @BindView(R.id.targetSpinner)
    Spinner targetSpinner;
    @BindView(R.id.targetContentTv)
    TextView targetContentTv;
    private String[] mEncodeItems = new String[]{UTF_8, GBK, GB2312, US_ASCII, ISO_8859_1, UTF_16BE, UTF_16LE, UTF_16};
    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private String title;

    public static EncodeTransformerDialogFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        EncodeTransformerDialogFragment fragment = new EncodeTransformerDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        title = getArguments().getString(EXTRA_TITLE);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_Light_Dialog_Alert);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.dialog_string_encode, container, true);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTv.setText(title);
        originEt.addTextChangedListener(originEtTextWatcherAdapter);
        ArrayAdapter<String> originAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mEncodeItems);
        originAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        originSpinner.setAdapter(originAdapter);
        ArrayAdapter<String> targetAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mEncodeItems);
        targetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetSpinner.setAdapter(originAdapter);
    }

    @OnItemSelected(R.id.originSpinner)
    void onOriginItemSelected(int position) {
        updateContent();
    }

    @OnItemSelected(R.id.targetSpinner)
    void onTargetItemSelected(int position) {
        updateContent();
    }

    private TextWatcherAdapter originEtTextWatcherAdapter = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable text) {
            updateContent();
        }
    };

    private void updateContent() {
        String txt = originEt.getText().toString();
        targetContentTv.setText(StringUtil.changeCharset(txt,
                mEncodeItems[originSpinner.getSelectedItemPosition()],
                mEncodeItems[targetSpinner.getSelectedItemPosition()]));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
