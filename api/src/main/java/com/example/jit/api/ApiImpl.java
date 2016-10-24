package com.example.jit.api;

import com.example.jit.model.ArticlesValueBean;
import com.example.jit.model.CarouselValueBean;
import com.example.jit.model.VillageDetailValueBean;
import com.example.jit.model.VillageValueBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Api的实现类
 * Created by Max on 2016/6/27.
 */
public class ApiImpl implements Api {
    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";
    // http引擎
    private HttpEngine httpEngine;

    public ApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }


    @Override
    public ApiResponse<List<CarouselValueBean>> getCarousel(String tag) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("method",Get_Carousel);
        paramMap.put("tag", tag);

        Type type = new TypeToken<ApiResponse<List<CarouselValueBean>>>(){}.getType();
        try {
            return httpEngine.getHandle(paramMap,type);
        } catch (Exception e) {
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<List<ArticlesValueBean>> getArticles(String tag,String lastupload,String sinceupload) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("method",Get_Articles);
        paramMap.put("tag",tag);
        paramMap.put("lastupload",lastupload);
        paramMap.put("sinceupload",sinceupload);

        Type type = new TypeToken<ApiResponse<List<ArticlesValueBean>>>(){}.getType();

        try {
            return httpEngine.getHandle(paramMap,type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> getArticlesDetail(String type1, String id) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("method",Get_ArticlesDetail);
        paramMap.put("type",type1);
        paramMap.put("id",id);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();

        try {
            return httpEngine.getHandle(paramMap,type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> getCarouselDetail(String uploadtime) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("method",Get_CarouselDetail);
        paramMap.put("uploadtime",uploadtime);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();

        try {
            return httpEngine.getHandle(paramMap,type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<List<VillageValueBean>> getVillage()  {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("method",Get_Village);

        Type type = new TypeToken<ApiResponse<List<VillageValueBean>>>(){}.getType();

        try {
            return httpEngine.getHandle(paramMap,type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<List<List<VillageDetailValueBean>>> getVillageDetail(String regionid) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("method",Get_VillageDetail);
        paramMap.put("regionid",regionid);

        Type type = new TypeToken<ApiResponse<List<List<VillageDetailValueBean>>>>(){}.getType();

        try {
            return httpEngine.getHandle(paramMap,type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> getVillageArticlesDetail(String id, String type1) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("method",Get_VillageArticlesDetail);
        paramMap.put("id",id);
        paramMap.put("type",type1);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();

        try {
            return httpEngine.getHandle(paramMap,type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }

    }

    @Override
    public ApiResponse<List<ArticlesValueBean>> getSearch(String tag, String key) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("method",Get_Search);
        paramMap.put("tag",tag);
        paramMap.put("key",key);

        Type type = new TypeToken<ApiResponse<List<ArticlesValueBean>>>(){}.getType();

        try {
            return httpEngine.getHandle(paramMap,type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

}

