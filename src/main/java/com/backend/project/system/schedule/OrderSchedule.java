package com.backend.project.system.schedule;

import com.alibaba.fastjson.JSONObject;
import com.backend.framework.config.ThreadPoolConfig;
import com.backend.framework.config.websocket.WebSocketServer;
import com.backend.project.system.domain.Device;
import com.backend.project.system.domain.ProductLink;
import com.backend.project.system.domain.websocketVo.WsPreOrderVo;
import com.backend.project.system.domain.websocketVo.WebSocketVo;
import com.backend.project.system.enums.WebsocketTitleEnum;
import com.backend.project.system.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 订单相关计划
 */
@Configuration
@EnableScheduling
@Slf4j
public class OrderSchedule {

    @Resource
    private ThreadPoolConfig threadPoolConfig;
    @Resource
    private AppConfigMapper appConfigMapper;
    @Resource
    private TaskPreOrderMapper taskPreOrderMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private ProductLinkMapper productLinkMapper;

    /**
     * task - preOrder
     * 提前生成预支付订单任务
     */
//    @Scheduled(fixedDelay = 60000L)
    private void preOrderTask() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
//            log.info("===== 提前生成预支付订单任务启动 =====");
            Integer orderNumberThreshold = Integer.valueOf(appConfigMapper.selectAppConfigByKy("order_number_threshold").getCValue());
            String orderAmountValue = appConfigMapper.selectAppConfigByKey("order_amount").getCValue();
            String[] amountSplit = orderAmountValue.split(",");
            for (String amount : amountSplit) {
                int count = orderInfoMapper.selectPreOrderUnused(amount);
                if (count < orderNumberThreshold) {
                    // 根据数量差额创建对应金额的预支付订单任务
                    int difference = orderNumberThreshold - count;
                    for (int i = 0; i < difference; i++) {
                        // 随机获取在线设备
                        Device device = deviceMapper.selectDeviceRand();
                        if (device == null) {
                            break;
                        }
                        ProductLink productLink = productLinkMapper.selectProductLinkRandByAmount(Double.valueOf(amount));
                        if (productLink == null) {
                            break;
                        }
                        try {
                            WsPreOrderVo wsPreOrderVo = new WsPreOrderVo(device.getId(), amount, productLink.getLink());
                            WebSocketVo webSocketVo = new WebSocketVo(WebsocketTitleEnum.preOrder.getTitle(), wsPreOrderVo);
                            String message = JSONObject.toJSONString(webSocketVo);
                            WebSocketServer.sendInfo(message, String.valueOf(device.getId()));
                        } catch (Exception e) {
                            log.info("preOrderTask exception ----->>>>", e);
                            continue;
                        }
                    }
                }
            }
        });
    }

}
