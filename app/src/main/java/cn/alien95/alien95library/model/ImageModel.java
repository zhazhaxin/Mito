package cn.alien95.alien95library.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import alien95.cn.http.request.HttpRequest;
import alien95.cn.http.request.callback.HttpCallBack;
import cn.alien95.alien95library.config.API;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class ImageModel {

    public static void getDataFromNet(String searchWord, int page, HttpCallBack callBack) {
        try {
            String word = URLEncoder.encode(searchWord, "GBK");
            String url = API.GET_IAMGES_BASEURL + "query=" + word + "&start=" + page + "&reqType=ajax&reqFrom=result";
            HttpRequest.getInstance().get(url, callBack);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
