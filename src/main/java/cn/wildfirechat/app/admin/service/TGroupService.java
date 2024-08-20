package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.AddGroupReqDTO;
import cn.wildfirechat.app.admin.dto.req.GroupListReqDTO;
import cn.wildfirechat.app.admin.dto.req.GroupUserListReqDTO;
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
}
