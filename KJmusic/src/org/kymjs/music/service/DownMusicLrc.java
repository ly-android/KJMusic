package org.kymjs.music.service;

import java.io.File;
import java.io.IOException;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.music.Config;
import org.kymjs.music.bean.Music;
import org.kymjs.music.utils.FileUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class DownMusicLrc extends Service {
    private final DownLrcHolder holder = new DownLrcHolder();

    public class DownLrcHolder extends Binder {
        public DownMusicLrc getService() {
            return DownMusicLrc.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return holder;
    }

    public void downLrc(Music music) {
        FileUtils fUtils = new FileUtils();
        File savePath = fUtils.getSavePath();
        if (music == null || music.getLrcUrl() == null
                || music.getLrcUrl().length() < 1) {
            KJLoger.debug(getClass() + "----歌曲下载失败,music对象有问题");
        } else if (savePath == null) {
            KJLoger.debug(getClass() + "----好像是没有存储卡");
        } else {
            File fileLrc = new File(savePath.toString() + "/"
                    + music.getTitle() + ".lrc");
            if (!fileLrc.exists()) {
                try {
                    fileLrc.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            KJHttp http = new KJHttp();
            File saveFile = new File(savePath.toString() + "/"
                    + music.getTitle() + ".lrc");
            http.download(music.getLrcUrl(), saveFile, new HttpCallBack() {
                @Override
                public void onSuccess(File t) {
                    super.onSuccess(t);
                    KJLoger.debug(getClass() + "数据下载：---onSuccess-----"
                            + t.getAbsolutePath());
                    Intent intent = new Intent(Config.RECEIVER_DOWNLOAD_LYRIC);
                    sendBroadcast(intent);
                }
            });
        }
    }
}
