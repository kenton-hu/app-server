package cn.wildfirechat.app.admin.controller;

import cn.wildfirechat.app.admin.dto.req.UpdatePwdReqDTO;
import cn.wildfirechat.app.admin.service.TUserService;
import org.simpleframework.xml.core.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController("/admin")
@Validate
public class AdminController {
    public static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private TUserService tUserService;

    /**
     * 更新密码
     */
    @PostMapping("/updatePwd")
    public Object updatePwd(@RequestBody UpdatePwdReqDTO reqDTO) {
        // 更新密码
        return tUserService.updatePwd(reqDTO);
    }
}
