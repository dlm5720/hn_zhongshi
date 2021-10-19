package com.hnzs.dao.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CommonDao {

    ArrayList<Map<Object, Object>> selectExecute(String sql);

    List<?> selectExecute(String sql, int offset, int length);

    @SuppressWarnings("rawtypes")
    int addUpdateDeleteExecute(ArrayList<String> alist);

}
