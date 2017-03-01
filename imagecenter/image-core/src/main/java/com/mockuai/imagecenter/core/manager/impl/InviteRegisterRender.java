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
 * Created by lizg on 2016/11/30.
 */
public class InviteRegisterRender extends BaseRender {

    String yahei =  "Microsoft YaHei";

    public InviteRegisterRender(String id, List<QrcodeConfigDetailDO> allDetailDOList, String content, String bgImage, boolean hasNeedAddLogo) throws IOException, ImageException {
        super(id, allDetailDOList, content, bgImage, hasNeedAddLogo);
    }


    @Override
    protected int getImageType() {
        return BufferedImage.TYPE_INT_RGB;
    }

    @Override
    public String getKey() {
        return KeyGenerator.generatorKey(QRCodeManager.INVITEREGISTER,id);
    }

    public  void renderInviteRegister(String nickName) throws FileNotFoundException {
        QrcodeConfigDetailDO config = getQrcodeConfigDetailDO(BizColType.NICK_NAME);
        DrawTextItem drawTextItem = addCoordText(nickName,config);
        Font yaHeiFont = new Font(yahei,Font.PLAIN,33);
        Font defaultFont = drawTextItem.getFont();
        Font nickNameFont = new Font(config.getFontFamily(),Font.PLAIN,config.getFontSize());

        boolean canAllDisplay = true;

        //检查汉仪晨妹子字体能否正常显示文字
        for (int i =0;i<nickName.toCharArray().length;i++) {
            char nChar = nickName.charAt(i);
            if (!nickNameFont.canDisplay(nChar)) {
                canAllDisplay = false;
            }
        }

        //能正常显示
        if (canAllDisplay) {
            List<DrawTextItem> data = new ArrayList<>();
            data.add(addCoordText(nickName,config));
            textParameter = new DrawTextParameter(data);
        }else {

            //包含不能正常显示的文字
            textParameter = new DrawTextParameter(calculateMultipleTextFont(nickName,config,defaultFont,yaHeiFont));

        }

        drawText();
        //重新绘制 降低png图片品质
        imageRender.repaints(new FileOutputStream(getTmpFile(QRCodeManager.INVITEREGISTER)));
    }

  private  List<DrawTextItem> calculateMultipleTextFont (String text,QrcodeConfigDetailDO config,
                                                         Font defaultFont,Font fixedFont) {

        List<DrawTextItem> data = Lists.newArrayList();
        LinkedList<QrcodeConfigDetailDO> detailDOList = Lists.newLinkedList();

        for (int i=0;i<text.toCharArray().length;i++) {
               char item = text.charAt(i);
               QrcodeConfigDetailDO itemWidth = new QrcodeConfigDetailDO();
               int width = 0;
            BeanUtils.copyProperties(config,itemWidth);
            if (defaultFont.canDisplay(item)) {
                width =(int) Math.ceil(computeTextFontWidth(item+"",defaultFont));

            }

            if(!defaultFont.canDisplay(text.charAt(i))){
                width = (int) Math.ceil(computeTextFontWidth(item+"",fixedFont));
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

  private double computeTextFontWidth(String text,Font textFont) {
      Graphics2D graphics2D = imageRender.getGraphics2D();
      graphics2D.setFont(textFont);
      FontMetrics fontMetrics = graphics2D.getFontMetrics();
      Rectangle2D rectangle2D = fontMetrics.getStringBounds(text,graphics2D);
      return rectangle2D.getWidth();
  }
}
