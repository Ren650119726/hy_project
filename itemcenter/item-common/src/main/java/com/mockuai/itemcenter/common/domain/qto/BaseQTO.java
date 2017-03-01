package com.mockuai.itemcenter.common.domain.qto;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 7/27/15.
 */
public class BaseQTO implements Serializable{
    /**
     * 偏移行数
     */
    private int offset = 0;
    /**
     * 一次查询行数
     */
    private int count = 200;


}
