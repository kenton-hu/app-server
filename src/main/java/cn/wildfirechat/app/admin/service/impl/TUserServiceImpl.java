package cn.wildfirechat.app.admin.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.wildfirechat.app.admin.dto.req.UpdateIconReqDTO;
import cn.wildfirechat.app.admin.dto.req.UpdatePwdReqDTO;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TUserService;
import cn.wildfirechat.app.wfchat.jpa.TUser;
import cn.wildfirechat.app.wfchat.jpa.TUserRepository;
import com.qcloud.cos.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class TUserServiceImpl implements TUserService {

    private static final Logger LOG = LoggerFactory.getLogger(TUserServiceImpl.class);
    @Autowired
    private TUserRepository tUserRepository;
    @Override
    public Result<?> updatePwd(UpdatePwdReqDTO reqDTO) {
        LOG.info("reqDTO: {}", reqDTO);
        Optional<TUser> optional = tUserRepository.findById(2);
        if (!optional.isPresent()) {
            // 用户不存在，返回错误信息
            return new Result<>().error("user:not:exist", "用户不存在", reqDTO.getSessionId());
        }
        // 验证原密码是否正确
        String oldPassword = DigestUtil.md5Hex(reqDTO.getOldPwd());
        TUser tUser = optional.get();
        LOG.info("tuser: {}", tUser);
        // 验证原密码是否正确
        if (!oldPassword.equals(tUser.getPasswordMD5())) {
            // 原密码不正确，返回错误信息
            return new Result<>().error("user:oldpwd:error", "用户原密码错误", reqDTO.getSessionId());
        }
        tUser.setPasswordMD5(DigestUtil.md5Hex(reqDTO.getNewPwd()));
        tUserRepository.save(tUser);
        return new Result<>().success(null, null);
    }

    @Override
    public Result<?> updateIcon(UpdateIconReqDTO reqDTO) {
        LOG.info("reqDTO: {}", reqDTO);
        Optional<TUser> optional = tUserRepository.findById(2);
        if (!optional.isPresent()) {
            // 用户不存在，返回错误信息
            return new Result<>().error("user:not:exist", "用户不存在", reqDTO.getSessionId());
        }
        TUser tUser = optional.get();
        LOG.info("tuser: {}", tUser);
        tUser.setPortrait(reqDTO.getIcon());
        tUserRepository.save(tUser);
        return new Result<>().success(tUser, null);
    }
}
