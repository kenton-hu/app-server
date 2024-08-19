package cn.wildfirechat.app.service.user;


import cn.wildfirechat.app.RestResult;
import cn.wildfirechat.app.pojo.UserPasswordLoginRequest;
import cn.wildfirechat.pojos.InputOutputUserInfo;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    /**
     * 获取用户列表
     * @return
     */
    RestResult getUserList(int count, int offset);

    /**
     * 创建用户
     * @return
     */
    RestResult createUser(InputOutputUserInfo userInfo);

    /**
     * 搜索abc系统用户
     * @param username 用户名
     * @return
     */
    RestResult searchAbcUser(String username);

    /**
     * 获取用户信息获取token
     * @param userInfo 用户信息
     * @return
     */
    RestResult getTokenByUserInfo(UserPasswordLoginRequest request,  HttpServletResponse resp);
}
