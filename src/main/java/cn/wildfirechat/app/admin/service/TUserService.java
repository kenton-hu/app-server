package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.UpdatePwdReqDTO;
import cn.wildfirechat.app.admin.result.Result;

/**
 * t_user service
 */
public interface TUserService {
    /**
     * 更新密码
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     */
    Result<?> updatePwd(UpdatePwdReqDTO reqDTO);
}
