package com.gw.tools.fragment.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gw.tools.R;
import com.gw.tools.lib.util.StringUtil;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by GongWen on 17/10/18.
 */

public class EncryptFragment extends AppCompatDialogFragment {
    Unbinder unbinder;

    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.textView)
    AppCompatEditText textView;
    @BindView(R.id.keyView)
    AppCompatEditText keyView;
    @BindView(R.id.resultView)
    AppCompatTextView resultView;
    @BindView(R.id.encryptView)
    AppCompatButton encryptView;

    private static final String EXTRA_TITLE = "EXTRA_TITLE";

    private String title;

    public static EncryptFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        EncryptFragment fragment = new EncryptFragment();
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
        View mView = inflater.inflate(R.layout.dialog_encrypte, container, true);
        unbinder = ButterKnife.bind(this, mView);
        titleTv.setText(title);
        return mView;
    }

    @OnClick(R.id.encryptView)
    void onClick() {
        try {
            byte[]bytes=StringUtil.encryption4DES(textView.getText().toString(), keyView.getText().toString());
            resultView.setText(StringUtil.encodeToString4Base64(bytes, Base64.DEFAULT));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
