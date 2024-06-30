package cn.wildfirechat.app;

import cn.wildfirechat.app.pojo.UserPasswordLoginRequest;
import cn.wildfirechat.app.service.user.UserService;
import cn.wildfirechat.pojos.InputOutputUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


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
     * 创建用户
     *
     * @param userInfo 用户信息
     * @return
     */
    @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
    public Object loginWithMobileCode(@RequestBody InputOutputUserInfo userInfo) {
        return userService.createUser(userInfo);
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
}