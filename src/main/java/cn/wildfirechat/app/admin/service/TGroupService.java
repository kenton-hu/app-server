package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.*;
import cn.wildfirechat.app.admin.result.Result;

import javax.validation.Valid;

/**
 * t_group service
 */
public interface TGroupService {

    /**
     * 获取群组列表
     * @param reqDTO
     * @return
     */
    Result<?> getGroupList(GroupListReqDTO reqDTO);

    /**
     * 创建群组
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> createGroup(AddGroupReqDTO reqDTO);

    /**
     * 获取群成员列表
     * @param reqDTO 请求参数
     * @return 群成员列表
     */
    Result<?> groupUsers(GroupUserListReqDTO reqDTO);

    /**
     * 设为群主
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> transferGroup(TransferGroupReqDTO reqDTO);

    /**
     * 设置/取消群管理员
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> changeUserType(ChangeGroupUserTypeReqDTO reqDTO);

    /**
     * 删除群成员
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> delUser(DelGroupUserReqDTO reqDTO);
}
