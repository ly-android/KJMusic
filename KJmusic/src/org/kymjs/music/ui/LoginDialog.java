package org.kymjs.music.ui;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.music.R;
import org.kymjs.music.utils.StringUtils;

import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;

/**
 * 用户登录对话框
 */
public class LoginDialog extends KJActivity {

    private ViewSwitcher mViewSwitcher;
    private ImageButton btn_close;
    private Button btn_login;
    private AutoCompleteTextView mAccount;
    private EditText mPwd;
    private AnimationDrawable loadingAnimation;
    private View loginLoading;
    private InputMethodManager imm;

    public final static int LOGIN_OTHER = 0x00;
    public final static int LOGIN_MAIN = 0x01;
    public final static int LOGIN_SETTING = 0x02;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_login);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            KJActivityStack.create().finishActivity(LoginDialog.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initWidget() {
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        mViewSwitcher = (ViewSwitcher) findViewById(R.id.logindialog_view_switcher);
        loginLoading = findViewById(R.id.login_loading);
        mAccount = (AutoCompleteTextView) findViewById(R.id.login_account);
        mPwd = (EditText) findViewById(R.id.login_password);

        btn_close = (ImageButton) findViewById(R.id.login_close_button);
        btn_close.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.login_btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if (v.getId() == R.id.login_btn_login) {
            // 隐藏软键盘
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            String account = mAccount.getText().toString();
            String pwd = mPwd.getText().toString();
            // 判断输入
            if (StringUtils.isEmpty(account)) {
                ViewInject.toast(v.getContext(),
                        v.getContext().getString(R.string.input_username));
                return;
            }
            if (StringUtils.isEmpty(pwd)) {
                ViewInject.toast(v.getContext(),
                        v.getContext().getString(R.string.input_pwd));
                return;
            }

            btn_close.setVisibility(View.GONE);
            loadingAnimation = (AnimationDrawable) loginLoading.getBackground();
            loadingAnimation.start();
            mViewSwitcher.showNext();
        } else if (v.getId() == R.id.login_close_button) {
            KJActivityStack.create().finishActivity(LoginDialog.this);
        }
    }
}
