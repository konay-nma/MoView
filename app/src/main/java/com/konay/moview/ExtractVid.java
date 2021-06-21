package com.konay.moview;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.konay.moview.services.Facebook;

import static com.konay.moview.utils.FacebookUtils.check_fb_url;

public class ExtractVid {
    private Context context;
    private OnTaskCompleted onTaskComplete;
    public static final String agent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.99 Safari/537.36";

    public ExtractVid(Context context) {
        this.context = context;
        AndroidNetworking.initialize(context);
    }

    public void find(String url) {
        if(check_fb_url(url)) {
            Facebook.fetch(url, onTaskComplete);
        }
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(String sd, String hd);
        void onError();
    }

    public void onFinish(OnTaskCompleted onTaskCompleted) {
        this.onTaskComplete = onTaskCompleted;
    }
}
