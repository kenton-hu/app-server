package cn.wildfirechat.app.admin.service;

import cn.wildfirechat.app.admin.dto.req.IndexInfoReqDTO;
import cn.wildfirechat.app.admin.dto.resp.IndexInfoRespDTO;
import cn.wildfirechat.app.admin.dto.resp.IndexTop10RespDTO;
import cn.wildfirechat.app.admin.result.Result;

import java.util.List;

/**
 * index service
 */
public interface IndexService {

    // 每日新增用户数
    List<IndexInfoRespDTO> dailyNewUserCount(IndexInfoReqDTO reqDTO);
    // 每日活跃用户数
    List<IndexInfoRespDTO> dailyActiveUserCount(IndexInfoReqDTO reqDTO);
    // 累计用户数
    List<IndexInfoRespDTO> totalUser(IndexInfoReqDTO reqDTO);
    // 每日消息发送数
    List<IndexInfoRespDTO> dailySendMsgCount(IndexInfoReqDTO reqDTO);
    // 每日创建群数
    List<IndexInfoRespDTO> dailyCreateGroup(IndexInfoReqDTO reqDTO);
    // 累计创建群数
    List<IndexInfoRespDTO> totalCreateGroup(IndexInfoReqDTO reqDTO);
    // 最新100张图片轮播
    List<IndexInfoRespDTO> top100Image(IndexInfoReqDTO reqDTO);
    // Top10活跃用户数
    List<IndexTop10RespDTO> top10ActiveUser(IndexInfoReqDTO reqDTO);
    // Top10活跃群组数
    List<IndexTop10RespDTO> top10ActiveGroup(IndexInfoReqDTO reqDTO);
}
