package com.mockuai.tradecenter.core.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;  
import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.NamedNodeMap;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mockuai.tradecenter.common.domain.LogisticsCompanyDTO;

public class LogisticsCompanyUtil {
	private static Logger log = LoggerFactory.getLogger(LogisticsCompanyUtil.class);
	
	public static List<LogisticsCompanyDTO> getLogisticsCompany(){
		
		List<LogisticsCompanyDTO> result = new ArrayList<LogisticsCompanyDTO>();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File("src/main/resources/LogisticsCompany.xml"));  
			//获得根元素结点  
			Element root = doc.getDocumentElement();  
//			NodeList companys = root.getChildNodes();
			
			 NodeList companys = doc.getElementsByTagName("company");  
//			   for(int i = 0; i < list.getLength(); i++)  
//		        { 
//		            
//		        	NamedNodeMap map = list.item(i).getAttributes();
//		        	
////		        	map.getLength()
//		        	 //获得该元素的每一个属性  
//	                Attr attr = (Attr)map.item(i);  
//	                  
//	                String attrName = attr.getName();  
//	                String attrValue = attr.getValue();  
//	                  
//	                System.out.print(" " + attrName + "=\"" + attrValue + "\"");  
//		        }
			for(int i=0;i<companys.getLength();i++){
				
				Node node = companys.item(i);
				
				NamedNodeMap map = node.getAttributes();
				
			    Attr id = (Attr)map.item(0);  
			    String idValue = id.getValue();  
			    
			    Attr name = (Attr)map.item(1);  
			    String nameValue = name.getValue();  
			    
			    LogisticsCompanyDTO company = new LogisticsCompanyDTO();
			    company.setId(Integer.valueOf(idValue));
			    company.setName(nameValue);
			    
			    result.add(company);
			}
		} catch (NumberFormatException e) {
			log.error("LogisticsCompanyUtil", e);
		} catch (ParserConfigurationException e) {
			log.error("LogisticsCompanyUtil", e);
		} catch (SAXException e) {
			log.error("LogisticsCompanyUtil", e);
		} catch (IOException e) {
			log.error("LogisticsCompanyUtil", e);
		}
		
		return result;
	}
}
