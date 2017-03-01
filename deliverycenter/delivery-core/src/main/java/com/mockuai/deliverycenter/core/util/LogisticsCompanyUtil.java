package com.mockuai.deliverycenter.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mockuai.deliverycenter.common.dto.express.LogisticsCompanyDTO;


public class LogisticsCompanyUtil {
	private static Logger log = LoggerFactory.getLogger(LogisticsCompanyUtil.class);
	//工程目录
    private static final String FILE_BASE_DIR  = System.getProperty("user.dir");
    //文件系统分隔符
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    //file url前缀
    private static final String FILE_PROTOCAL  = ResourceUtils.FILE_URL_PREFIX;
    private final static LogisticsCompanyUtil instance = new LogisticsCompanyUtil();
    public static void main(String args[]){
    	List<LogisticsCompanyDTO> list = getLogisticsCompany();
    }
    
    private InputStream loadFile(String filePath) {
		return this.getClass().getResourceAsStream(filePath);
	}
    
    public static Map<String,LogisticsCompanyDTO> getLogisticsCompMaps(){
    	List<LogisticsCompanyDTO> list = getLogisticsCompany();
    	Map<String,LogisticsCompanyDTO> map = new HashMap<String,LogisticsCompanyDTO>();
    	for(LogisticsCompanyDTO dto:list){
    		LogisticsCompanyDTO temp = map.get(dto.getCode());
    		if(null==temp){
    			map.put(dto.getCode(), dto);
    		}
    	}
    	      return map;  
    }
    
       
    
	@SuppressWarnings("unchecked")
	public static List<LogisticsCompanyDTO> getLogisticsCompany(){
		
		List<LogisticsCompanyDTO> result = new ArrayList<LogisticsCompanyDTO>();
		
		try {
			
			InputStream is = instance.loadFile("/LogisticsCompany.xml");
			if (is == null) {
				return Collections.emptyList();
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);  
			//获得根元素结点  
			Element root = doc.getDocumentElement();  
			
			NodeList companys = doc.getElementsByTagName("company");  
			
			for(int i=0;i<companys.getLength();i++){
				
				Node node = companys.item(i);
				
				NamedNodeMap map = node.getAttributes();
				
			    Attr code = (Attr)map.item(0);  
			    String codeValue = code.getValue();  
			    
			    Attr name = (Attr)map.item(1);  
			    String nameValue = name.getValue();  
			    
			    LogisticsCompanyDTO company = new LogisticsCompanyDTO();
			    company.setCode(codeValue);
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
