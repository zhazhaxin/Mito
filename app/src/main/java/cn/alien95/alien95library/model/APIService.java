package cn.alien95.alien95library.model;

import cn.alien95.alien95library.model.bean.ImageRespond;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by linlongxin on 2016/1/30.
 */
public interface APIService {

    @GET("query=/{word}/&start=/{page}/&reqType=ajax&reqFrom=result")
    Call<ImageRespond> getImagesInfo(@Path("word") String word, @Path("page") int page);
}
