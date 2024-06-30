package cn.wildfirechat.app.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description: 用户名token
 * @author: Dragon
 * @date: 2024/6/4 12:34
 */
public class UsernameToken extends UsernamePasswordToken {

    public UsernameToken(String username) {
        this.username = username;
    }

    private String username;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Object getCredentials() {
        //构建假的密码，也可以是别的，和后面SimpleAuthenticationInfo中加密的假密码对应一致即可
        return "123456";
    }
}
