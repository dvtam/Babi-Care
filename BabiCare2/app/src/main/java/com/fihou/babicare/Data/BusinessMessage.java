package com.fihou.babicare.Data;

import com.fihou.babicare.Network.MessageKey;
import com.fihou.babicare.Network.MessagesID;

import org.json.JSONObject;

/**
 * Created by Administrator on 10/03/2017.
 */
public class BusinessMessage {
    public static JSONObject HelloDKM() {
        JSONObject res = new JSONObject();
        try {
            res.put(MessageKey.mid, "HELLO");
            res.put("uid", 120918);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static JSONObject pingServer() {
        try {
            JSONObject getPostList = new JSONObject();
            getPostList.put(MessageKey.mid, MessagesID.PING);
            getPostList.put(MessageKey.action, 0);
            return getPostList;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
    public static JSONObject pingReConnectServer(long timeout) {
        try {
            JSONObject getPostList = new JSONObject();
            getPostList.put(MessageKey.mid, MessagesID.PING);
            getPostList.put(MessageKey.timeout, timeout);
            return getPostList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
