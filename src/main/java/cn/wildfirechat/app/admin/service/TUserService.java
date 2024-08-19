package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.UpdateIconReqDTO;
import cn.wildfirechat.app.admin.dto.req.UpdatePhoneReqDTO;
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

    /**
     * 更新头像
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> updateIcon(UpdateIconReqDTO reqDTO);

    /**
     * 修改手机号
     * @param reqDTO
     * @return
     */
    Result<?> updatePhone(UpdatePhoneReqDTO reqDTO);
}
