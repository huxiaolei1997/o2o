package com.imooc.o2o.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei hu
 * @date 2018/9/4 19:53
 **/
public class Area implements Serializable {
    private static final long serialVersionUID = -6551348044761819655L;
    // ID
    private Integer areaId;
    // 名称
    private String areaName;
    // 权重
    private Integer priority;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date lastEditTime;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "{" +
                "areaId: " + areaId +
                ", areaName: '" + areaName + '\'' +
                ", priority: " + priority +
                ", createTime: " + createTime +
                ", lastEditTime: " + lastEditTime +
                '}';
    }
}
