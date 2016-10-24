package com.example.jit.model;

/**
 * Created by Max on 2016/7/9.
 */
public class VillageDetailValueBean {


    /**
     * id : 11
     * title : 村务公开五西村党务公开
     * content :
     * image : /eventUpload/0.6088956035673618.png
     * regionid : 43
     * categoryid : 1
     * uploadtime : 1468046822000
     * superid : null
     */

    private int id;
    private String title;
    private String content;
    private String image;
    private int regionid;
    private int categoryid;
    private String uploadtime;
    private Object superid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRegionid() {
        return regionid;
    }

    public void setRegionid(int regionid) {
        this.regionid = regionid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Object getSuperid() {
        return superid;
    }

    public void setSuperid(Object superid) {
        this.superid = superid;
    }
}
