package cn.wildfirechat.app.admin.controller;

import cn.wildfirechat.app.admin.dto.req.SettingsInfoReqDTO;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TSettingsService;
import org.simpleframework.xml.core.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
@Validate
public class SettingsController {
    public static final Logger LOG = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    private TSettingsService tSettingsService;

   /**
     * 获取信息
     */
    @PostMapping("/info")
    public Result<?> updatePwd(@RequestBody SettingsInfoReqDTO reqDTO) {
        return tSettingsService.info(reqDTO);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody SettingsInfoReqDTO reqDTO) {
        return tSettingsService.update(reqDTO);
    }
}
