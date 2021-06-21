package com.konay.moview.services;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.konay.moview.ExtractVid;

import static com.konay.moview.ExtractVid.agent;
import static com.konay.moview.utils.FacebookUtils.getHDLink;
import static com.konay.moview.utils.FacebookUtils.getSDLink;

public class Facebook {
    public static void fetch(String url, final ExtractVid.OnTaskCompleted onTaskCompleted) {
        AndroidNetworking.post("https://fbdown.net/download.php")
                .addBodyParameter("URLz", "https://www.facebook.com/video.php?v="+ url)
                .addHeaders("User-agent", agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        String sd = getSDLink(response);
                        String hd = getHDLink(response);
                        onTaskCompleted.onTaskCompleted(sd,hd);
                    }

                    @Override
                    public void onError(ANError anError) {
                        onTaskCompleted.onError();
                    }
                });
    }
}
