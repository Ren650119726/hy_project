package com.mockuai.itemcenter.common.api;

import com.mockuai.itemcenter.common.api.ItemResponse;

import java.util.List;
import java.util.Locale;

/**
 * Created by idoud on 4/22/15.
 */
public interface CategoryService {
    /**
     * 根据类目ID，查询下级子分类列表
     * @param appkey
     * @param userId
     * @param categoryId
     * @return
     */
    public ItemResponse<List<Locale.Category>> queryCategory(String appkey, long userId, long categoryId);

}
