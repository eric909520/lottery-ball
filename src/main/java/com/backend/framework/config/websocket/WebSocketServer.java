package com.backend.framework.config.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.project.system.enums.DeviceStatusEnum;
import com.backend.project.system.enums.WebsocketTitleEnum;
import com.backend.project.system.mapper.DeviceMapper;
import com.backend.project.system.service.IOrderInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

//前端请求的路径
@ServerEndpoint("/ws/{deviceId}")
@Component
public class WebSocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private IOrderInfoService orderInfoService;

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收deviceId
     */
    private String deviceId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("deviceId") String deviceId) {
        this.session = session;
        this.deviceId = deviceId;
        if (webSocketMap.containsKey(deviceId)) {
            webSocketMap.remove(deviceId);
            webSocketMap.put(deviceId, this);
            //加入set中
        } else {
            webSocketMap.put(deviceId, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        deviceMapper.updateDeviceStatus(Long.valueOf(deviceId), DeviceStatusEnum.online.getStatus());
        log.info("设备连接:" + deviceId + ",当前在线设备数为:" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */

    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(deviceId)) {
            webSocketMap.remove(deviceId);
            //从set中删除
            subOnlineCount();
            deviceMapper.updateDeviceStatus(Long.valueOf(deviceId), DeviceStatusEnum.offline.getStatus());
        }
        log.info("设备离线:" + deviceId + ",当前在线设备数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("设备消息:" + deviceId + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                String data = jsonObject.getString("data");
                String title = jsonObject.getString("title");
                // 生成预支付订单
                if (WebsocketTitleEnum.preOrder.getTitle().equals(title)) {
                    orderInfoService.savePreOrder(Long.valueOf(deviceId), data);
                } else if (WebsocketTitleEnum.confirmOrder.getTitle().equals(title)) {
                    // 确认订单支付
                    orderInfoService.orderConfirm(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("设备错误:" + this.deviceId + ",原因:" + error.getMessage());
        deviceMapper.updateDeviceStatus(Long.valueOf(deviceId), DeviceStatusEnum.offline.getStatus());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */

    public void sendMessage(String message) throws IOException {
        log.info("服务器消息推送：" + message);
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送自定义消息
     */
    public static Boolean sendInfo(String message, @PathParam("deviceId") String deviceId) throws IOException {
        // 心跳检测
        if (webSocketMap.get(deviceId) != null) {
            webSocketMap.get(deviceId).sendPing();
        }

        log.info("发送消息到:" + deviceId + "，报文:" + message);
        if (StringUtils.isNotBlank(deviceId) && webSocketMap.containsKey(deviceId)) {
            webSocketMap.get(deviceId).sendMessage(message);
            return true;
        } else {
            log.error("设备" + deviceId + ",不在线！");
        }
        return false;
    }

    /**
     * 发送ping消息
     * @return
     */
    public void sendPing() {
        try {
            this.session.getBasicRemote().sendPing(ByteBuffer.wrap("SUCCESS".getBytes()));
        } catch (IOException e) {
            onClose();
            throw new RuntimeException(e);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
