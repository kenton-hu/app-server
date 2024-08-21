package cn.wildfirechat.app.admin.controller;

import cn.wildfirechat.app.admin.dto.req.*;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TGroupService;
import org.simpleframework.xml.core.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/group")
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
     * @param reqDTO 群组信息
     * @return
     */
    @PostMapping(value = "/create")
    public Result<?> createGroup(@RequestBody AddGroupReqDTO reqDTO) {
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

    /**
     * 设为群主
     *
     * @param reqDTO 请求参数
     * @return
     */
    @PostMapping(value = "/transferGroup")
    public Result<?> transferGroup(@RequestBody TransferGroupReqDTO reqDTO) {
        return tGroupService.transferGroup(reqDTO);
    }

    /**
     * 设置/取消群管理员
     *
     * @param reqDTO 请求参数
     * @return
     */
    @PostMapping(value = "/changeUserType")
    public Result<?> changeUserType(@RequestBody ChangeGroupUserTypeReqDTO reqDTO) {
        return tGroupService.changeUserType(reqDTO);
    }

    /**
     * 删除群成员
     *
     * @param reqDTO 请求参数
     * @return
     */
    @PostMapping(value = "/delUser")
    public Result<?> delUser(@RequestBody GroupUserReqDTO reqDTO) {
        return tGroupService.delUser(reqDTO);
    }

    /**
     * 添加群成员
     *
     * @param reqDTO 请求参数
     * @return
     */
    @PostMapping(value = "/addUser")
    public Result<?> addUser(@RequestBody GroupUserReqDTO reqDTO) {
        return tGroupService.addUser(reqDTO);
    }

    /**
     * 修改群组信息
     *
     * @param reqDTO 请求参数
     * @return
     */
    @PostMapping(value = "/modifyGroupInfo")
    public Result<?> modifyGroupInfo(@RequestBody ModifyGroupReqDTO reqDTO) {
        return tGroupService.modifyGroupInfo(reqDTO);
    }

    /**
     * 解散群组
     *
     * @param reqDTO 请求参数
     * @return
     */
    @PostMapping(value = "/dismissGroup")
    public Result<?> dismissGroup(@RequestBody DismissGroupReqDTO reqDTO) {
        return tGroupService.dismissGroup(reqDTO);
    }
}
