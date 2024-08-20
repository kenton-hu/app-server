package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.RecallMessageReqDTO;
import cn.wildfirechat.app.admin.result.Result;

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
}
