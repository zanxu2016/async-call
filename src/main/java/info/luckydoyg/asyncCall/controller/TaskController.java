package info.luckydoyg.asyncCall.controller;

import info.luckydoyg.asyncCall.model.ResponseMsg;
import info.luckydoyg.asyncCall.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * TaskController
 *
 * @author eric
 * @since 2019/11/28
 */
@RequestMapping("/task")
@RestController
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 请求非阻塞
     *
     * 开异步线程处理业务逻辑
     * 主线程直接返回，且被释放，继续去处理其他请求。
     *
     * @return Callable
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Callable<ResponseMsg<String>> getResult() {

        log.info("接收请求，开始处理...");

        Callable<ResponseMsg<String>> result = (() -> taskService.getResult());

        log.info("接收任务线程完成并退出");

        return result;
    }
}
