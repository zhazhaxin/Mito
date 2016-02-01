package cn.alien95.alien95library.model;

import cn.alien95.alien95library.model.bean.ImageRespond;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by linlongxin on 2016/1/31.
 */
public interface APIService {

    @GET("pics")
    Call<ImageRespond> getImageRespond(@Query("query") String query, @Query("start") int start,
                                       @Query("reqType") String reqType, @Query("reqFrom") String reqFrom);

}
