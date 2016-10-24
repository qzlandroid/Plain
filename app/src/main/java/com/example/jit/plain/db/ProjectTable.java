package com.example.jit.plain.db;

import android.text.Spanned;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Max on 2016/7/21.
 */
@Table(name = "ProjectTable")
public class ProjectTable {
    @Column(name = "id", isId = true, autoGen = true)
    public String id;

    @Column(name = "title")
    public String title;

    @Column(name = "image")
    public String image;

    @Column(name = "content")
    public String content;

    @Column(name = "type")
    public String type;

    @Column(name = "uploadtime")
    public String uploadtime;

    @Column(name = "htmltext")
    public Spanned htmltext;

    @Override
    public String toString() {
        return "ProjectTable{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", uploadtime='" + uploadtime + '\'' +
                ", htmltext=" + htmltext +
                '}';
    }
}
