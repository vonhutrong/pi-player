package com.trong.piplayer.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfo {
    private String fileName;
    private String extension;
    private String url;
}
