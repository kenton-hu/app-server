package cn.wildfirechat.app.admin.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TUserService;
import cn.wildfirechat.app.wfchat.jpa.TUser;
import cn.wildfirechat.app.wfchat.jpa.TUserRepository;
import com.qcloud.cos.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class TUserServiceImpl implements TUserService {

    @Autowired
    private TUserRepository tUserRepository;
    @Override
    public Result<?> updatePwd(String oldPwd, String newPwd) {
        Optional<TUser> optional = tUserRepository.findById(33234);
        if (!optional.isPresent()) {
            // 用户不存在，返回错误信息
            return new Result<>().error("user:not:exist", "用户不存在", null);
        }
        // 验证原密码是否正确
        String oldPassword = DigestUtil.md5Hex(oldPwd);
        TUser tUser = optional.get();
        // 验证原密码是否正确
        if (!oldPassword.equals(tUser.getPasswordMD5())) {
            // 原密码不正确，返回错误信息
            return new Result<>().error("user:oldpwd:error", "用户原密码错误", null);
        }
        tUser.setPasswordMD5(DigestUtil.md5Hex(newPwd));
        tUserRepository.save(tUser);
        return new Result<>().success(null, null);
    }
}
