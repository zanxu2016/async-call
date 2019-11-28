package info.luckydoyg.asyncCall.service;

import info.luckydoyg.asyncCall.model.ResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TaskService
 *
 * @author eric
 * @since 2019/11/28
 */
@Service
@Slf4j
public class TaskService {

    public ResponseMsg<String> getResult() {

        log.info("任务开始执行，持续等待中...");

        try {
            Thread.sleep(30000L);// 模拟处理业务逻辑
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("任务处理完成");
        return new ResponseMsg<>(0, "操作成功", "success");
    }
}
