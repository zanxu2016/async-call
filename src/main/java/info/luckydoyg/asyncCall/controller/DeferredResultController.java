package info.luckydoyg.asyncCall.controller;

import info.luckydoyg.asyncCall.model.ResponseMsg;
import info.luckydoyg.asyncCall.model.TaskQueue;
import info.luckydoyg.asyncCall.model.TaskSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Set;

/**
 * DeferredResultController
 *
 * @author eric
 * @since 2019/11/28
 */
@RequestMapping("/deferred")
@RestController
@Slf4j
public class DeferredResultController {

    //超时结果
    private static final ResponseMsg<String> TIME_OUT_RESULT = new ResponseMsg<>(-1, "超时", "time out");

    //超时时间
    private static final long TIME_OUT = 10000L;

    @Autowired
    private TaskQueue taskQueue;

    /**
     * 接收请求，并将其放入队列中，主线程直接返回并被释放。
     * 由另一个线程处理请求，并返回结果
     *
     * @return DeferredResult
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public DeferredResult<ResponseMsg<String>> getResult() {

        log.info("接收请求，开始处理...");

        //建立DeferredResult对象，设置超时时间，以及超时后返回超时结果
        DeferredResult<ResponseMsg<String>> result = new DeferredResult<>(TIME_OUT, TIME_OUT_RESULT);
        // 设置超时后的处理
        result.onTimeout(() -> log.info("调用超时"));
        // 设置完成后的处理
        result.onCompletion(() -> log.info("调用完成"));
        //并发，加锁
        synchronized (taskQueue) {
            taskQueue.put(result);
        }
        log.info("接收任务线程完成并退出");
        return result;
    }

    @Autowired
    private TaskSet taskSet;

    /**
     * 将请求放入集合中，并设置超时和完成事件
     *
     * @return
     */
    @RequestMapping(value = "/get2", method = RequestMethod.GET)
    public DeferredResult<ResponseMsg<String>> getResult2() {

        log.info("接收请求，开始处理...");
        //建立DeferredResult对象，设置超时时间，以及超时返回超时结果
        DeferredResult<ResponseMsg<String>> result = new DeferredResult<>(TIME_OUT, TIME_OUT_RESULT);

        // 设置超时后的处理
        result.onTimeout(() -> {
            log.info("调用超时，移除任务，此时队列长度为{}", taskSet.getSet().size());
            synchronized (taskSet.getSet()) {
                taskSet.getSet().remove(result);
            }
        });

        // 设置完成后的处理
        result.onCompletion(() -> {
            log.info("调用完成，移除任务，此时队列长度为{}", taskSet.getSet().size());
            synchronized (taskSet.getSet()) {
                taskSet.getSet().remove(result);
            }
        });

        //并发，加锁
        synchronized (taskSet.getSet()) {
            taskSet.getSet().add(result);
        }
        log.info("加入任务集合，集合大小为:{}", taskSet.getSet().size());
        log.info("接收任务线程完成并退出");
        return result;
    }

    /**
     * 传入请求参数，将其设置为请求集合中请求的结果，并返回
     *
     * @param result 请求的结果
     * @return String
     */
    @GetMapping(value = "/set/{result}")
    public String setResult(@PathVariable("result") String result) {
        ResponseMsg<String> res = new ResponseMsg<>(0, "success", result);
        log.info("结果处理开始，得到输入结果为:{}", res);
        Set<DeferredResult<ResponseMsg<String>>> set = taskSet.getSet();
        synchronized (set) {
            set.forEach((deferredResult) -> deferredResult.setResult(res));
        }
        return "Successfully set result: " + result;
    }
}
