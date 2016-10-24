/**
 * Copyright (C) 2015. Keegan小钢（http://keeganlee.me）
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.jit.core;


import android.content.Context;

import com.example.jit.api.ApiResponse;
import com.example.jit.model.ArticlesValueBean;
import com.example.jit.model.CarouselValueBean;
import com.example.jit.model.HtmlValueBean;
import com.example.jit.model.VillageDetailValueBean;
import com.example.jit.model.VillageValueBean;

import java.util.List;

/**
 * 接收app层的各种Action
 *
 */
public interface AppAction {
    /**
     * 获取首页轮播图
     *
     * @param tag 最新0／惠农政策1／惠农项目2
     * @param listener 回调监听器
     */
    public void getCarousel(String tag, ActionCallbackListener<List<CarouselValueBean>> listener);

    /**
     * 获取首页文章列表
     *
     * @param tag 最新0／惠农政策1／惠农项目2
     * @param listener 回调监听器
     */
    public void getArticles(String tag, String lastupload,String sinceupload,ActionCallbackListener<List<ArticlesValueBean>> listener);

    /**
     * 获取文章详情
     *
     * @param type 文章分类 政策1/项目2
     * @param tag  文章id
     * @param listener 回调监听器
     */
    public void getArticlesDetail(String type, String tag, ActionCallbackListener<String> listener);

    /**
     * 获取轮播图详情
     *
     * @param uploadtime 更新时间
     * @param listener 回调监听器
     */
    public void getCarouselDetail(String uploadtime, ActionCallbackListener<String> listener);

    /**
     * 获取村庄信息
     *
     * @param listener 回调监听器
     */
    public void getVillage(ActionCallbackListener<List<VillageValueBean>> listener);

    /**
     * 获取村务内容列表
     *
     * @param regionid 地区id
     * @param listener 回调监听器
     */
    public void getVillageDetail(String regionid, ActionCallbackListener<List<List<VillageDetailValueBean>>> listener);

    /**
     * 获取文章详情
     *
     * @param id:文章id
     * @param type:文章类型 党务公开1／村务公开2／财务公开3／惠农资金4
     * @param listener 回调监听器
     */
    public void getVillageArticlesDetail(String id,String type, ActionCallbackListener<String> listener);

    /**
     * 搜索
     *
     * @param tag:标题0/内容1
     * @param key:搜索内容
     * @param listener 回调监听器
     */
    public void getSearch(String tag,String key, ActionCallbackListener<List<ArticlesValueBean>> listener);

}
