package info.luckydoyg.asyncCall.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashSet;
import java.util.Set;

/**
 * TaskSet
 *
 * @author eric
 * @since 2019/11/28
 */
@Component
@Data
public class TaskSet {
    // DeferredResult 集合类
    private Set<DeferredResult<ResponseMsg<String>>> set = new HashSet<>();
    // 可考虑使用 ConcurrentHashMap 实现高效并发
}
