package com.mockuai.suppliercenter;

import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import org.junit.Test;

/**
 * Created by duke on 15/12/10.
 */
public class TempTest {
    @Test
    public void test() {
        Element root = new BaseElement("row");
        Element memberNameNode = new BaseElement("MemberName");
        memberNameNode.setText("12345");
        root.add(memberNameNode);
        Element memberNameTypeNode = new BaseElement("MemberNameType");
        memberNameTypeNode.setText("1");
        root.add(memberNameTypeNode);
        Element newAppNode = new BaseElement("new_app");
        newAppNode.setText("1");
        root.add(newAppNode);
        Element originTypeNode = new BaseElement("OriginType");
        originTypeNode.setText("3");
        root.add(originTypeNode);
        Element typeNode = new BaseElement("Type");
        typeNode.setText("1");
        root.add(typeNode);
        Element phoneNum = new BaseElement("phoneNum");
        phoneNum.setText("12345");
        root.add(phoneNum);
        System.out.println(root.asXML());
    }
}
