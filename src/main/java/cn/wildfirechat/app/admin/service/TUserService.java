package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.*;
import cn.wildfirechat.app.admin.result.Result;

import javax.validation.Valid;

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

    /**
     * 获取用户信息
     * @param reqDTO
     * @return
     */
    Result<?> getUserInfo(UserInfoReqDTO reqDTO);

    /**
     * 获取用户列表
     * @param reqDTO
     * @return
     */
    Result<?> getUserList(UserListReqDTO reqDTO);

    /**
     * 发送消息
     * @param reqDTO
     * @return
     */
    Result<?> sendMessage(UserSendMsgReqDTO reqDTO);

    /**
     * 登录
     * @param reqDTO
     * @return
     */
    Result<?> login(LoginReqDTO reqDTO);

    /**
     * 创建用户
     * @param reqDTO
     * @return
     */
    Result<?> createUser(AddUserReqDTO reqDTO);

    Result<?> updateUserStatus(BlockUserReqDTO reqDTO);

    /**
     * 销毁用户
     * @param reqDTO 用户信息
     * @return
     */
    Result<?> destroyUser(DestroyUserReqDTO reqDTO);

    Result<?> getBlockList(@Valid BlockUserListReqDTO reqDTO);
}
