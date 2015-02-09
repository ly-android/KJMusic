package org.kymjs.music.utils;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

/**
 * 歌曲下载帮助类
 * 
 * @author kymjs
 */
public class MusicDownUtils {
    public static String getLrcXML(String musicName, String artist) {
        String xml = null;
        KJHttp kjh = new KJHttp();
        HttpParams params = new HttpParams();
        params.put("count", 1 + "");
        params.put("op", 12 + "");
        params.put("title", musicName + "$$" + artist + "$$$$");
        kjh.get("http://box.zhangmen.baidu.com/x", params,
                new HttpCallBack() {});
        return xml;
    }
}
