package cn.alien95.set.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by linlongxin on 2015/12/27.
 */
public class HttpQueue {
    private LinkedBlockingDeque<Runnable> requestQueue;
    private static HttpQueue instance;
    private ExecutorService threadPool;

    private HttpQueue(){
        requestQueue = new LinkedBlockingDeque<>();
        threadPool = Executors.newFixedThreadPool(8);
    }

    public static HttpQueue getInstance(){
        if(instance == null){
            synchronized (HttpQueue.class){
                if(instance == null){
                    instance = new HttpQueue();
                }
            }
        }
        return instance;
    }

    public void addQuest(Runnable runnable){
        requestQueue.push(runnable);
        start();
    }

    private synchronized void start(){
        while (requestQueue.peek() != null){
            threadPool.execute(requestQueue.poll());
        }
    }


}
