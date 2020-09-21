package com.example.admin.config;

import com.example.admin.service.RestFulService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hui.yunfei@qq.com on 2019/6/4
 */
@Component
@EnableScheduling
public class TimeTask {
    private static Logger logger = LoggerFactory.getLogger(TimeTask.class);

    @Autowired
    public SimpMessagingTemplate template;
    @Autowired
    RestFulService restFulService;
    @Scheduled(cron = "0/20 * * * * ?")
    public void test(){
        System.err.println("*********   定时任务执行   **************");
//        CopyOnWriteArraySet<WebSocketServer> webSocketSet =
//                WebSocketServer.getWebSocketSet();
//        int i = 0 ;
//        webSocketSet.forEach(c->{
//            try {
//                c.sendMessage("  定时发送  " + new Date().toLocaleString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        String message=restFulService.getMessage();
        template.convertAndSend("/topic/getResponse", "我是服务器主动推送的定时消息"+message+"|||"+new Date().toLocaleString());
        System.err.println("/n 定时任务完成.......");
        Timer timer = new Timer();
        timer.schedule(new AlarmTask("闹钟"),1000,2000);
    }
    static class AlarmTask extends TimerTask {
        String name;
        public AlarmTask(String name) {
            this.name=name;
        }
        public void run() {
            logger.info(new Date()+name +" 嘀。。。");
            try {
                Thread.sleep(10_000); //模拟闹钟执行时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
