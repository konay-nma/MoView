package com.konay.moview.utils;

public class FacebookUtils {
    public static String getSDLink(String response) {
        if(response == null) return null;
        String[] tokens = response.split("id=\"sdlink\"");
        if(tokens.length < 2) return null;
        tokens = tokens[1].split("href=\"");
        if(tokens.length < 2) return null;
        tokens = tokens[1].split("\"");
        if(tokens.length < 2) return null;
        return tokens[0].replace("&amp;", "&");
    }
    public static String getHDLink(String response) {
        if(response == null) return null;
        String[] tokens = response.split("id=\"hdlink\"");
        if(tokens.length < 2) return null;
        tokens = tokens[1].split("href=\"");
        if(tokens.length < 2) return null;
        tokens = tokens[1].split("\"");
        if(tokens.length < 2) return null;
        return tokens[0].replace("&amp;", "&");
    }

    public static boolean check_fb_url(String url) {
        return url.matches("-?\\d+(\\.\\d+)?");
    }
}
