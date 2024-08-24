package cn.wildfirechat.app.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import cn.wildfirechat.app.admin.dto.req.*;
import cn.wildfirechat.app.admin.dto.resp.IndexRespDTO;
import cn.wildfirechat.app.admin.dto.resp.PageRespDTO;
import cn.wildfirechat.app.admin.dto.resp.UserInfoRespDTO;
import cn.wildfirechat.app.admin.dto.resp.UserRespDTO;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.IndexService;
import cn.wildfirechat.app.admin.service.TUserService;
import cn.wildfirechat.app.admin.utils.RedisUtil;
import cn.wildfirechat.app.admin.utils.UserUtils;
import cn.wildfirechat.app.wfchat.jpa.*;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.*;
import cn.wildfirechat.sdk.MessageAdmin;
import cn.wildfirechat.sdk.SensitiveAdmin;
import cn.wildfirechat.sdk.UserAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TUserServiceImpl implements TUserService {

    private static final Logger LOG = LoggerFactory.getLogger(TUserServiceImpl.class);
    @Autowired
    private TUserRepository tUserRepository;
    @Autowired
    private TUserStatusRepository tUserStatusRepository;
    @Autowired
    private TSensitiveMessageRepository tSensitiveMessageRepository;
    @Autowired
    private IndexService indexService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserUtils userUtils;

    @Override
    public Result<?> updatePwd(UpdatePwdReqDTO reqDTO) {
        LOG.info("reqDTO: {}", reqDTO);
        // 获取用户
        TUser user = userUtils.getUserBySessionId(reqDTO.getSessionId());
        if (null == user) {
            return new Result<>().error("session:invalid", "sessionId失效", reqDTO.getSessionId());
        }
        Optional<TUser> optional = tUserRepository.findById(user.getId());
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
        return new Result<>().success(null, reqDTO.getSessionId());
    }

    @Override
    public Result<?> updateIcon(UpdateIconReqDTO reqDTO) {
        LOG.info("reqDTO: {}", reqDTO);
        // 获取用户
        TUser user = userUtils.getUserBySessionId(reqDTO.getSessionId());
        if (null == user) {
            return new Result<>().error("session:invalid", "sessionId失效", reqDTO.getSessionId());
        }
        Optional<TUser> optional = tUserRepository.findById(user.getId());
        if (!optional.isPresent()) {
            // 用户不存在，返回错误信息
            return new Result<>().error("user:not:exist", "用户不存在", reqDTO.getSessionId());
        }
        TUser tUser = optional.get();
        LOG.info("tuser: {}", tUser);
        tUser.setPortrait(reqDTO.getIcon());
        tUserRepository.save(tUser);
        return new Result<>().success(tUser, reqDTO.getSessionId());
    }

    @Override
    public Result<?> updatePhone(UpdatePhoneReqDTO reqDTO) {
        LOG.info("reqDTO: {}", reqDTO);
        // 获取用户
        TUser user = userUtils.getUserBySessionId(reqDTO.getSessionId());
        if (null == user) {
            return new Result<>().error("session:invalid", "sessionId失效", reqDTO.getSessionId());
        }
        Optional<TUser> optional = tUserRepository.findById(user.getId());
        if (!optional.isPresent()) {
            // 用户不存在，返回错误信息
            return new Result<>().error("user:not:exist", "用户不存在", reqDTO.getSessionId());
        }
        TUser tUser = optional.get();
        LOG.info("tuser: {}", tUser);
        tUser.setMobile(reqDTO.getPhoneNumber());
        tUserRepository.save(tUser);
        return new Result<>().success(tUser, reqDTO.getSessionId());
    }

    @Override
    public Result<?> getUserInfo(UserInfoReqDTO reqDTO) {
        LOG.info("reqDTO: {}", reqDTO);
        // 获取用户
        TUser user = userUtils.getUserBySessionId(reqDTO.getSessionId());
        if (null == user) {
            return new Result<>().error("session:invalid", "sessionId失效", reqDTO.getSessionId());
        }
        Optional<TUser> optional = tUserRepository.findById(user.getId());
        if (!optional.isPresent()) {
            // 用户不存在，返回错误信息
            return new Result<>().error("user:not:exist", "用户不存在", reqDTO.getSessionId());
        }
        TUser tUser = optional.get();
        UserInfoRespDTO userInfoRespDTO = new UserInfoRespDTO();
        BeanUtil.copyProperties(tUser, userInfoRespDTO);
        userInfoRespDTO.setUser(tUser);
        userInfoRespDTO.setSessionId(reqDTO.getSessionId());
        // 查询用户状态
        TUserStatus tUserStatus = tUserStatusRepository.findByUid(tUser.getUid());
        userInfoRespDTO.setUserStatus(tUserStatus);
        return new Result<UserInfoRespDTO>().success(userInfoRespDTO, reqDTO.getSessionId());
    }

    @Override
    public Result<?> getUserList(UserListReqDTO reqDTO) {
        // 查询条件存在这个对象中
        Specification<TUser> specification = (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (StrUtil.isNotBlank(reqDTO.getSearchKey())) {
                List<Predicate> tempOr = new ArrayList<>(); //临时查询条件or
                tempOr.add(cb.like(root.get("name").as(String.class), "%" + reqDTO.getSearchKey() + "%"));
                tempOr.add(cb.like(root.get("displayName").as(String.class), "%" + reqDTO.getSearchKey() + "%"));
                tempOr.add(cb.like(root.get("mobile").as(String.class), "%" + reqDTO.getSearchKey() + "%"));

                Predicate[] arrayOr = new Predicate[tempOr.size()];
                predicateList.add(cb.or(tempOr.toArray(arrayOr)));

            }
            Predicate[] p = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(p));
        };
        PageRequest pageRequest = PageRequest.of(reqDTO.getPageNo() - 1, reqDTO.getPageSize());
        Page<TUser> page = tUserRepository.findAll(specification, pageRequest);
        PageRespDTO<TUser> pageRespDTO = new PageRespDTO<>();
        for (TUser tUser : page.getContent()) {
            TUserStatus tUserStatus = tUserStatusRepository.findByUid(tUser.getUid());
            if (null != tUserStatus) {
                tUser.setUserStatus(String.valueOf(tUserStatus.getStatus()));
            }
        }
        pageRespDTO.setItems(page.getContent());
        pageRespDTO.setPageNo(reqDTO.getPageNo());
        pageRespDTO.setPageSize(reqDTO.getPageSize());
        pageRespDTO.setTotalPage(page.getTotalPages());
        pageRespDTO.setTotalCount(page.getTotalElements());
        return new Result<>().success(pageRespDTO, reqDTO.getSessionId());
    }

    @Override
    public Result<?> sendMessage(UserSendMsgReqDTO reqDTO) {
        // 获取用户
        TUser user = userUtils.getUserBySessionId(reqDTO.getSessionId());
        if (null == user) {
            return new Result<>().error("session:invalid", "sessionId失效", reqDTO.getSessionId());
        }

        Conversation conversation = new Conversation();
        conversation.setTarget(reqDTO.getTo());

        MessagePayload payload = new MessagePayload();
        payload.setContent(reqDTO.getContent());
        IMResult<SendMessageResult> imResult = null;
        try {
            imResult = MessageAdmin.sendMessage(user.getUid(), conversation, payload);
            if (imResult != null && imResult.getCode() == ErrorCode.ERROR_CODE_SUCCESS.code) {
                return new Result<>().success(imResult.getResult(), reqDTO.getSessionId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result<>().error(imResult.getErrorCode() + "", imResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> login(LoginReqDTO reqDTO) {
        // 根据userName查询
        TUser tUser = tUserRepository.findByName(reqDTO.getUserName());
        if (null == tUser) {
            return new Result<>().error("user:not:exist", "用户不存在", reqDTO.getSessionId());
        }
        String password = DigestUtil.md5Hex(reqDTO.getPassword());
        LOG.info("tuser: {}", tUser);
        // 验证密码是否正确
        if (!password.equals(tUser.getPasswordMD5())) {
            // 密码不正确，返回错误信息
            return new Result<>().error("user:pwd:error", "密码错误", reqDTO.getSessionId());
        }
        UserInfoRespDTO userInfoRespDTO = new UserInfoRespDTO();
        BeanUtil.copyProperties(tUser, userInfoRespDTO);
        // 生成sessionId
        String sessionId = IdUtil.simpleUUID();
        userInfoRespDTO.setSessionId(sessionId);
        // 查询用户状态
        TUserStatus tUserStatus = tUserStatusRepository.findByUid(tUser.getUid());
        userInfoRespDTO.setUserStatus(tUserStatus);
        // 将用户信息存入redis
        redisUtil.set(sessionId, JSONUtil.toJsonStr(tUser), 24 * 60 * 60);
        return new Result<>().success(tUser, userInfoRespDTO.getSessionId());
    }

    @Override
    public Result<?> createUser(AddUserReqDTO reqDTO) {
        InputOutputUserInfo inputOutputUserInfo = new InputOutputUserInfo();
        BeanUtil.copyProperties(reqDTO, inputOutputUserInfo);
        IMResult<OutputCreateUser> imResult = null;
        IMResult<Void> voidIMResult = null;
        String code = "";
        String msg = "";
        try {
            if (StrUtil.isNotBlank(reqDTO.getUserId())) {
                // 修改
                voidIMResult = UserAdmin.updateUserInfo(inputOutputUserInfo, 1);
                if (voidIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    // 返回data
                    return new Result<>().success(new UserRespDTO(reqDTO.getName(), reqDTO.getUserId()), reqDTO.getSessionId());
                }
                code = String.valueOf(voidIMResult.getCode());
                msg = voidIMResult.getMsg();
            } else {
                // 添加
                imResult = UserAdmin.createUser(inputOutputUserInfo);
                if (imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    OutputCreateUser createUser = imResult.getResult();
                    // 返回data
                    return new Result<>().success(new UserRespDTO(createUser.getName(), createUser.getUserId()), reqDTO.getSessionId());
                }
                code = String.valueOf(imResult.getCode());
                msg = imResult.getMsg();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(code, msg, reqDTO.getSessionId());
    }

    @Override
    public Result<?> updateUserStatus(BlockUserReqDTO reqDTO) {
        LOG.info("updateUserStatus: {}", reqDTO);
        IMResult<Void> voidIMResult = null;
        try {
            voidIMResult = UserAdmin.updateUserBlockStatus(reqDTO.getUserId(), reqDTO.getStatus());
            if (voidIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(voidIMResult.getCode()), voidIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> destroyUser(DestroyUserReqDTO reqDTO) {
        LOG.info("destroyUser: {}", reqDTO);
        IMResult<Void> voidIMResult = null;
        try {
            voidIMResult = UserAdmin.destroyUser(reqDTO.getUserId());
            if (voidIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(voidIMResult.getCode()), voidIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> getBlockList(BlockUserListReqDTO reqDTO) {
        // 查询封禁用户列表
        List<TUserStatus> userStatusList = tUserStatusRepository.findByStatus(2);
        if (CollUtil.isEmpty(userStatusList)) {
            PageRespDTO<TUser> pageRespDTO = new PageRespDTO<>();
            pageRespDTO.setPageNo(reqDTO.getPageNo());
            pageRespDTO.setPageSize(reqDTO.getPageSize());
            pageRespDTO.setTotalPage(0);
            pageRespDTO.setTotalCount(0);
            return new Result<>().success(pageRespDTO, reqDTO.getSessionId());
        }
        // 获取封禁用户id
        List<String> userIdList = userStatusList.stream().map(TUserStatus::getUid).collect(Collectors.toList());
        // 查询条件存在这个对象中
        Specification<TUser> specification = (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (StrUtil.isNotBlank(reqDTO.getSearchKey())) {
                List<Predicate> tempOr = new ArrayList<>(); //临时查询条件or
                tempOr.add(cb.like(root.get("name").as(String.class), "%" + reqDTO.getSearchKey() + "%"));
                tempOr.add(cb.like(root.get("displayName").as(String.class), "%" + reqDTO.getSearchKey() + "%"));
                tempOr.add(cb.like(root.get("mobile").as(String.class), "%" + reqDTO.getSearchKey() + "%"));

                Predicate[] arrayOr = new Predicate[tempOr.size()];
                predicateList.add(cb.or(tempOr.toArray(arrayOr)));
            }
            if (CollUtil.isNotEmpty(userIdList)) {
                Expression<String> exp = root.<String>get("uid");
                predicateList.add(exp.in(userIdList));
            }
            Predicate[] p = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(p));
        };
        PageRequest pageRequest = PageRequest.of(reqDTO.getPageNo() - 1, reqDTO.getPageSize());
        Page<TUser> page = tUserRepository.findAll(specification, pageRequest);
        PageRespDTO<TUser> pageRespDTO = new PageRespDTO<>();
        for (TUser tUser : page.getContent()) {
            TUserStatus tUserStatus = tUserStatusRepository.findByUid(tUser.getUid());
            if (null != tUserStatus) {
                tUser.setUserStatus(String.valueOf(tUserStatus.getStatus()));
            }
        }
        pageRespDTO.setItems(page.getContent());
        pageRespDTO.setPageNo(reqDTO.getPageNo());
        pageRespDTO.setPageSize(reqDTO.getPageSize());
        pageRespDTO.setTotalPage(page.getTotalPages());
        pageRespDTO.setTotalCount(page.getTotalElements());
        // 查询用户状态
        return new Result<>().success(pageRespDTO, reqDTO.getSessionId());
    }

    @Override
    public Result<?> getSensetive(BasicReqDTO reqDTO) {
        IMResult<InputOutputSensitiveWords> sensitiveWordsIMResult = null;
        try {
            sensitiveWordsIMResult = SensitiveAdmin.getSensitives();
            if (sensitiveWordsIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(sensitiveWordsIMResult.getResult(), reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(sensitiveWordsIMResult.getCode())
                , sensitiveWordsIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> addSensetive(SensetiveReqDTO reqDTO) {
        IMResult<Void> sensitiveWordsIMResult = null;
        try {
            sensitiveWordsIMResult = SensitiveAdmin.addSensitives(reqDTO.getWords());
            if (sensitiveWordsIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(sensitiveWordsIMResult.getCode())
                , sensitiveWordsIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> delSensetive(SensetiveReqDTO reqDTO) {
        IMResult<Void> sensitiveWordsIMResult = null;
        try {
            sensitiveWordsIMResult = SensitiveAdmin.removeSensitives(reqDTO.getWords());
            if (sensitiveWordsIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(sensitiveWordsIMResult.getCode())
                , sensitiveWordsIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> sensitiveShot(BasicReqDTO reqDTO) {
        PageRequest pageRequest = PageRequest.of(reqDTO.getPageNo() - 1, reqDTO.getPageSize());
        Page<TSensitiveMessage> page = tSensitiveMessageRepository.findAll(pageRequest);
        PageRespDTO<TSensitiveMessage> pageRespDTO = new PageRespDTO<>();
        pageRespDTO.setItems(page.getContent());
        pageRespDTO.setPageNo(reqDTO.getPageNo());
        pageRespDTO.setPageSize(reqDTO.getPageSize());
        pageRespDTO.setTotalPage(page.getTotalPages());
        pageRespDTO.setTotalCount(page.getTotalElements());
        return new Result<>().success(pageRespDTO, reqDTO.getSessionId());
    }

    @Override
    public Result<?> delSensitiveShot(ClearSensitiveMessageReqDTO reqDTO) {
        if (reqDTO.isClearFlag()) {
            tSensitiveMessageRepository.deleteAll();
        }
        return new Result<>().success(null, reqDTO.getSessionId());
    }

    @Override
    public Result<?> alls(BasicReqDTO reqDTO) {
        IMResult<OutputGetUserList> allUsersIMResult = new IMResult<>();
        try {
            allUsersIMResult = UserAdmin.getAllUsers(Integer.MAX_VALUE, 0);
            if (allUsersIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(allUsersIMResult.getResult(), reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(allUsersIMResult.getCode()), allUsersIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> getIndexInfo(IndexInfoReqDTO reqDTO) {
        IndexRespDTO respDTO = new IndexRespDTO();
        // 每日新增用户数
        respDTO.setNewUser(indexService.dailyNewUserCount(reqDTO));
        // 每日活跃用户数
        respDTO.setActiveUser(indexService.dailyActiveUserCount(reqDTO));
        // 累计用户数
        respDTO.setTotalUser(indexService.totalUser(reqDTO));
        // 每日消息发送数
        respDTO.setTotalMsg(indexService.dailySendMsgCount(reqDTO));
        // 每日创建群数
        respDTO.setNewGroup(indexService.dailyCreateGroup(reqDTO));
        // 累计创建群数
        respDTO.setTotalGroup(indexService.totalCreateGroup(reqDTO));
        // 最新100张图片轮播
        respDTO.setTop100Image(indexService.top100Image(reqDTO));
        // Top10活跃用户数
        respDTO.setTop10NewMesFrom(indexService.top10ActiveUser(reqDTO));
        // Top10活跃群组数
        respDTO.setTop10Group(indexService.top10ActiveGroup(reqDTO));
        return new Result<>().success(respDTO, reqDTO.getSessionId());
    }
}
