package cn.wildfirechat.app.admin.dto.resp;

import cn.wildfirechat.app.wfchat.jpa.TUser;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应DTO
 */
public class PageRespDTO<T> implements Serializable {
    private List<T> items;
    private int pageSize;
    private int pageNo;
    private long totalCount;
    private int totalPage;

    public PageRespDTO() {
    }

    public PageRespDTO(int pageSize, int pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
