package com.example.jit.model;

/**
 * 首页轮播图的数据类
 * Created by Max on 2016/7/9.
 */
public class CarouselValueBean {


    /**
     * id : 文章id
     * title : 标题
     * image : 图片
     * uploadtime : 发布时间
     */

    private String id;
    private String title;
    private String image;
    private String type;
    private String uploadtime;

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

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
