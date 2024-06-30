package cn.wildfirechat.app.shiro;


import cn.wildfirechat.app.tools.PasswordUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.Sha1CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@Service
public class UsernameRealm extends AuthorizingRealm {
    /**
     * 盐
     */
    public static final String SALT = "123456";

    public static final String DEFAULT_ENCRYPT_PASSWORD;

    static {
        try {
            DEFAULT_ENCRYPT_PASSWORD = PasswordUtil.ecnryptSha1Password("123456", SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void initMatcher() {
        Sha1CredentialsMatcher matcher = new Sha1CredentialsMatcher();
        matcher.setStoredCredentialsHexEncoded(false);
        setCredentialsMatcher(matcher);
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        Set<String> stringSet = new HashSet<>();
//        stringSet.add("user:show");
//        stringSet.add("user:admin");
//        info.setStringPermissions(stringSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof UsernamePasswordToken) {
            return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), DEFAULT_ENCRYPT_PASSWORD,
                    ByteSource.Util.bytes(SALT.getBytes(StandardCharsets.UTF_8)), getName());
        }

        throw new AuthenticationException("没有密码");
    }
}