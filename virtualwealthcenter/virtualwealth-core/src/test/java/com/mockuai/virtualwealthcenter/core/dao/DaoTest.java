package com.mockuai.virtualwealthcenter.core.dao; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.hanshu.imagecenter.client.ImageClient;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossUserAuthonDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UserAuthonQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.manager.CacheManager;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hy on 2016/5/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DaoTest  {

	@Autowired
	private UserAuthonAppManager userAuthonAppManager;

	//    @Resource
	//    private static CacheManager cacheManager;

	@Test
	public void testWithdrawalItem() throws VirtualWealthException{
		//       WithdrawalsItemDO itemDO  = withdrawalsItemDAO.getWithdrawalItem("8cd3d3ef-0e08-46e4-bd0f-913f1516c574");
		//       System.out.println(itemDO+"");


		//    	WealthAccountQTO a =  new WealthAccountQTO();
		//    	a.setUserId((long)420515);
		//    	 List<WealthAccountDO> wealthAdqdq = wealthAccountDAO.queryWealthAccount(a);
		//       System.out.println();
		//    
		//	com.mockuai.imagecenter.common.api.action.Response<ImageDTO> imagestr = imageClient.addRecommendImage((long) 1, null,"27c7bc87733c6d253458fa8908001eef");
		//	System.out.println("11");
		UserAuthonQTO userAuthonQTO = new UserAuthonQTO();

		//userAuthonQTO.setOffset(5);
		//System.out.println("12");
		//userAuthonQTO.setCount(20);
		//List<BossUserAuthonDTO> userAuthonDTOs =  userAuthonAppManager.selectUserAuthon(userAuthonQTO); 
		//System.out.println(userAuthonDTOs);
    	UserAuthonAppDO userAuthonAppDO = new UserAuthonAppDO();
    	userAuthonAppDO.setUserId(123456l);
    	userAuthonAppDO.setAuthonMobile("18888888888");
    	userAuthonAppDO.setAuthonRealname("123");
		//    	userAuthonAppDO.setAuthonStatus(1);
		//		userAuthonAppDO.setId((long)90);
		//		System.out.println(userAuthonAppDO);
    	Long id = userAuthonAppManager.addUserAuthon(userAuthonAppDO);
    	System.out.println(id);
//		MopUserAuthonAppDTO user=userAuthonAppManager.selectAuditStatus(1842113l);
//		System.out.println(user);
		

		//		cacheManager.set("zhongshaoxiang123", 1000*60*20, "nimeia");
		//		System.out.println(cacheManager.get("zhongshaoxiang123"));
		//		MopUserAuthonAppDTO userinfo = userAuthonAppManager.selectMopUserAuthonByPersonalId("341221198908204872");
		//		if(userinfo != null){
		//			System.out.println("123");
		//		}
		//		List<Long> userlist = new ArrayList<Long>();
		//		userlist.add(Long.valueOf(1841258));
		//		userlist.add(Long.valueOf(1841984));
		//		List<MopUserAuthonAppDTO> userinfo1 = userAuthonAppManager.selectMopUserAuthonList(userlist);
		//		if(userinfo1 != null){
		//			System.out.println("1234");
		//		}

	}
	@Test
	public void testAuthon() {
		MopUserAuthonAppQTO mopUserAuthonAppQTO=new MopUserAuthonAppQTO();
		//mopUserAuthonAppQTO.setAuthonPersonalid("330481198801244826");
		mopUserAuthonAppQTO.setAuthonRealname("朱海洁");
		try {
			List<MopUserAuthonAppDTO> mopUserAuthonAppDTOlist=userAuthonAppManager.selectUserAuthonByQto(mopUserAuthonAppQTO);
			
		} catch (VirtualWealthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
