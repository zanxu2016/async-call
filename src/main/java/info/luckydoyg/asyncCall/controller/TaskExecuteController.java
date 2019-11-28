package info.luckydoyg.asyncCall.controller;

import info.luckydoyg.asyncCall.model.ResponseMsg;
import info.luckydoyg.asyncCall.model.TaskSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Set;

/**
 * TaskExecuteController
 *
 * @author eric
 * @since 2019/11/28
 */
@RequestMapping("deferred")
@RestController
@Slf4j
public class TaskExecuteController {

    @Autowired
    private TaskSet taskSet;
}
