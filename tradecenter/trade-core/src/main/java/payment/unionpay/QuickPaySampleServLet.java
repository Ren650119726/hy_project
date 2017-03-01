package payment.unionpay;
import com.mockuai.tradecenter.core.util.JsonUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 名称：支付请求类
 * 功能：servlet方式支付类请求
 * 类属性：
 * 版本：1.0
 * 日期：2011-03-11
 * 作者：中国银联UPOP团队
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */

public class QuickPaySampleServLet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6247574514957274823L;
	static int i= 0;
	public void init() {

	}

	public static Map<String,String> getReqParamMap() {
		//商户需要组装如下对象的数据
		String[] valueVo = new String[]{
				QuickPayConf.version,//协议版本
				QuickPayConf.charset,//字符编码
	            "01",//交易类型
	            "",//原始交易流水号
	            QuickPayConf.merCode,//商户代码
	            QuickPayConf.merName,//商户简称
	            "",//收单机构代码（仅收单机构接入需要填写）
	            "",//商户类别（收单机构接入需要填写）
	            "",//商品URL
	            "物品名称如短袖",//商品名称
	            "3000",//商品单价 单位：分
	            "1",//商品数量
	            "0",//折扣 单位：分
	            "1000",//运费 单位：分
	            new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+(++i),//订单号（需要商户自己生成）
	            "3100",//交易金额 单位：分
	            "156",//交易币种
	            new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),//交易时间
	            "127.0.0.1",//用户IP
	            "张三",//用户真实姓名
	            "",//默认支付方式
	            "",//默认银行编号
	            "300000",//交易超时时间
	            QuickPayConf.merFrontEndUrl,// 前台回调商户URL
				QuickPayConf.merBackEndUrl,// 后台回调商户URL
	            ""//商户保留域
		};
		
		String signType = QuickPayConf.signType;

		
		Map<String,String> reqParam = new QuickPayUtils().createReqParam(valueVo, signType);//跳转到银联页面支付
		System.out.println("response:"+ JsonUtil.toJson(reqParam));
		return reqParam;
		
//		response.setContentType("text/html;charset="+QuickPayConf.charset);
//		response.setCharacterEncoding(QuickPayConf.charset);
//		try {
//			response.getWriter().write(html);
//		} catch (IOException e) {
//
//		}
//		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	
	

}
