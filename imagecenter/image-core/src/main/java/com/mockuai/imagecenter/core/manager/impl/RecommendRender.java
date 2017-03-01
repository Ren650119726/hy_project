package com.mockuai.imagecenter.core.manager.impl;


import com.alibaba.simpleimage.render.DrawTextItem;
import com.alibaba.simpleimage.render.DrawTextParameter;
import com.google.common.collect.Lists;
import com.mockuai.imagecenter.core.domain.BizColType;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.BaseRender;
import com.mockuai.imagecenter.core.manager.QRCodeManager;
import com.mockuai.imagecenter.core.util.KeyGenerator;
import org.springframework.beans.BeanUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 推荐二维码 冠生
 * on 2016/5/25.
 */
public class RecommendRender extends BaseRender {


     String yahei =  "Microsoft YaHei";

    public RecommendRender(String id, List<QrcodeConfigDetailDO> allDetailDOList,String content, String bgImage) throws IOException, ImageException {
        super(id, allDetailDOList,  content,bgImage);
    }

    @Override
    protected int getImageType() {
        return BufferedImage.TYPE_INT_RGB;
    }

    @Override
    public String getKey() {
        return KeyGenerator.generatorKey(QRCodeManager.RECOMMEND, id);

    }
    public void renderRecommend(String userName) throws FileNotFoundException {
        QrcodeConfigDetailDO config =  getQrcodeConfigDetailDO(BizColType.USER_NAME);
        DrawTextItem item1 =  addCoordText(userName,config);

        Font yaHeiFont = new Font(yahei,Font.PLAIN
                , 33);
        Font defaultFont = item1.getFont();
        Font userNameFont = new Font(config.getFontFamily(),Font.PLAIN,config.getFontSize());

        boolean canAllDisplay = true;
        //检查汉仪晨妹子字体能否正常显示文字
        for(int i = 0 ; i < userName.toCharArray().length;i++ ){
            char item = userName.charAt(i) ;
             if( !userNameFont.canDisplay( item ) ){
                canAllDisplay = false;
             }
        }
        //能正常显示
        if(canAllDisplay){
            List<DrawTextItem> data = new ArrayList<>();
            data.add( addCoordText(userName,config)) ;
            textParameter = new DrawTextParameter(data);
        }else{
            //包含不能正常显示的文字
            textParameter = new DrawTextParameter( calculateMultipleFont(userName,config,defaultFont,yaHeiFont));
        }

        drawText();
        //重新绘制 降低png图片品质
        imageRender.repaints(new FileOutputStream(getTmpFile(QRCodeManager.RECOMMEND)));
    }

    private List<DrawTextItem> calculateMultipleFont(String text , QrcodeConfigDetailDO config,
                                                     Font defaultFont ,Font fixedFont){
        List<DrawTextItem> data =   Lists.newArrayList();
        LinkedList<QrcodeConfigDetailDO> detailDOList =   Lists.newLinkedList();
        for(int i = 0 ; i < text.toCharArray().length;i++ ){
            char item = text.charAt(i) ;
            QrcodeConfigDetailDO itemWidth = new QrcodeConfigDetailDO();
            int width  = 0 ;
            BeanUtils.copyProperties(config,itemWidth);
            if( defaultFont.canDisplay( item ) ){
                width = (int) Math.ceil(  computeFontWidth(item+"",defaultFont));

            }
            if(!defaultFont.canDisplay(text.charAt(i))){
                width = (int) Math.ceil(  computeFontWidth(item+"",fixedFont));
                itemWidth.setFontFamily(yahei);
                itemWidth.setFontSize(25);
            }
            QrcodeConfigDetailDO lastItem;
            if(detailDOList.isEmpty()){
                lastItem = config;
            }else{
                lastItem   =  detailDOList.getLast();
            }
            itemWidth.setWidth(width);
            if(lastItem.getWidth() == null){
                lastItem.setWidth(0);
            }
            itemWidth.setX(lastItem.getX() + lastItem.getWidth());
            detailDOList.add(itemWidth);
            data.add(addCoordText(item + "", itemWidth));
        }
        return data;
    }

    private double computeFontWidth(String text,Font textFont){
        Graphics2D g2 = imageRender.getGraphics2D() ;

        g2.setFont(textFont);
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D rc = fm.getStringBounds(text, g2);
        return rc.getWidth();
    }



}
