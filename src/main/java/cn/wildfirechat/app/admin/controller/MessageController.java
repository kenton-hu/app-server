package cn.wildfirechat.app.admin.controller;

import cn.wildfirechat.app.admin.dto.req.MessageListReqDTO;
import cn.wildfirechat.app.admin.dto.req.RecallMessageReqDTO;
import cn.wildfirechat.app.admin.dto.req.SendMessageReqDTO;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TMessageService;
import org.simpleframework.xml.core.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/message")
@Validate
public class MessageController {
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private TMessageService tMessageService;

    /**
     * 撤回
     *
     * @param reqDTO 请求参数
     * @return
     */
    @PostMapping(value = "/recall")
    public Result<?> recall(@RequestBody RecallMessageReqDTO reqDTO) {
        return tMessageService.recall(reqDTO);
    }

    /**
     * 获取列表
     * @param reqDTO  请求参数
     * @return 列表
     */
    @CrossOrigin
    @PostMapping(value = "/list")
    public Result<?> messageList(@Valid @RequestBody MessageListReqDTO reqDTO) {
        return tMessageService.messageList(reqDTO);
    }

    /**
     * 群发消息
     *
     * @param reqDTO 请求参数
     * @return
     */
    @PostMapping(value = "/multicast")
    public Result<?> multicast(@RequestBody SendMessageReqDTO reqDTO) {
        return tMessageService.multicast(reqDTO);
    }

    /**
     * 广播消息
     *
     * @param reqDTO 请求参数
     * @return
     */
    @PostMapping(value = "/broadcast")
    public Result<?> broadcast(@RequestBody SendMessageReqDTO reqDTO) {
        return tMessageService.broadcast(reqDTO);
    }
}
