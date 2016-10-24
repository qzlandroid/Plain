package com.example.jit.api;

import com.example.jit.model.ArticlesValueBean;
import com.example.jit.model.CarouselValueBean;
import com.example.jit.model.HtmlValueBean;
import com.example.jit.model.VillageDetailValueBean;
import com.example.jit.model.VillageValueBean;

import java.util.List;

/**
 * Created by Max on 2016/6/27.
 */
public interface Api {
    // 获取首页轮播图,tag：最新0／惠农政策1／惠农项目2
    public final static String Get_Carousel = "/mobile_gov/pagination?";
    // 获取首页文章列表,tag：最新0／惠农政策1／惠农项目2
    public final static String Get_Articles = "/mobile_gov/list?";
    // 获取文章详情,type:文章分类 政策1/项目2  id:文章id
    public final static String Get_ArticlesDetail = "/mobile_gov/detail?";
    // 获取轮播图详情
    public final static String Get_CarouselDetail = "/mobile_gov/pagdetail?";
    // 获取乡镇村庄信息
    public final static String Get_Village = "/mobile_con/region";
    // 获取村务内容列表,regionid:地区id  tag:党务公开1／村务公开2／财务公开3／惠农资金4
    // lastupload:加载的最新文章的时间戳 sinceupload:加载的最旧的文章的时间戳
    public final static String Get_VillageDetail = "/mobile_con/list?";
    // 获取文章详情,id:文章id type:文章类型 党务公开1／村务公开2／财务公开3／惠农资金4
    public final static String Get_VillageArticlesDetail = "/mobile_con/detail?";
    // 搜索,tag:标题0/内容1 key:搜索内容
    public final static String Get_Search = "/mobile_search?";


    /**
     * 获取首页轮播图
     *
     * @param tag 最新0／惠农政策1／惠农项目2
     * @return 成功时返回：{ "status": "success", "value":[]}
     */
    public ApiResponse<List<CarouselValueBean>> getCarousel(String tag);

    /**
     * 获取首页文章列表
     *
     * @param tag 最新0／惠农政策1／惠农项目2
     * @return 成功时返回： "status": "success", "value":[]}
     */
    public ApiResponse<List<ArticlesValueBean>> getArticles(String tag,String lastupload,String sinceupload);

    /**
     * 获取文章详情
     *
     * @param type 文章分类 政策1/项目2
     * @param tag  文章id
     * @return 成功时返回："url":html链接
     */
    public ApiResponse<Void> getArticlesDetail(String type, String tag);

    /**
     * 获取轮播图详情
     *
     * @param uploadtime 更新时间
     * @return 成功时返回："url":html链接
     */
    public ApiResponse<Void> getCarouselDetail(String uploadtime);

    /**
     * 获取村庄信息
     *
     * @return 成功时返回： "status": "success", "value":[]}
     */
    public ApiResponse<List<VillageValueBean>> getVillage();

    /**
     * 获取村务内容列表
     *
     * @param regionid 地区id
     * @return 成功时返回： "status": "success", "value":[]}
     */
    public ApiResponse<List<List<VillageDetailValueBean>>> getVillageDetail(String regionid);

    /**
     * 获取文章详情
     *
     * @param id:文章id
     * @param type:文章类型 党务公开1／村务公开2／财务公开3／惠农资金4
     * @return 成功时返回："url":html链接
     */
    public ApiResponse<Void> getVillageArticlesDetail(String id,String type);

    /**
     * 搜索
     *
     * @param tag:标题0/内容1
     * @param key:搜索内容
     * @return 成功时返回： "status": "success", "value":[]}
     */
    public ApiResponse<List<ArticlesValueBean>> getSearch(String tag,String key);


}
