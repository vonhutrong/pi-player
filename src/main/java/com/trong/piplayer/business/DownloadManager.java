package com.trong.piplayer.business;

import com.trong.piplayer.bean.UrlInfo;
import com.trong.piplayer.util.Utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class DownloadManager {
    private static DownloadManager instance = new DownloadManager();
    private static List<String> downloadingUrls = new ArrayList<>();

    private DownloadManager() {}

    public static DownloadManager getInstance() {
        return instance;
    }

    public String download(final String url) {
        URL website;
        UrlInfo urlInfo;
        ReadableByteChannel rbc;
        try {
            website = new URL(url);
            rbc = Channels.newChannel(website.openStream());
            urlInfo = Utils.getUrlInfo(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (urlInfo != null) {
            new Thread(() -> {
                try {
                    FileOutputStream fos = new FileOutputStream("/share/" + urlInfo.getFileName() + "." + urlInfo.getExtension());
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    fos.close();
                    downloadingUrls.remove(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            downloadingUrls.add(url);
            return url;
        } else {
            return null;
        }
    }

    public static List<String> downloading() {
        return downloadingUrls;
    }

}
