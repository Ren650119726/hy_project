package com.mockuai.dts.core.manager;

import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public interface ItemExportManager {

    /**
     * 导出商品到本地文件;
     * @param itemExportQTO
     * @return
     */
    public boolean exportItems(ItemExportQTO itemExportQTO, ExportTaskDO exportTaskDO) throws DtsException;


}
