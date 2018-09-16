package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.PersonInfo;

public interface PersonInfoDao {

    /**
     * @param personInfoCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<PersonInfo> queryPersonInfoList(
            @Param("personInfoCondition") PersonInfo personInfoCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * @param personInfoCondition
     * @return
     */
    int queryPersonInfoCount(
            @Param("personInfoCondition") PersonInfo personInfoCondition);

    /**
     * 通过用户 Id 查询用户
     *
     * @param userId
     * @return
     */
    PersonInfo queryPersonInfoById(long userId);

    /**
     * 添加用户信息
     *
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);

    /**
     * @param personInfo
     * @return
     */
    int updatePersonInfo(PersonInfo personInfo);

    /**
     * @param userId
     * @return
     */
    int deletePersonInfo(long userId);
}
