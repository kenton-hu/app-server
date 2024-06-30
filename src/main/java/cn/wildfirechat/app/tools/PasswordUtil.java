package cn.wildfirechat.app.tools;

import org.apache.shiro.crypto.hash.Sha1Hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @Description: 密码工具类
 * @author: Dragon
 * @date: 2024/6/4 13:25
 */
public class PasswordUtil {

    /**
     * 加密密码sha1
     * @param password 密码
     * @param salt 盐
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String ecnryptSha1Password(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(Sha1Hash.ALGORITHM_NAME);
        digest.reset();
        digest.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] hashed = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashed);
    }
}
