package cn.wildfirechat.app.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wildfirechat.app.admin.dto.req.*;
import cn.wildfirechat.app.admin.dto.resp.GroupUserRespDTO;
import cn.wildfirechat.app.admin.dto.resp.PageRespDTO;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TGroupService;
import cn.wildfirechat.app.wfchat.jpa.*;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.OutputCreateGroupResult;
import cn.wildfirechat.pojos.PojoGroupInfo;
import cn.wildfirechat.pojos.PojoGroupMember;
import cn.wildfirechat.sdk.GroupAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TGroupServiceImpl implements TGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(TGroupServiceImpl.class);
    @Autowired
    private TGroupRepository tGroupRepository;
    @Autowired
    private TGroupMemberRepository tGroupMemberRepository;
    @Autowired
    private TUserRepository tUserRepository;

    @Override
    public Result<?> getGroupList(GroupListReqDTO reqDTO) {
        LOG.info("getGroupList: {}", reqDTO);
        // 查询条件存在这个对象中
        Specification<TGroup> specification = (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (StrUtil.isNotBlank(reqDTO.getSearchKey())) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + reqDTO.getSearchKey() + "%"));
            }
            Predicate[] p = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(p));
        };
        PageRequest pageRequest = PageRequest.of(reqDTO.getPageNo() - 1, reqDTO.getPageSize());
        Page<TGroup> page = tGroupRepository.findAll(specification, pageRequest);
        PageRespDTO<TGroup> pageRespDTO = new PageRespDTO<>();
        pageRespDTO.setItems(page.getContent());
        pageRespDTO.setPageNo(reqDTO.getPageNo());
        pageRespDTO.setPageSize(reqDTO.getPageSize());
        pageRespDTO.setTotalPage(page.getTotalPages());
        pageRespDTO.setTotalCount(page.getTotalElements());
        return new Result<>().success(pageRespDTO, reqDTO.getSessionId());
    }

    @Override
    public Result<?> createGroup(AddGroupReqDTO reqDTO) {
        PojoGroupInfo groupInfo = new PojoGroupInfo();
        groupInfo.setExtra(reqDTO.getGroupExtra());
        groupInfo.setName(reqDTO.getGroupName());
        groupInfo.setOwner(reqDTO.getOwner());
        groupInfo.setPortrait(reqDTO.getPortrait());
        groupInfo.setType(Integer.parseInt(reqDTO.getType()));
        IMResult<OutputCreateGroupResult> groupImResult = new IMResult<>();
        try {
            groupImResult = GroupAdmin.createGroup("admin", groupInfo, null, null, null);
            if (groupImResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                // 返回data
                return new Result<>().success(groupImResult.getResult(), reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(groupImResult.getCode()), groupImResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> groupUsers(GroupUserListReqDTO reqDTO) {
        LOG.info("groupUsers: {}", reqDTO);
        // 查询条件存在这个对象中
        Specification<TGroupMember> specification = (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (StrUtil.isNotBlank(reqDTO.getSearchKey())) {
                predicateList.add(cb.like(root.get("mid").as(String.class), "%" + reqDTO.getSearchKey() + "%"));
            }
            Predicate[] p = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(p));
        };
        PageRequest pageRequest = PageRequest.of(reqDTO.getPageNo() - 1, reqDTO.getPageSize());
        Page<TGroupMember> page = tGroupMemberRepository.findAll(specification, pageRequest);
        PageRespDTO<GroupUserRespDTO> pageRespDTO = new PageRespDTO<>();
        List<TGroupMember> content = page.getContent();
        if (CollUtil.isNotEmpty(content)) {
            List<String> midList = content.stream().map(TGroupMember::getMemberId).collect(Collectors.toList());
            List<TUser> userList = tUserRepository.findByUids(midList);
            Map<String, TUser> userMap = userList.stream().collect(Collectors.toMap(TUser::getUid, user -> user));

            List<GroupUserRespDTO> resultList = new ArrayList<>();
            for (TGroupMember groupMember : content) {
                GroupUserRespDTO groupUserRespDTO = new GroupUserRespDTO();
                BeanUtil.copyProperties(groupMember, groupUserRespDTO);
                if (userMap.containsKey(groupMember.getMemberId())) {
                    TUser user = userMap.get(groupMember.getMemberId());
                    groupUserRespDTO.setMemberId(user.getUid());
                    groupUserRespDTO.setAlias(user.getDisplayName());
                    groupUserRespDTO.setPortrait(user.getPortrait());
                    groupUserRespDTO.setName(user.getName());
                    groupUserRespDTO.setDisplayName(user.getDisplayName());
                }
                resultList.add(groupUserRespDTO);
            }
            pageRespDTO.setItems(resultList);
        }
        pageRespDTO.setPageNo(reqDTO.getPageNo());
        pageRespDTO.setPageSize(reqDTO.getPageSize());
        pageRespDTO.setTotalPage(page.getTotalPages());
        pageRespDTO.setTotalCount(page.getTotalElements());
        return new Result<>().success(pageRespDTO, reqDTO.getSessionId());
    }

    @Override
    public Result<?> transferGroup(TransferGroupReqDTO reqDTO) {
        LOG.info("transferGroup: {}", reqDTO);
        IMResult<Void> transferIMResult = new IMResult<>();
        try {
            transferIMResult =  GroupAdmin.transferGroup("admin", reqDTO.getTargetId(), reqDTO.getNewOwnerId(), null, null);
            if (transferIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(transferIMResult.getCode()), transferIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> changeUserType(ChangeGroupUserTypeReqDTO reqDTO) {
        LOG.info("changeUserType: {}", reqDTO);
        IMResult<Void> changeIMResult = new IMResult<>();
        try {
            changeIMResult =  GroupAdmin.setGroupManager("admin", reqDTO.getGroupId(),
                    Collections.singletonList(reqDTO.getMemberId()), 1 == reqDTO.getType(),
                    null, null);
            if (changeIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(changeIMResult.getCode()), changeIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> delUser(GroupUserReqDTO reqDTO) {
        LOG.info("delUser: {}", reqDTO);
        IMResult<Void> delUserIMResult = new IMResult<>();
        try {
            delUserIMResult =  GroupAdmin.kickoffGroupMembers("admin", reqDTO.getTargetId(), reqDTO.getUserIds(), null ,null);
            if (delUserIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(delUserIMResult.getCode()), delUserIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> addUser(GroupUserReqDTO reqDTO) {
        LOG.info("addUser: {}", reqDTO);
        IMResult<Void> addUserIMResult = new IMResult<>();
        try {
            List<PojoGroupMember> groupMembers = new ArrayList<>();
            for (String userId : reqDTO.getUserIds()) {
                PojoGroupMember groupMember = new PojoGroupMember();
                groupMember.setMember_id(userId);
                groupMembers.add(groupMember);
            }
            addUserIMResult =  GroupAdmin.addGroupMembers("admin", reqDTO.getTargetId(), groupMembers, null ,null);
            if (addUserIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(addUserIMResult.getCode()), addUserIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> modifyGroupInfo(ModifyGroupReqDTO reqDTO) {
        LOG.info("modifyGroupInfo: {}", reqDTO);
        IMResult<Void> modifyGroupIMResult = new IMResult<>();
        try {
            modifyGroupIMResult =  GroupAdmin.modifyGroupInfo("admin", reqDTO.getTargetId(), reqDTO.getType(),
                    String.valueOf(reqDTO.getValue()), null, null);
            if (modifyGroupIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(modifyGroupIMResult.getCode()), modifyGroupIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> dismissGroup(DismissGroupReqDTO reqDTO) {
        LOG.info("dismissGroup: {}", reqDTO);
        IMResult<Void> dismissGroupIMResult = new IMResult<>();
        try {
            dismissGroupIMResult =  GroupAdmin.dismissGroup("admin", reqDTO.getTargetId(),null, null);
            if (dismissGroupIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(null, reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(dismissGroupIMResult.getCode()), dismissGroupIMResult.getMsg(), reqDTO.getSessionId());
    }
}
