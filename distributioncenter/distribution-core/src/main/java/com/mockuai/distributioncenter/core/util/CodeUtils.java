package com.mockuai.distributioncenter.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 16/3/9.
 */
public class CodeUtils {
    /**
     * 解析邀请码
     * @param invitationCodeStr 邀请码字符串
     * @return 邀请码列表
     * */
    public static List<String> parseInvitationCode(String invitationCodeStr, int bizType) {
        String [] codes = invitationCodeStr.split(",");
        List<String> invitationCodes = new ArrayList<String>();

        for (String code : codes) {
            String [] tokens = code.split("-");

            if (tokens.length < 2) {
                continue;
            }

            if (!tokens[0].matches("\\d+")) {
                continue;
            }

            // 解析分销和微小店的邀请码
            if (Integer.parseInt(tokens[0]) == bizType) {
                invitationCodes.add(tokens[1]);
            }
        }
        return invitationCodes;
    }
}
