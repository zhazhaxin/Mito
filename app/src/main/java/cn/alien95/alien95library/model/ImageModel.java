package cn.alien95.alien95library.model;

import cn.alien95.alien95library.config.API;
import cn.alien95.alien95library.model.bean.ImageRespond;
import cn.alien95.alien95library.util.LoggingInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

    public static void getImagesFromNet(String query, int page, Observer<ImageRespond> observer) {

        service.getImageRespond(query, page, "ajax", "result")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())  //事件消费在主线程
                .doOnNext(new Action1<ImageRespond>() {
                    @Override
                    public void call(ImageRespond imageRespond) {
                          //要写在主线程
                    }
                })
                .subscribe(observer);
    }

}
