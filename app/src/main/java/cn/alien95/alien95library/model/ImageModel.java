package cn.alien95.alien95library.model;

import cn.alien95.alien95library.config.API;
import cn.alien95.alien95library.model.bean.Image;
import cn.alien95.alien95library.model.bean.ImageRespond;
import cn.alien95.alien95library.util.LoggingInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class ImageModel {

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API.GET_IAMGES_BASEURL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static APIService service = retrofit.create(APIService.class);

    public static rx.Observable<Image[]> getImagesFromNet(String query, int page){
        return service.getImageRespond(query, page, "ajax", "result")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ImageRespond, Image[]>() {
                    @Override
                    public Image[] call(ImageRespond imageRespond) {
                        return imageRespond.getItems();
                    }
                });
    }

}
