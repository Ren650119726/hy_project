package com.mockuai.tradecenter.core.manager.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderSeqManager;

public class OrderSeqManagerImpl implements OrderSeqManager{
	
	private static DateFormat DATE_FORMAT =new SimpleDateFormat("yyyyMMddHHmmssS");
	@Override
	public String getOrderSn(Long userId)throws TradeException{
//		//TODO 生成订单流水号的逻辑先简单实现，后续需要重构
		/**
		 *  20161008
			144632
			764
			0
			1760950
			763
		 */
		String orderSn = StringUtils.rightPad(DATE_FORMAT.format(new Date()), 17, "0")+"0"+StringUtils.leftPad("" + userId % 10000000, 7, "0")
				+genRandomNumber(3);
		
		return orderSn;
	}

	@Override
	public List<String> getSubOrderSnList(String mainOrderSn, int subOrderCount) throws TradeException {
		//取主单流水号前25位和后3位
		String beforeStr = mainOrderSn.substring(0, 25);
		String lastStr = mainOrderSn.substring(25);
		int lastNum = Integer.valueOf(lastStr);
		List<String> subOrderSnList = new ArrayList<String>();
		for (int i = 0; i < subOrderCount; i++) {
			lastNum++;
			String orderSn = beforeStr + StringUtils.leftPad("" + (lastNum % 1000), 3, "0");
			subOrderSnList.add(orderSn);
		}
		return subOrderSnList;
	}

	@Override
	public List<String> getRootSubOrderSnList(String mainOrderSn, int subOrderCount) throws TradeException {
		//取主单流水号前25位和后3位
		String beforeStr = mainOrderSn.substring(0, 25);
		String lastStr = mainOrderSn.substring(25);
		int lastNum = Integer.valueOf(lastStr);
		List<String> subOrderSnList = new ArrayList<String>();
		for (int i = 0; i < subOrderCount; i++) {
			lastNum+=10;
			String orderSn = beforeStr + StringUtils.leftPad("" + (lastNum % 1000), 3, "0");
			subOrderSnList.add(orderSn);
		}
		return subOrderSnList;
	}

//	@Override
	/*public String getCombOrderSn(String mainOrderSn, int subOrderCount) throws TradeException {
		//取主单流水号前25位和后3位
		String beforeStr = mainOrderSn.substring(0, 25);
		String lastStr = mainOrderSn.substring(25);
		int lastNum = Integer.valueOf(lastStr);
		String combOrderSn = "";
		lastNum += 10;
		combOrderSn = beforeStr + StringUtils.leftPad("" + (lastNum % 1000), 3, "0");

		
		return combOrderSn;
	}*/

	private String genRandomNumber(int count){
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<count;i++){
        	int num = r.nextInt(str.length());
        	if(i==0){
        		num = r.nextInt(9);
        	}
            
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

}
