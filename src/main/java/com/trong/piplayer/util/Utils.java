package com.trong.piplayer.util;

import com.trong.piplayer.bean.UrlInfo;
import org.apache.commons.io.FilenameUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

    public static UrlInfo getUrlInfo(final String urlString) {
        URL url;
        try {
            url = new URL(urlString);
            String urlPath = url.getPath();
            return new UrlInfo(FilenameUtils.getBaseName(urlPath), FilenameUtils.getExtension(urlPath), urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
