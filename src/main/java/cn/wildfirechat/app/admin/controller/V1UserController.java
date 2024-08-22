package cn.wildfirechat.app.admin.controller;

import cn.wildfirechat.app.admin.dto.req.*;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TUserService;
import cn.wildfirechat.app.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class V1UserController {
    private static final Logger LOG = LoggerFactory.getLogger(V1UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private TUserService tUserService;

    /**
     * 获取用户列表
     * @param reqDTO  请求参数
     * @return 用户列表
     */
    @CrossOrigin
    @PostMapping(value = "/list")
    public Result<?> userList(@Valid @RequestBody UserListReqDTO reqDTO) {
        return tUserService.getUserList(reqDTO);
    }

    /**
     * 发送消息
     */
    @PostMapping(value = "/sendMsg")
    public Object sendMessage(@RequestBody UserSendMsgReqDTO reqDTO) {
        return tUserService.sendMessage(reqDTO);
    }

    /**
     * 创建用户
     *
     * @param reqDTO 用户信息
     * @return
     */
    @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
    public Result createUser(@RequestBody AddUserReqDTO reqDTO) {
        return tUserService.createUser(reqDTO);
    }


    /**
     * 更新用户状态
     * @param reqDTO 用户信息
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/updateUserStatus")
    public Result<?> updateUserStatus(@RequestBody BlockUserReqDTO reqDTO) {
        return tUserService.updateUserStatus(reqDTO);
    }

    /**
     * 销毁用户
     * @param reqDTO 用户信息
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/destroyUser")
    public Result<?> destroyUser(@RequestBody DestroyUserReqDTO reqDTO) {
        return tUserService.destroyUser(reqDTO);
    }

    /**
     * 获取封禁用户列表
     * @param reqDTO  请求参数
     * @return 封禁用户列表
     */
    @CrossOrigin
    @PostMapping(value = "/getBlockList")
    public Result<?> getBlockList(@Valid @RequestBody BlockUserListReqDTO reqDTO) {
        return tUserService.getBlockList(reqDTO);
    }

    /**
     * 获取敏感词列表
     * @param reqDTO  请求参数
     * @return 敏感词列表
     */
    @CrossOrigin
    @PostMapping(value = "/getSensetive")
    public Result<?> getSensetive(@Valid @RequestBody BasicReqDTO reqDTO) {
        return tUserService.getSensetive(reqDTO);
    }

    /**
     * 添加敏感词
     * @param reqDTO  请求参数
     * @return 结果
     */
    @CrossOrigin
    @PostMapping(value = "/addSensetive")
    public Result<?> addSensetive(@Valid @RequestBody SensetiveReqDTO reqDTO) {
        return tUserService.addSensetive(reqDTO);
    }

    /**
     * 删除敏感词
     * @param reqDTO  请求参数
     * @return 结果
     */
    @CrossOrigin
    @PostMapping(value = "/delSensetive")
    public Result<?> delSensetive(@Valid @RequestBody SensetiveReqDTO reqDTO) {
        return tUserService.delSensetive(reqDTO);
    }

    /**
     * 获取敏感词命中列表
     * @param reqDTO  请求参数
     * @return 敏感词列表
     */
    @CrossOrigin
    @PostMapping(value = "/sensitiveShot")
    public Result<?> sensitiveShot(@Valid @RequestBody BasicReqDTO reqDTO) {
        return tUserService.sensitiveShot(reqDTO);
    }

    /**
     * 清空敏感词命中
     * @param reqDTO  请求参数
     * @return 敏感词列表
     */
    @CrossOrigin
    @PostMapping(value = "/delSensitiveShot")
    public Result<?> delSensitiveShot(@Valid @RequestBody ClearSensitiveMessageReqDTO reqDTO) {
        return tUserService.delSensitiveShot(reqDTO);
    }

   /**
     * 获取所有用户
     * @param reqDTO  请求参数
     * @return 用户列表
     */
    @CrossOrigin
    @PostMapping(value = "/alls")
    public Result<?> alls(@Valid @RequestBody BasicReqDTO reqDTO) {
        return tUserService.alls(reqDTO);
    }
}