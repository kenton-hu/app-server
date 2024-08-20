package cn.wildfirechat.app;

import cn.wildfirechat.app.admin.dto.req.*;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TUserService;
import cn.wildfirechat.app.pojo.SendMessageRequest;
import cn.wildfirechat.app.pojo.UserPasswordLoginRequest;
import cn.wildfirechat.app.service.user.UserService;
import cn.wildfirechat.pojos.InputOutputUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private TUserService tUserService;


    @GetMapping()
    public Object health() {
        return "Ok";
    }


    /**
     * 获取用户列表
     *
     * @param count  数量
     * @param offset 偏移
     * @return 用户列表
     */
    @CrossOrigin
    @GetMapping(value = "/list/{count}/{offset}")
    public Object loginWithMobileCode(@PathVariable("count") int count, @PathVariable("offset") int offset) {
        return userService.getUserList(count, offset);
    }

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

//    /**
//     * 创建用户
//     *
//     * @param userInfo 用户信息
//     * @return
//     */
//    @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
//    public Object loginWithMobileCode(@RequestBody InputOutputUserInfo userInfo) {
//        return userService.createUser(userInfo);
//    }

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
     * 搜索外部系统Abc用户
     *
     * @param username 用户名
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/abc/search")
    public Object searchAbcUser(@Validated @NotBlank(message = "用户名不能为空")
                                    @RequestParam("username") String username) {
        return userService.searchAbcUser(username);
    }

    /**
     * 根据用户信息获取token
     * @param userInfo 用户名
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/token/get")
    public Object getToken(@RequestBody UserPasswordLoginRequest request, HttpServletResponse response) {
        return userService.getTokenByUserInfo(request, response);
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
}