package org.kymjs.music.ui;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.widget.KJViewPager;
import org.kymjs.kjframe.widget.KJViewPager.OnViewChangeListener;
import org.kymjs.music.Config;
import org.kymjs.music.R;
import org.kymjs.music.service.ScanMusic;
import org.kymjs.music.utils.PreferenceHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 首次安装时进入的欢迎界面
 * 
 * @author kymjs
 * 
 */
public class FirstInstallActivity extends KJActivity implements
        OnViewChangeListener {
    private LinearLayout pointLayout;
    private KJViewPager scrollLayout;
    private Button mBtnStart;
    private int count;
    private ImageView[] imgs;
    private int currentItem;
    private String scanToast;

    @BindView(id = R.id.scan_music_title)
    private TextView scanMusicName;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_welcome_first);
    }

    @Override
    public void initWidget() {
        pointLayout = (LinearLayout) findViewById(R.id.pointLayout);
        scrollLayout = (KJViewPager) findViewById(R.id.scrollLayout);
        mBtnStart = (Button) findViewById(R.id.startBtn);
        count = scrollLayout.getChildCount();
        imgs = new ImageView[count];
        for (int i = 0; i < count; i++) {
            imgs[i] = (ImageView) pointLayout.getChildAt(i);
            imgs[i].setEnabled(true);
            imgs[i].setTag(i);
        }
        currentItem = 0;
        imgs[currentItem].setEnabled(false);
        scrollLayout.setOnViewChangeListener(this);
        mBtnStart.setOnClickListener(this);

        loadRes();
        writeLog();
    }

    BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Config.RECEIVER_UPDATE_MUSIC_LIST.equals(intent.getAction())) {
                scanToast = "扫描到 "
                        + intent.getIntExtra(Config.SCAN_MUSIC_COUNT, 0)
                        + " 首歌曲";
            } else if (Config.RECEIVER_MUSIC_SCAN_FAIL.equals(intent
                    .getAction())) {
                scanToast = "呀，扫描失败了，再试一试？";
            }
        }
    };

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.RECEIVER_UPDATE_MUSIC_LIST);
        filter.addAction(Config.RECEIVER_MUSIC_SCAN_FAIL);
        registerReceiver(scanReceiver, filter);
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(scanReceiver);
    }

    @Override
    public void widgetClick(View v) {
        startActivity(new Intent(this, Main.class));
    }

    /**
     * 扫描本地歌曲
     */
    private void loadRes() {
        Intent it = new Intent();
        it.setClass(this, ScanMusic.class);
        startService(it);
    }

    /**
     * 写入本地记录
     */
    private void writeLog() {
        PreferenceHelper.write(this, Config.FIRSTINSTALL_FILE,
                Config.FIRSTINSTALL_KEY, false);
    }

    // 设置当前点
    @Override
    public void OnViewChange(int postion) {
        if (postion < 0 || postion > count - 1 || currentItem == postion) {
            return;
        }
        imgs[currentItem].setEnabled(true);
        imgs[postion].setEnabled(false);
        currentItem = postion;
        if (postion == count - 2) {
            scanMusicName.setText(scanToast);
        }

    }
}
