package cn.wildfirechat.app.admin.service.impl;

import cn.wildfirechat.app.admin.dto.req.SettingsInfoReqDTO;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TSettingsService;
import cn.wildfirechat.app.wfchat.jpa.TSettings;
import cn.wildfirechat.app.wfchat.jpa.TSettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TSettingsServiceImpl implements TSettingsService {

    private static final Logger LOG = LoggerFactory.getLogger(TSettingsServiceImpl.class);
    @Autowired
    private TSettingsRepository tSettingsRepository;

    @Override
    public Result<?> info(SettingsInfoReqDTO reqDTO) {
        Optional<TSettings> optional = tSettingsRepository.findById(1);
        if (!optional.isPresent()) {
            return new Result<>().error("settings:not:exist", "配置不存在", reqDTO.getSessionId());
        }
        return new Result<TSettings>().success(optional.get(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> update(SettingsInfoReqDTO reqDTO) {
        LOG.info("update req data: {}", reqDTO);
        Optional<TSettings> optional = tSettingsRepository.findById(1);
        if (!optional.isPresent()) {
            return new Result<>().error("settings:not:exist", "配置不存在", reqDTO.getSessionId());
        }
        TSettings tSettings = optional.get();
        tSettings.setValue(reqDTO.getValue());
        tSettingsRepository.save(tSettings);
        return new Result<TSettings>().success(tSettings, reqDTO.getSessionId());
    }
}
