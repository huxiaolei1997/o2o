package com.imooc.o2o.dto;

import java.util.List;


/**
 * 分页信息和查询数据条件
 *
 * @param <T>
 */
public class Pager<T> {
    // 页号
    private Integer pageIndex;
    // 每页显示多少条数据
    private Integer pageSize;
    // 总页数
    private Integer totalPage;
    // 总记录数
    private Integer totalRecord;
    // 要显示的分页数据
    private List<T> dataList;
    // 数据实体，用来接收映射接收前端的json数据，组装查询条件
    private T dataCondition;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public T getDataCondition() {
        return dataCondition;
    }

    public void setDataCondition(T dataCondition) {
        this.dataCondition = dataCondition;
    }

    public Pager(Integer pageIndex, Integer pageSize, Integer totalPage, Integer totalRecord,
                 List<T> dataList, T dataCondition) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalRecord = totalRecord;
        this.dataList = dataList;
        this.dataCondition = dataCondition;
    }

}
