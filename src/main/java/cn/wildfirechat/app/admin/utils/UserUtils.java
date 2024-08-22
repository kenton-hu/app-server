package cn.wildfirechat.app.admin.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.wildfirechat.app.wfchat.jpa.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 用户工具类
 * @author: Dragon
 * @date: 2024/8/22 22:08
 */
@Component
public class UserUtils {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据sessionId获取用户信息
     * @param sessionId 会话Id
     * @return 用户信息
     */
    public TUser getUserBySessionId(String sessionId) {
        if (StrUtil.isBlank(sessionId)) {
            return null;
        }
        return JSONUtil.toBean(redisUtil.get(sessionId).toString(), TUser.class);
    }
}
