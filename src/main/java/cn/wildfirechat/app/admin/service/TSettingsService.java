package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.SettingsInfoReqDTO;
import cn.wildfirechat.app.admin.result.Result;

/**
 * t_user service
 */
public interface TSettingsService {

    /**
     * 获取信息
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> info(SettingsInfoReqDTO reqDTO);

    /**
     * 更新信息
     * @param reqDTO 请求参数
     * @return
     */
    Result<?> update(SettingsInfoReqDTO reqDTO);
}
