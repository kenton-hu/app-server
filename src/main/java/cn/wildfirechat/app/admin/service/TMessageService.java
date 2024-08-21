package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.MessageListReqDTO;
import cn.wildfirechat.app.admin.dto.req.RecallMessageReqDTO;
import cn.wildfirechat.app.admin.dto.req.SendMessageReqDTO;
import cn.wildfirechat.app.admin.result.Result;

import javax.validation.Valid;

/**
 * t_message service
 */
public interface TMessageService {

    /**
     * 撤回消息
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> recall(RecallMessageReqDTO reqDTO);

    /**
     * 获取消息列表
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> messageList(@Valid MessageListReqDTO reqDTO);

    /**
     * 群发消息
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> multicast(SendMessageReqDTO reqDTO);

    /**
     * 广播消息
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> broadcast(SendMessageReqDTO reqDTO);
}
