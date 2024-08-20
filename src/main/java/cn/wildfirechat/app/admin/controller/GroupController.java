package cn.wildfirechat.app.admin.controller;

import cn.wildfirechat.app.admin.dto.req.*;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TGroupService;
import org.simpleframework.xml.core.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/group")
@Validate
public class GroupController {
    private static final Logger LOG = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private TGroupService tGroupService;

    /**
     * 获取群组列表
     * @param reqDTO  请求参数
     * @return 群组列表
     */
    @CrossOrigin
    @PostMapping(value = "/list")
    public Result<?> userList(@Valid @RequestBody GroupListReqDTO reqDTO) {
        return tGroupService.getGroupList(reqDTO);
    }

    /**
     * 创建群组
     *
     * @param reqDTO 用户信息
     * @return
     */
    @PostMapping(value = "/create")
    public Result<?> createUser(@RequestBody AddGroupReqDTO reqDTO) {
        return tGroupService.createGroup(reqDTO);
    }

    /**
     * 获取群成员
     * @param reqDTO  请求参数
     * @return 群成员列表
     */
    @CrossOrigin
    @PostMapping(value = "/users")
    public Result<?> groupUsers(@Valid @RequestBody GroupUserListReqDTO reqDTO) {
        return tGroupService.groupUsers(reqDTO);
    }
}
