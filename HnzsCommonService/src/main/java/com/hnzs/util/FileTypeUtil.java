package com.hnzs.util;


public class FileTypeUtil {
    //	-- \0其他
    //	-- \1视频
    //	-- \2音频
    //	-- \3图片
    //	-- \4文档

    public static String fileType(String fileName) {
        if (fileName == null) {
            fileName = "文件名为空！";
            return "0";
        } else {
            // 获取文件后缀名并转化为消息写，用于后续比较
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
            // 创建视频类型数组
            String video[] = { "mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm","mpg","mxf" };
            for (int i = 0; i < video.length; i++) {
                if (video[i].equals(fileType)) {
                    return "1";
                }
            }
            // 创建音乐类型数组
            String music[] = { "mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
                    "m4a", "vqf" , "amr"};
            for (int i = 0; i < music.length; i++) {
                if (music[i].equals(fileType)) {
                    return "2";
                }
            }
            // 创建图片类型数组
            String img[] = { "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
                    "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf" };
            for (int i = 0; i < img.length; i++) {
                if (img[i].equals(fileType)) {
                    return "3";
                }
            }
            // 创建文档类型数组
            String document[] = { "txt", "doc", "docx", "xls","xlsx", "htm", "html", "jsp", "rtf", "wpd", "pdf", "ppt", "pptx" };
            for (int i = 0; i < document.length; i++) {
                if (document[i].equals(fileType)) {
                    return "4";
                }
            }
        }
        return "0";
    }
}