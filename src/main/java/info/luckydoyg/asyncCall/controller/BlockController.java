package info.luckydoyg.asyncCall.controller;

import info.luckydoyg.asyncCall.model.ResponseMsg;
import info.luckydoyg.asyncCall.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BlockController
 *
 * @author eric
 * @since 2019/11/28
 */
@RequestMapping("/block")
@RestController
@Slf4j
public class BlockController {

    @Autowired
    private TaskService taskService;

    /**
     * 阻塞请求
     * 主线程处理完毕后返回结果，释放主线程。
     *
     * @return ResponseMsg
     */
    @GetMapping(value = "/get")
    public ResponseMsg<String> getResult() {

        log.info("接收请求，开始处理...");
        ResponseMsg<String> result = taskService.getResult();
        log.info("接收任务线程完成并退出");

        return result;

    }
}
