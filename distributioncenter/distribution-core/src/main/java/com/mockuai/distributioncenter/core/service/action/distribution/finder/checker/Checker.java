package com.mockuai.distributioncenter.core.service.action.distribution.finder.checker;

/**
 * Created by duke on 16/5/17.
 */
public interface Checker<T> {
    /**
     * 检查是否满足条件
     *
     * @param o1 当前节点
     * @param o2 父级节点
     * @return true - o1 和 o2 之间满足条件
     *         false - 01 和 02 之间不满足条件
     * */
    boolean check(T o1, T o2);
}
