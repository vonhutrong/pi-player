package com.trong.piplayer.business;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class BasicWebCrawler {
    private static BasicWebCrawler instance = new BasicWebCrawler();

    private BasicWebCrawler() {}

    public static BasicWebCrawler getInstance() {
        return instance;
    }

    public ArrayList<String> getFileUrls() {
        String url = "http://trongvn13.ddns.net/share";
        try {
            Document document = Jsoup.connect(url).get();
            Elements linksOnPage = document.select("a[href]");
            ArrayList<String> fileUrls = new ArrayList<>();
            for (Element element : linksOnPage) {
                String href = element.attr("abs:href");
                fileUrls.add(href);
            }
            return fileUrls;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
