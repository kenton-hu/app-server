package cn.wildfirechat.app.service.user.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.wildfirechat.app.IMConfig;
import cn.wildfirechat.app.RestResult;
import cn.wildfirechat.app.jpa.AnnouncementRepository;
import cn.wildfirechat.app.jpa.FavoriteRepository;
import cn.wildfirechat.app.jpa.UserPassword;
import cn.wildfirechat.app.jpa.UserPasswordRepository;
import cn.wildfirechat.app.pojo.InputOutputUserInfoExt;
import cn.wildfirechat.app.pojo.LoginResponse;
import cn.wildfirechat.app.pojo.UserPasswordLoginRequest;
import cn.wildfirechat.app.service.user.UserService;
import cn.wildfirechat.app.shiro.AuthDataSource;
import cn.wildfirechat.app.shiro.UsernameToken;
import cn.wildfirechat.app.sms.SmsService;
import cn.wildfirechat.app.tools.ProtobufUtil;
import cn.wildfirechat.app.tools.RateLimiter;
import cn.wildfirechat.app.tools.ShortUUIDGenerator;
import cn.wildfirechat.app.tools.Utils;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.*;
import cn.wildfirechat.proto.ProtoConstants;
import cn.wildfirechat.proto.WFCMessage;
import cn.wildfirechat.sdk.*;
import cn.wildfirechat.sdk.model.IMResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static cn.wildfirechat.app.RestResult.RestCode.*;

@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    private IMConfig mIMConfig;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserPasswordRepository userPasswordRepository;

    @Value("${sms.super_code}")
    private String superCode;

    @Value("${logs.user_logs_path}")
    private String userLogPath;

    @Value("${im.admin_url}")
    private String adminUrl;

    @Value("${abc_url}")
    private String abcUrl;

    @Autowired
    private ShortUUIDGenerator userNameGenerator;

    @Autowired
    private AuthDataSource authDataSource;

    private RateLimiter rateLimiter;

    @Value("${wfc.compat_pc_quick_login}")
    protected boolean compatPcQuickLogin;

    @Value("${media.server.media_type}")
    private int ossType;

    @Value("${media.server_url}")
    private String ossUrl;

    @Value("${media.access_key}")
    private String ossAccessKey;

    @Value("${media.secret_key}")
    private String ossSecretKey;

    @Value("${media.bucket_general_name}")
    private String ossGeneralBucket;
    @Value("${media.bucket_general_domain}")
    private String ossGeneralBucketDomain;

    @Value("${media.bucket_image_name}")
    private String ossImageBucket;
    @Value("${media.bucket_image_domain}")
    private String ossImageBucketDomain;

    @Value("${media.bucket_voice_name}")
    private String ossVoiceBucket;
    @Value("${media.bucket_voice_domain}")
    private String ossVoiceBucketDomain;

    @Value("${media.bucket_video_name}")
    private String ossVideoBucket;
    @Value("${media.bucket_video_domain}")
    private String ossVideoBucketDomain;


    @Value("${media.bucket_file_name}")
    private String ossFileBucket;
    @Value("${media.bucket_file_domain}")
    private String ossFileBucketDomain;

    @Value("${media.bucket_sticker_name}")
    private String ossStickerBucket;
    @Value("${media.bucket_sticker_domain}")
    private String ossStickerBucketDomain;

    @Value("${media.bucket_moments_name}")
    private String ossMomentsBucket;
    @Value("${media.bucket_moments_domain}")
    private String ossMomentsBucketDomain;

    @Value("${media.bucket_favorite_name}")
    private String ossFavoriteBucket;
    @Value("${media.bucket_favorite_domain}")
    private String ossFavoriteBucketDomain;

    @Value("${local.media.temp_storage}")
    private String ossTempPath;

    private ConcurrentHashMap<String, Boolean> supportPCQuickLoginUsers = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        AdminConfig.initAdmin(mIMConfig.admin_url, mIMConfig.admin_secret);
        rateLimiter = new RateLimiter(60, 200);
        if (StringUtils.isEmpty(mIMConfig.admin_user_id)) {
            mIMConfig.admin_user_id = "admin";
        }
    }

    private String getIp() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    private int getUserStatus(String mobile) {
        try {
            IMResult<InputOutputUserInfo> inputOutputUserInfoIMResult = UserAdmin.getUserByMobile(mobile);
            if (inputOutputUserInfoIMResult != null && inputOutputUserInfoIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                IMResult<OutputUserStatus> outputUserStatusIMResult = UserAdmin.checkUserBlockStatus(inputOutputUserInfoIMResult.getResult().getUserId());
                if (outputUserStatusIMResult != null && outputUserStatusIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    return outputUserStatusIMResult.getResult().getStatus();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getUserStatusByName(String name) {
        try {
            IMResult<InputOutputUserInfo> inputOutputUserInfoIMResult = UserAdmin.getUserByName(name);
            if (inputOutputUserInfoIMResult != null && inputOutputUserInfoIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                IMResult<OutputUserStatus> outputUserStatusIMResult = UserAdmin.checkUserBlockStatus(inputOutputUserInfoIMResult.getResult().getUserId());
                if (outputUserStatusIMResult != null && outputUserStatusIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    return outputUserStatusIMResult.getResult().getStatus();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean isUsernameAvailable(String username) {
        try {
            IMResult<InputOutputUserInfo> existUser = UserAdmin.getUserByName(username);
            if (existUser.code == ErrorCode.ERROR_CODE_NOT_EXIST.code) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public RestResult getUserList(int count, int offset) {
        try {
            IMResult<OutputGetUserList> allUsersResult = UserAdmin.getAllUsers(count, offset);
            if (allUsersResult.code == ErrorCode.ERROR_CODE_SUCCESS.code) {
                OutputGetUserList userList = allUsersResult.getResult();
                return RestResult.ok(userList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("getUserList exception", e);
            return RestResult.error(ERROR_SERVER_ERROR);
        }
        return RestResult.ok();
    }

    @Override
    public RestResult createUser(InputOutputUserInfo userInfo) {
        LOG.info("createUser userInfo {}", userInfo);
        //使用用户名查询用户信息。
        IMResult<InputOutputUserInfo> userResult = null;
        try {
            if (StringUtils.isEmpty(userInfo.getUserId())) {
                return RestResult.error(ERROR_USERNAME_NOT_EXIST);
            }
            userResult = UserAdmin.getUserByUserId(userInfo.getUserId());

            //如果用户信息不存在，创建用户
            InputOutputUserInfoExt user = new InputOutputUserInfoExt();
            if (userResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_EXIST) {
                LOG.info("User not exist, try to create");

                //获取用户名。如果用的是shortUUID生成器，是有极小概率会重复的，所以需要去检查是否已经存在相同的userName。
                //ShortUUIDGenerator内的main函数有测试代码，可以观察一下碰撞的概率，这个重复是理论上的，作者测试了几千万次次都没有产生碰撞。
                //另外由于并发的问题，也有同时生成相同的id并同时去检查的并同时通过的情况，但这种情况概率极低，可以忽略不计。
                String userName = userInfo.getName();
                if (StringUtils.isEmpty(userInfo.getName())) {
                    int tryCount = 0;
                    do {
                        tryCount++;
                        userName = userNameGenerator.getUserName(userInfo.getMobile());
                        if (tryCount > 10) {
                            return RestResult.error(ERROR_SERVER_ERROR);
                        }
                    } while (!isUsernameAvailable(userName));
                }

                user.setUserId(userInfo.getUserId());
                user.setName(userName);
                if (StringUtils.isEmpty(userInfo.getDisplayName())) {
                    String displayName = "用户" + (int) (Math.random() * 10000);
                    user.setDisplayName(displayName);
                } else {
                    user.setDisplayName(userInfo.getDisplayName());
                }
                user.setMobile(userInfo.getMobile());
                InputOutputUserInfo inputOutputUserInfo = new InputOutputUserInfo();
                BeanUtil.copyProperties(user, inputOutputUserInfo);
                IMResult<OutputCreateUser> userIdResult = UserAdmin.createUser(inputOutputUserInfo);
                if (userIdResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    user.setUserId(userIdResult.getResult().getUserId());
                    setUserDefaultPassword(user.getUserId(), "123456");
                    return RestResult.ok(user);
                } else {
                    LOG.info("Create user failure {}", userIdResult.code);
                    return RestResult.error(ERROR_SERVER_ERROR);
                }
            } else {
                InputOutputUserInfo result = userResult.getResult();
                BeanUtil.copyProperties(result, user);
                return RestResult.ok(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("createUser exception", e);
            return RestResult.error(ERROR_SERVER_ERROR);
        }
    }


    /**
     * 设置用户默认密码
     *
     * @param userId   用户id
     * @param password 默认密码
     * @throws Exception
     */
    private void setUserDefaultPassword(String userId, String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(Sha1Hash.ALGORITHM_NAME);
        digest.reset();
        String salt = UUID.randomUUID().toString();
        digest.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] hashed = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String hashedPwd = Base64.getEncoder().encodeToString(hashed);
        UserPassword up = new UserPassword(userId, password, null);
        up.setPassword(hashedPwd);
        up.setSalt(salt);
        userPasswordRepository.save(up);
    }


    @Override
    public RestResult searchAbcUser(String username) {
//        http://abc.com/api/Control/Webservice.ashx?Action=lotto&Logic=UserDetails&Origin=1
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("Action", "lotto");
        paramMap.put("Logic", "UserDetails");
        paramMap.put("Origin", "1");
        paramMap.put("UserName", username);

        LOG.info("searchAbcUser paramMap {}", paramMap);

        String post = HttpUtil.post(abcUrl, paramMap);
        LOG.info("searchAbcUser post {}", post);
        if (StringUtils.isEmpty(post) || !JSONUtil.isJson(post)) {
            return RestResult.error(ERROR_SERVER_ERROR);
        }

        cn.hutool.json.JSONObject postResultJsonObject = JSONUtil.parseObj(post);
        Boolean success = postResultJsonObject.getBool("Success");
        List<String> userList = new ArrayList<>();
        if (Boolean.TRUE.equals(success) && postResultJsonObject.containsKey("Data")) {
            cn.hutool.json.JSONObject dataJsonObject = postResultJsonObject.getJSONObject("Data");
            if (dataJsonObject.containsKey("value")) {
                JSONArray userArray = dataJsonObject.getJSONArray("value"); // flag = 1
                if (!userArray.isEmpty()) {
//                    if (!userArray.isEmpty()) {
                    userArray.forEach(user -> {
                        cn.hutool.json.JSONObject userJsonObject = JSONUtil.parseObj(user);
                        if (!"1".equals(userJsonObject.getStr("exist")) ||  !"1".equals(userJsonObject.getStr("u_flag"))) {
                            return;
                        }
                        // 创建用户
                        RestResult restResult = searchAbcCreateUser(user, username);
                        WFCMessage.User userMessage = getSearchResultUser(restResult);
                        if (null != userMessage) {
                            try {
                                userList.add(ProtobufUtil.toJson(userMessage));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        }
        return RestResult.ok(userList);
    }

    /**
     * 获取搜索接口用户信息
     *
     * @param restResult 搜索结果
     * @return
     */
    private static WFCMessage.User getSearchResultUser(RestResult restResult) {
        WFCMessage.User.Builder userBuilder = WFCMessage.User.newBuilder();
        if (0 == restResult.getCode() && restResult.getResult() instanceof InputOutputUserInfoExt) {
            InputOutputUserInfoExt result = (InputOutputUserInfoExt) restResult.getResult();
            userBuilder.setUid(result.getUserId())
                    .setDisplayName(result.getDisplayName())
                    .setName(result.getName())
                    .setGender(result.getGender());
            if (StrUtil.isNotBlank(result.getMobile())) {
                userBuilder.setMobile(result.getMobile());
            }
            if (StrUtil.isNotBlank(result.getPortrait())) {
                userBuilder.setPortrait(result.getPortrait());
            }
            if (StrUtil.isNotBlank(result.getEmail())) {
                userBuilder.setEmail(result.getEmail());
            }
            if (StrUtil.isNotBlank(result.getAddress())) {
                userBuilder.setAddress(result.getAddress());
            }
            if (StrUtil.isNotBlank(result.getExtra())) {
                userBuilder.setPortrait(result.getExtra());
            }
            return userBuilder.build();
        }
        return null;
    }

    /**
     * 搜索结果创建用户
     *
     * @param user 用户信息
     * @return
     */
    private RestResult searchAbcCreateUser(Object user, String username) {
        cn.hutool.json.JSONObject userJsonObject = JSONUtil.parseObj(user);
        InputOutputUserInfo userInfo = new InputOutputUserInfo();
        userInfo.setUserId(username);
        userInfo.setName(username);
        String uNickName = userJsonObject.getStr("u_nickName");
        if (StrUtil.isNotBlank(uNickName)) {
            userInfo.setDisplayName(uNickName);
        } else {
            userInfo.setDisplayName(username);
        }
        RestResult restResult = this.createUser(userInfo);
        LOG.info("searchAbcUser createUser result {}", restResult);
        return restResult;
    }


    @Override
    public RestResult getTokenByUserInfo(UserPasswordLoginRequest request, HttpServletResponse resp) {
        InputOutputUserInfo userInfo = new InputOutputUserInfo();
        userInfo.setUserId(request.getUserId());
        userInfo.setName(request.getUserName());
        userInfo.setDisplayName(request.getDisplayName());
        RestResult restResult = this.createUser(userInfo);
        if (SUCCESS.code == restResult.getCode() && null != restResult.getResult()
                && restResult.getResult() instanceof InputOutputUserInfoExt) {
            try {
                InputOutputUserInfoExt inputOutputUserInfo = (InputOutputUserInfoExt) restResult.getResult();
                return loginWithPassword(request, inputOutputUserInfo, true, resp);
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("setUserDefaultPassword exception", e);
            }
        }
        return RestResult.error(ERROR_SERVER_ERROR);
    }

    private RestResult loginWithPassword(UserPasswordLoginRequest request, InputOutputUserInfoExt userInfoExt, boolean withResetCode, HttpServletResponse resp) {
        try {
            IMResult<InputOutputUserInfo> userResult = UserAdmin.getUserByName(userInfoExt.getName());
            if (userResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_EXIST) {
                //当用户不存在或者密码不存在时，返回密码错误。避免被攻击遍历登录获取用户名。
                return RestResult.error(ERROR_CODE_INCORRECT);
            }
            if (userResult.getErrorCode() != ErrorCode.ERROR_CODE_SUCCESS) {
                return RestResult.error(ERROR_SERVER_ERROR);
            }
            InputOutputUserInfo userInfo = userResult.getResult();
            Optional<UserPassword> optional = userPasswordRepository.findById(userInfo.getUserId());
            if (!optional.isPresent()) {
                //当用户不存在或者密码不存在时，返回密码错误。避免被攻击遍历登录获取用户名。
                return RestResult.error(ERROR_CODE_INCORRECT);
            }
            UserPassword up = optional.get();
            if (up.getTryCount() > 5) {
                if (System.currentTimeMillis() - up.getLastTryTime() < 5 * 60 * 1000) {
                    return RestResult.error(ERROR_FAILURE_TOO_MUCH_TIMES);
                }
                up.setTryCount(0);
            }
            up.setTryCount(up.getTryCount() + 1);
            up.setLastTryTime(System.currentTimeMillis());
            userPasswordRepository.save(up);

            //检查用户是否被封禁
            int userStatus = getUserStatusByName(userInfoExt.getName());
            if (userStatus == 2) {
                return RestResult.error(ERROR_USER_FORBIDDEN);
            }

            Subject subject = SecurityUtils.getSubject();
            // 在认证提交前准备 token（令牌）
            UsernameToken token = new UsernameToken(userInfoExt.getUserId());
            // 执行认证登陆
            try {
                subject.login(token);
            } catch (UnknownAccountException uae) {
                return RestResult.error(ERROR_SERVER_ERROR);
            } catch (IncorrectCredentialsException ice) {
                return RestResult.error(ERROR_CODE_INCORRECT);
            } catch (LockedAccountException lae) {
                return RestResult.error(ERROR_CODE_INCORRECT);
            } catch (ExcessiveAttemptsException eae) {
                return RestResult.error(ERROR_CODE_INCORRECT);
            } catch (AuthenticationException ae) {
                return RestResult.error(ERROR_CODE_INCORRECT);
            }
            if (subject.isAuthenticated()) {
                long timeout = subject.getSession().getTimeout();
                LOG.info("Login success " + timeout);
                up.setTryCount(0);
                up.setLastTryTime(0);
                userPasswordRepository.save(up);
            } else {
                return RestResult.error(ERROR_CODE_INCORRECT);
            }

            //使用用户id获取token
            IMResult<OutputGetIMTokenData> tokenResult = UserAdmin.getUserToken(userInfo.getUserId(),
                    request.getClientId(), request.getPlatform());
            if (tokenResult.getErrorCode() != ErrorCode.ERROR_CODE_SUCCESS) {
                LOG.error("Get user token failure {}", tokenResult.code);
                return RestResult.error(ERROR_SERVER_ERROR);
            }

            subject.getSession().setAttribute("userId", userInfo.getUserId());

            //返回用户id，token和是否新建
            LoginResponse response = new LoginResponse();
            response.setUserId(userInfo.getUserId());
            response.setToken(tokenResult.getResult().getToken());
//            response.setRegister(isNewUser);
            response.setPortrait(userInfo.getPortrait());
            response.setUserName(userInfo.getName());

            if (withResetCode) {
                String code = Utils.getRandomCode(6);
                Optional<UserPassword> optionalUp = userPasswordRepository.findById(userInfo.getUserId());
                UserPassword userPassword;
                if (optional.isPresent()) {
                    userPassword = optional.get();
                } else {
                    userPassword = new UserPassword(userInfo.getUserId(), null, null);
                }
                userPassword.setResetCode(code);
                userPassword.setResetCodeTime(System.currentTimeMillis());
                userPasswordRepository.save(userPassword);
                response.setResetCode(code);
            }

            if (userInfoExt.isNewUser()) {
                if (!StringUtils.isEmpty(mIMConfig.getWelcome_for_new_user())) {
                    sendTextMessage(mIMConfig.admin_user_id, userInfoExt.getUserId(), mIMConfig.getWelcome_for_new_user());
                }

                if (mIMConfig.isNew_user_robot_friend() && !StringUtils.isEmpty(mIMConfig.getRobot_friend_id())) {
                    RelationAdmin.setUserFriend(userInfoExt.getUserId(), mIMConfig.getRobot_friend_id(), true, null);
                }
                if (!StringUtils.isEmpty(mIMConfig.getRobot_welcome())) {
                    sendTextMessage(mIMConfig.getRobot_friend_id(), userInfoExt.getUserId(), mIMConfig.getRobot_welcome());
                }

                if (!StringUtils.isEmpty(mIMConfig.getNew_user_subscribe_channel_id())) {
                    try {
                        GeneralAdmin.subscribeChannel(mIMConfig.getNew_user_subscribe_channel_id(), userInfoExt.getUserId());
                    } catch (Exception e) {

                    }
                }
            } else {
                if (!StringUtils.isEmpty(mIMConfig.getWelcome_for_back_user())) {
                    sendTextMessage(mIMConfig.admin_user_id, userInfoExt.getUserId(), mIMConfig.getWelcome_for_back_user());
                }
                if (!StringUtils.isEmpty(mIMConfig.getRobot_welcome())) {
                    sendTextMessage(mIMConfig.getRobot_friend_id(), userInfoExt.getUserId(), mIMConfig.getRobot_welcome());
                }
                if (!StringUtils.isEmpty(mIMConfig.getBack_user_subscribe_channel_id())) {
                    try {
                        IMResult<OutputBooleanValue> booleanValueIMResult = GeneralAdmin.isUserSubscribedChannel(userInfoExt.getUserId(), mIMConfig.getBack_user_subscribe_channel_id());
                        if (booleanValueIMResult != null && booleanValueIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS && !booleanValueIMResult.getResult().value) {
                            GeneralAdmin.subscribeChannel(mIMConfig.getBack_user_subscribe_channel_id(), userInfoExt.getUserId());
                        }
                    } catch (Exception e) {

                    }
                }
            }

            if (!StringUtils.isEmpty(mIMConfig.getPrompt_text())) {
                sendTextMessage(mIMConfig.admin_user_id, userInfoExt.getUserId(), mIMConfig.getPrompt_text());
            }

            if (!StringUtils.isEmpty(mIMConfig.getImage_msg_url()) && !StringUtils.isEmpty(mIMConfig.getImage_msg_base64_thumbnail())) {
                sendImageMessage(mIMConfig.admin_user_id, userInfoExt.getUserId(), mIMConfig.getImage_msg_url(), mIMConfig.getImage_msg_base64_thumbnail());
            }

            LOG.info("login with session success, userId {}, clientId {}, platform {}, adminUrl {}", userInfoExt.getUserId(), request.getClientId(), request.getPlatform(), adminUrl);
            Object sessionId = subject.getSession().getId();
            resp.setHeader("authToken", sessionId.toString());
            return RestResult.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.error(ERROR_SERVER_ERROR);
        }
    }

    private void sendTextMessage(String fromUser, String toUser, String text) {
        Conversation conversation = new Conversation();
        conversation.setTarget(toUser);
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = new MessagePayload();
        payload.setType(1);
        payload.setSearchableContent(text);

        sendMessage(fromUser, conversation, payload);
    }

    private void sendMessage(String fromUser, Conversation conversation, MessagePayload payload) {
        try {
            IMResult<SendMessageResult> resultSendMessage = MessageAdmin.sendMessage(fromUser, conversation, payload);
            if (resultSendMessage != null && resultSendMessage.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                LOG.info("send message success");
            } else {
                LOG.error("send message error {}", resultSendMessage != null ? resultSendMessage.getErrorCode().code : "unknown");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("send message error {}", e.getLocalizedMessage());
        }
    }

    private void sendImageMessage(String fromUser, String toUser, String url, String base64Thumbnail) {
        Conversation conversation = new Conversation();
        conversation.setTarget(toUser);
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = new MessagePayload();
        payload.setType(3);
        payload.setRemoteMediaUrl(url);
        payload.setBase64edData(base64Thumbnail);
        payload.setMediaType(1);
        payload.setSearchableContent("[图片]");

        sendMessage(fromUser, conversation, payload);
    }
}
