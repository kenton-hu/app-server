package cn.wildfirechat.app.admin.controller;

import cn.wildfirechat.app.admin.dto.req.*;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TUserService;
import org.simpleframework.xml.core.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin")
@Validate
public class AdminController {
    public static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private TUserService tUserService;

    /**
     * 登录
     * @param reqDTO
     * @return
     */
    @PostMapping("/login")
    public Result<?> login(LoginReqDTO reqDTO) {
        return tUserService.login(reqDTO);
    }

    /**
     * 获取用户信息
     */
    @PostMapping("/getUserInfo")
    public Result<?> getUserInfo(@RequestBody UserInfoReqDTO reqDTO) {
        return tUserService.getUserInfo(reqDTO);
    }

    /**
     * 更新密码
     */
    @PostMapping("/updatePwd")
    public Result<?> updatePwd(@RequestBody UpdatePwdReqDTO reqDTO) {
        // 更新密码
        return tUserService.updatePwd(reqDTO);
    }

    /**
     * 更新密码
     */
    @PostMapping("/updateIcon")
    public Result<?> updateIcon(@RequestBody UpdateIconReqDTO reqDTO) {
        return tUserService.updateIcon(reqDTO);
    }

    /**
     * 更新密码
     */
    @PostMapping("/updatePhone")
    public Result<?> updatePhone(@RequestBody UpdatePhoneReqDTO reqDTO) {
        return tUserService.updatePhone(reqDTO);
    }
}
