package cn.alien95.alien95library.model;

import cn.alien95.alien95library.config.API;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class ImageModel {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API.GET_IAMGES_BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static APIService getImageForNet() {
        APIService service = retrofit.create(APIService.class);
        return service;
    }

}
