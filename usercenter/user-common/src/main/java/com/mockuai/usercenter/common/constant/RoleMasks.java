package com.mockuai.usercenter.common.constant;

/**
 * Created by duke on 15/8/22.
 */

/**
 * 用户权限，由于用户可能会具有多个权限，所以采用掩码的方式进行处理
 * */
public class RoleMasks {
    public final static Long NONE = 0L;
    public final static Long BUYER = 1L;
    public final static Long SELLER = 2L;
    public final static Long MANAGER = 4L;

    /**
     * 检查权限
     *
     * @param roleMask
     * @param role
     * */
    public static Boolean checkPermission(Long roleMask, Long role) {
        // 拥有对应的权限
        if ((roleMask & role) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 在已有权限的基础上授予权限
     *
     * @param roleMask
     * @param role
     * @return
     * */
    public static Long grantPermission(Long roleMask, Long role) {
        return roleMask | role;
    }

    /**
     * 去除权限
     *
     * @param roleMask
     * @param role
     * @return
     * */
    public static Long cancelPermission(Long roleMask, Long role) {
        return roleMask & (~role);
    }
}
