package info.luckydoyg.asyncCall.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * TaskQueue
 *
 * @author eric
 * @since 2019/11/28
 */
@Component
@Slf4j
public class TaskQueue {

    private static final int QUEUE_LENGTH = 10;

    // 任务队列，阻塞队列，底层使用加锁同步
    private BlockingQueue<Task> queue = new LinkedBlockingDeque<>(QUEUE_LENGTH);
    // 可使用 ConcurrentLinkedQueue，无界非阻塞队列，高效处理并发，但可能会出现OOM

    private int taskId = 0;


    /**
     * 加入任务
     *
     * @param deferredResult
     */
    public void put(DeferredResult<ResponseMsg<String>> deferredResult) {

        taskId++;

        log.info("任务加入队列，id为：{}", taskId);

        queue.offer(new Task(taskId, deferredResult));

    }

    /**
     * 获取任务
     *
     * @return Task
     * @throws InterruptedException
     */
    public Task take() throws InterruptedException {

        Task task = queue.poll();

        log.info("获得任务:{}", task);

        return task;
    }
}
