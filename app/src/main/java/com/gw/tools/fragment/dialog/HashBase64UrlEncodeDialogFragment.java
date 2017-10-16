package com.gw.tools.fragment.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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

import static com.gw.tools.lib.util.StringUtil.MD5;
import static com.gw.tools.lib.util.StringUtil.SHA_1;
import static com.gw.tools.lib.util.StringUtil.SHA_256;
import static com.gw.tools.lib.util.StringUtil.SHA_512;

/**
 * Created by GongWen on 17/10/18.
 */

public class HashBase64UrlEncodeDialogFragment extends AppCompatDialogFragment {
    private Unbinder unbinder;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.originEt)
    AppCompatEditText originEt;
    @BindView(R.id.targetTitleTv)
    TextView targetTitleTv;
    @BindView(R.id.targetEt)
    AppCompatEditText targetEt;
    private String[] mItems = new String[]{MD5, SHA_1, SHA_256, SHA_512, "Base64", "URL-ENCODED"};
    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private String title;
    private int spinnerPosition = 0;

    public static HashBase64UrlEncodeDialogFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        HashBase64UrlEncodeDialogFragment fragment = new HashBase64UrlEncodeDialogFragment();
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
        View mView = inflater.inflate(R.layout.dialog_hash_base64_urlencode, container, true);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTv.setText(title);
        originEt.addTextChangedListener(originEtTextWatcherAdapter);
        targetEt.addTextChangedListener(targetEtTextWatcherAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @OnItemSelected(R.id.spinner)
    void onItemSelected(int position) {
        this.spinnerPosition = position;
        String txt = originEt.getText().toString();
        targetTitleTv.setText(mItems[position] + " 结果 :");
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
                targetEt.setEnabled(false);
                setTextNoTextChangeListener(targetEt, targetEtTextWatcherAdapter, toHash(txt, getHashAlgorithm(position)));
                break;
            case 4:
                targetEt.setEnabled(true);
                setTextNoTextChangeListener(targetEt, targetEtTextWatcherAdapter, encode4Base64(txt));
                break;
            case 5:
                targetEt.setEnabled(true);
                setTextNoTextChangeListener(targetEt, targetEtTextWatcherAdapter, urlEncode(txt));
                break;
        }
    }

    private TextWatcherAdapter originEtTextWatcherAdapter = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable text) {
            switch (spinnerPosition) {
                case 0:
                case 1:
                case 2:
                case 3:
                    setTextNoTextChangeListener(targetEt, targetEtTextWatcherAdapter, toHash(text.toString(), getHashAlgorithm(spinnerPosition)));
                    break;
                case 4:
                    setTextNoTextChangeListener(targetEt, targetEtTextWatcherAdapter, encode4Base64(text.toString()));
                    break;
                case 5:
                    setTextNoTextChangeListener(targetEt, targetEtTextWatcherAdapter, urlEncode(text.toString()));
                    break;
            }
        }
    };
    private TextWatcherAdapter targetEtTextWatcherAdapter = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable text) {
            try {
                switch (spinnerPosition) {
                    case 4:
                        setTextNoTextChangeListener(originEt, originEtTextWatcherAdapter, decode4Base64(text.toString()));
                        break;
                    case 5:
                        setTextNoTextChangeListener(originEt, originEtTextWatcherAdapter, urlDecode(text.toString()));
                        break;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                //ToastUtil.shortToast(getActivity(), e.getMessage());
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public String getHashAlgorithm(int position) {
        return mItems[position];
    }

    public String toHash(String plainText, String algorithm) {
        return StringUtil.toHash(plainText, algorithm);
    }

    private String encode4Base64(String str) {
        return StringUtil.encodeToString4Base64(StringUtil.string2Bytes(str), Base64.DEFAULT);
    }

    private String decode4Base64(String str) {
        return StringUtil.decodeToString4Base64(str, Base64.DEFAULT);
    }

    private String urlEncode(String str) {
        return StringUtil.urlEncode(str);
    }

    private String urlDecode(String str) {
        return StringUtil.urlDecode(str);
    }

    private void setTextNoTextChangeListener(TextView textView, TextWatcher textListener, String text) {
        textView.removeTextChangedListener(textListener);
        textView.setText(text);
        textView.addTextChangedListener(textListener);
    }

}
