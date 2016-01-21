package cn.alien95.set.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linlongxin on 2016/1/19.
 */
public class MessageNotify {

    private static MessageNotify instance;

    private Map<Object, Method> map = new HashMap<>();

    private MessageNotify() {
    }

    public static MessageNotify getInstance() {
        if (instance == null) {
            synchronized (MessageNotify.class) {
                instance = new MessageNotify();
            }
        }
        return instance;
    }

    public void registerEvent(Object object, Method method) {
        map.put(object, method);
    }

    private void executeEvent() {
        for (Map.Entry<Object, Method> entry : map.entrySet()) {
            try {

                if (entry.getKey() == null) {
                    Utils.Log("entry.getKey() == null");
                }
                if (entry.getValue() == null) {
                    Utils.Log("entry.getValue() == null");
                }
                entry.getValue().invoke(entry.getKey(), new Object[]{});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage() {
        executeEvent();
    }


}
