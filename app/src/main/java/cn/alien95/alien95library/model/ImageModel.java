package cn.alien95.alien95library.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.alien95.alien95library.config.API;
import cn.alien95.set.http.request.HttpCallBack;
import cn.alien95.set.http.request.HttpRequest;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class ImageModel {


    public static void getImageForNet(String word, int page, HttpCallBack callBack) {
        try {
            String readWord = URLEncoder.encode(word, "GBK");
            String url = API.GET_IAMGES_BASEURL + "query=" + readWord + "&start=" + page * 20 + "&reqType=ajax&reqFrom=result";
            HttpRequest.getInstance().get(url, callBack);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
