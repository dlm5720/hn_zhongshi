package com.hnzs.service.common;


import java.util.HashMap;

public interface RecruitService {

    /**
     * 开启收录信息保存
     * @param map
     * @return
     */
    public String saveOpenCollectionInfos(HashMap map);

    /**
     * 关闭指定收录信息保存
     * @param map
     * @return
     */
    public String saveCloseCollectionInfos(HashMap map);



}
