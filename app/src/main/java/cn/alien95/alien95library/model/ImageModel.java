package cn.alien95.alien95library.model;

import cn.alien95.alien95library.config.API;
import cn.alien95.alien95library.model.bean.ImageRespond;
import retrofit2.Callback;
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

    public static void getImagesFromNet(String query, int page, Callback<ImageRespond> callback) {
        APIService service = retrofit.create(APIService.class);
        service.getImageRespond(query, page, "ajax", "result").enqueue(callback);
    }

}
