package cn.wildfirechat.app.admin.service.impl;

import cn.wildfirechat.app.admin.dto.req.RecallMessageReqDTO;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TMessageService;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.sdk.MessageAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TMessageServiceImpl implements TMessageService {
    private static final Logger LOG = LoggerFactory.getLogger(TMessageServiceImpl.class);

    @Override
    public Result<?> recall(RecallMessageReqDTO reqDTO) {
        LOG.info("recall: {}", reqDTO);
        IMResult<String> recallIMResult = new IMResult<>();
        try {
            recallIMResult = MessageAdmin.recallMessage("admin", reqDTO.getMessageId());
            if (recallIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(recallIMResult.getResult(), reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(recallIMResult.getCode()), recallIMResult.getMsg(), reqDTO.getSessionId());
    }
}
