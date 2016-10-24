package com.example.jit.model;

import android.text.Html;
import android.text.Spanned;

/**
 * 首页文章列表的数据类
 * Created by Max on 2016/7/9.
 */
public class ArticlesValueBean {


    /**
     * id :
     * title :
     * image :
     * type :
     * content : 内容（前50个字）
     * uploadtime : 发布时间
     */

    private String id;
    private String title;
    private String image;
    private String content;
    private String type;
    private String uploadtime;
    private Spanned htmltext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Spanned getHtmltext() {
        return htmltext;
    }

    public void setHtmltext(String content) {
        this.htmltext = Html.fromHtml(content);
    }
}
