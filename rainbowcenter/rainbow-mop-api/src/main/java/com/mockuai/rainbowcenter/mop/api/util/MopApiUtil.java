package com.mockuai.rainbowcenter.mop.api.util;

import com.mockuai.rainbowcenter.common.dto.CreditAutoLoginDTO;
import com.mockuai.rainbowcenter.common.dto.DistDeductDTO;
import com.mockuai.rainbowcenter.mop.api.domain.MopCreditAutoLogDTO;
import com.mockuai.rainbowcenter.mop.api.domain.MopCreditsDTO;

/**
 * Created by lizg on 2016/7/19.
 */
public class MopApiUtil {



    public static MopCreditsDTO deductCredits(DistDeductDTO distDeductDTO) {
        MopCreditsDTO mopCreditsDTO = new MopCreditsDTO();
        mopCreditsDTO.setStatus(distDeductDTO.getStatus());
        mopCreditsDTO.setErrorMessage(distDeductDTO.getErrorMessage());
        mopCreditsDTO.setBizId(distDeductDTO.getBizId());
        mopCreditsDTO.setCredits(distDeductDTO.getBalanceCredits());
        return mopCreditsDTO;
    }


    public static MopCreditAutoLogDTO getCreditAutoLogDTO(CreditAutoLoginDTO creditAutoLoginDTO) {
        MopCreditAutoLogDTO mopCreditAutoLogDTO = new MopCreditAutoLogDTO();
        mopCreditAutoLogDTO.setLoginUrl(creditAutoLoginDTO.getLoginUrl());
        return mopCreditAutoLogDTO;
    }
}
