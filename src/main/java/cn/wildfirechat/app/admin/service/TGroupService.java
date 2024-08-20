package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.GroupListReqDTO;
import cn.wildfirechat.app.admin.result.Result;

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
}
