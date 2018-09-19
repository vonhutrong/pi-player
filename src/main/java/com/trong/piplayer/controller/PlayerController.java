package com.trong.piplayer.controller;

import com.trong.piplayer.bean.UrlInfo;
import com.trong.piplayer.business.BasicWebCrawler;
import com.trong.piplayer.business.DownloadManager;
import com.trong.piplayer.dto.AddUrlForm;
import com.trong.piplayer.util.Utils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PlayerController {

    private static List<String> SUPPORTED_EXT = new ArrayList<String>() {{
        add("mp4");
        add("mkv");
        add("srt");
    }};

    @RequestMapping(value = {"/", "/home"})
    public String index(final Model model, @ModelAttribute(value = "addingUrlResult") String addingUrlResult) {
        ArrayList<String> fileUrls = BasicWebCrawler.getInstance().getFileUrls();
        List<UrlInfo> urlInfoList = new ArrayList<>();
        fileUrls.forEach(s -> {
            UrlInfo urlInfo = Utils.getUrlInfo(s);
            if (SUPPORTED_EXT.stream().anyMatch(s::contains)) {
                urlInfoList.add(urlInfo);
            }
        });
        model.addAttribute("urlInfoList", urlInfoList.stream()
                .sorted(Comparator.comparing(UrlInfo::getFileName))
                .collect(Collectors.toList()));

        model.addAttribute("addUrlForm", new AddUrlForm());
        model.addAttribute("downloadingUrls", DownloadManager.downloading());
        model.addAttribute("addingUrlResult", Strings.isEmpty(addingUrlResult) ? null : Boolean.valueOf(addingUrlResult));
        return "index";
    }

    @PostMapping("/addToDownloadList")
    public String addToDownloadList(@ModelAttribute AddUrlForm addUrlForm, final RedirectAttributes redirectAttributes) {
        String url = addUrlForm.getUrl();
        String result = DownloadManager.getInstance().download(url);
        redirectAttributes.addFlashAttribute("addingUrlResult", result != null ? String.valueOf(true) : String.valueOf(false));
        return "redirect:/home";
    }

}
