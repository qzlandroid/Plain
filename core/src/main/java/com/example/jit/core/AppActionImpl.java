/**
 * Copyright (C) 2015. Keegan小钢（http://keeganlee.me）
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.jit.core;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.jit.api.Api;
import com.example.jit.api.ApiImpl;
import com.example.jit.api.ApiResponse;
import com.example.jit.model.ArticlesValueBean;
import com.example.jit.model.CarouselValueBean;
import com.example.jit.model.VillageDetailValueBean;
import com.example.jit.model.VillageValueBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

/**
 * AppAction接口的实现类
 *
 */
public class AppActionImpl implements AppAction {

    private final static int LOGIN_OS = 1; // 表示Android
    private final static int PAGE_SIZE = 10; // 默认每页20条

    private Context context;
    private Api api;

    public AppActionImpl(Context context) {
        this.context = context;
        this.api = new ApiImpl();
    }

    @Override
    public void getCarousel(final String tag, final ActionCallbackListener<List<CarouselValueBean>> listener) {
        //参数检查
        if (TextUtils.isEmpty(tag)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "tag为空");
            }
            return;
        }

        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<List<CarouselValueBean>>>() {
            @Override
            protected ApiResponse<List<CarouselValueBean>> doInBackground(Void... voids) {
                return api.getCarousel(tag);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<CarouselValueBean>> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getValue());
                    } else {
                        listener.onFailure(response.getStatus(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getArticles(final String tag, final String lastupload, final String sinceupload,
                            final ActionCallbackListener<List<ArticlesValueBean>> listener) {
        //         参数检查
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(lastupload) || TextUtils.isEmpty(sinceupload)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "参数错误");
            }
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<List<ArticlesValueBean>>>() {
            @Override
            protected ApiResponse<List<ArticlesValueBean>> doInBackground(Void... voids) {
                return api.getArticles(tag, lastupload, sinceupload);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<ArticlesValueBean>> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getValue());
                    } else {
                        listener.onFailure(response.getStatus(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getArticlesDetail(final String type, final String tag,
                                  final ActionCallbackListener<String> listener) {
        //         参数检查
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(type)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "参数错误");
            }
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.getArticlesDetail(type, tag);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getUrl());
                    } else {
                        listener.onFailure(response.getStatus(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getCarouselDetail(final String uploadtime, final ActionCallbackListener<String> listener) {
        //         参数检查
        if (TextUtils.isEmpty(uploadtime)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "参数错误");
            }
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.getCarouselDetail(uploadtime);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getUrl());
                    } else {
                        listener.onFailure(response.getStatus(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getVillage(final ActionCallbackListener<List<VillageValueBean>> listener) {
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<List<VillageValueBean>>>() {
            @Override
            protected ApiResponse<List<VillageValueBean>> doInBackground(Void... voids) {
                return api.getVillage();
            }

            @Override
            protected void onPostExecute(ApiResponse<List<VillageValueBean>> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getValue());
                    } else {
                        listener.onFailure(response.getStatus(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getVillageDetail(final String regionid, final ActionCallbackListener<List<List<VillageDetailValueBean>>> listener) {
        //         参数检查
        if (TextUtils.isEmpty(regionid)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "参数错误");
            }
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<List<List<VillageDetailValueBean>>>>() {
            @Override
            protected ApiResponse<List<List<VillageDetailValueBean>>> doInBackground(Void... voids) {
                return api.getVillageDetail(regionid);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<List<VillageDetailValueBean>>> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getValue());
                    } else {
                        listener.onFailure(response.getStatus(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getVillageArticlesDetail(final String id, final String type,
                                         final ActionCallbackListener<String> listener) {
        //         参数检查
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(type)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "参数错误");
            }
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.getVillageArticlesDetail(id, type);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getUrl());
                    } else {
                        listener.onFailure(response.getStatus(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getSearch(final String tag, final String key,
                          final ActionCallbackListener<List<ArticlesValueBean>> listener) {
        //         参数检查
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(key)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "参数错误");
            }
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<List<ArticlesValueBean>>>() {
            @Override
            protected ApiResponse<List<ArticlesValueBean>> doInBackground(Void... voids) {
                return api.getSearch(tag, key);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<ArticlesValueBean>> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getValue());
                    } else {
                        listener.onFailure(response.getStatus(), response.getMsg());
                    }
                }
            }
        }.execute();
    }


}
