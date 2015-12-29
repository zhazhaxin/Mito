package cn.alien95.alien95library.model;

import java.util.HashMap;

import cn.alien95.alien95library.config.API;
import cn.alien95.set.http.HttpCallBack;
import cn.alien95.set.http.request.HttpRequest;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class ImageModel {

    public static void getImageForNet(int id,HttpCallBack callBack){
        HashMap<String,String> params = new HashMap<>();
        params.put("id",id + "");
        HttpRequest.getInstance(API.getImage).post(params, callBack);
    }
}
