package cn.wildfirechat.app.admin.dto.resp;

import java.io.Serializable;
import java.util.List;

public class IndexRespDTO implements Serializable {
    /**
     * 每日活跃用户数
     */
    private List<IndexInfoRespDTO> activeUser;

    /**
     * 每日新创建群组
     */
    private List<IndexInfoRespDTO> newGroup;

    /**
     * 每日新增用户数
     */
    private List<IndexInfoRespDTO> newUser;

    /**
     * Top10活跃群组
     */
    private List<IndexTop10RespDTO> top10Group;

    /**
     * Top10活跃用户数
     */
    private List<IndexTop10RespDTO> top10NewMesFrom;

    /**
     * 最新100张图片轮播
     */
    private List<IndexInfoRespDTO> top100Image;

    /**
     * 累计创建群组数
     */
    private List<IndexInfoRespDTO> totalGroup;

    /**
     * 每日发送消息数
     */
    private List<IndexInfoRespDTO> totalMsg;

    /**
     * 累计用户数
     */
    private List<IndexInfoRespDTO> totalUser;

    public List<IndexInfoRespDTO> getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(List<IndexInfoRespDTO> activeUser) {
        this.activeUser = activeUser;
    }

    public List<IndexInfoRespDTO> getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(List<IndexInfoRespDTO> newGroup) {
        this.newGroup = newGroup;
    }

    public List<IndexInfoRespDTO> getNewUser() {
        return newUser;
    }

    public void setNewUser(List<IndexInfoRespDTO> newUser) {
        this.newUser = newUser;
    }

    public List<IndexTop10RespDTO> getTop10Group() {
        return top10Group;
    }

    public void setTop10Group(List<IndexTop10RespDTO> top10Group) {
        this.top10Group = top10Group;
    }

    public List<IndexTop10RespDTO> getTop10NewMesFrom() {
        return top10NewMesFrom;
    }

    public void setTop10NewMesFrom(List<IndexTop10RespDTO> top10NewMesFrom) {
        this.top10NewMesFrom = top10NewMesFrom;
    }

    public List<IndexInfoRespDTO> getTop100Image() {
        return top100Image;
    }

    public void setTop100Image(List<IndexInfoRespDTO> top100Image) {
        this.top100Image = top100Image;
    }

    public List<IndexInfoRespDTO> getTotalGroup() {
        return totalGroup;
    }

    public void setTotalGroup(List<IndexInfoRespDTO> totalGroup) {
        this.totalGroup = totalGroup;
    }

    public List<IndexInfoRespDTO> getTotalMsg() {
        return totalMsg;
    }

    public void setTotalMsg(List<IndexInfoRespDTO> totalMsg) {
        this.totalMsg = totalMsg;
    }

    public List<IndexInfoRespDTO> getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(List<IndexInfoRespDTO> totalUser) {
        this.totalUser = totalUser;
    }
}
