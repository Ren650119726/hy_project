package com.mockuai.imagecenter.core.manager.impl;


import com.alibaba.simpleimage.render.DrawTextItem;
import com.alibaba.simpleimage.render.DrawTextParameter;
import com.mockuai.imagecenter.core.domain.BizColType;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.BaseRender;
import com.mockuai.imagecenter.core.manager.QRCodeManager;
import com.mockuai.imagecenter.core.util.HttpUtil;
import com.mockuai.imagecenter.core.util.KeyGenerator;
import com.mockuai.imagecenter.core.util.ScaleRender;
import org.springframework.beans.BeanUtils;
import org.springframework.util.FileCopyUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *  商品二维码
 * Created by 冠生
 * on 2016/5/25.
 */
public class ItemRender extends BaseRender {


    public ItemRender(String id, List<QrcodeConfigDetailDO> allDetailDOList, String content, String bgImage,boolean hasAddLogo) throws IOException, ImageException {
        super(id, allDetailDOList,content, bgImage,hasAddLogo );
    }


    @Override
    protected int getImageType() {
        return BufferedImage.TYPE_INT_RGB;
    }


    @Override
    public String getKey() {
        return KeyGenerator.generatorKey(QRCodeManager.ITEM,id);

    }

    public void render(String imageUrl,String shopName ,String itemName,String price) throws IOException, ImageException {
        Graphics2D g2 = imageRender.getGraphics2D() ;
        QrcodeConfigDetailDO detailDO =     getQrcodeConfigDetailDO(BizColType.ITEM_NAME);
        QrcodeConfigDetailDO whoseShopDO =      getQrcodeConfigDetailDO(BizColType.WHOSE_SHOP);
        int detailFontWeight  =detailDO.getFontWeight() == null? Font.PLAIN:detailDO.getFontWeight();
        int whoseShopFontWeight  =whoseShopDO.getFontWeight() == null? Font.PLAIN:whoseShopDO.getFontWeight();
        int totalWidth =   imageRender.getBuffImg().getWidth() * (100 - detailDO.getX() )/100;

        String fontFamily = detailDO.getFontFamily();
        if(fontFamily == null){
            //
            fontFamily = "Microsoft YaHei";

        }
        List<DrawTextItem> textItemList = new ArrayList<DrawTextItem>();
        //将图片下载
        InputStream inputStream = new ByteArrayInputStream( HttpUtil.downloadFile(imageUrl));
        Path originItemPath =  Paths.get(folder,QRCodeManager.ITEM,getTime(),getKey()+"_item.png");
        createParentDirctory(originItemPath.toString());
        OutputStream originItemOutputStream =  Files.newOutputStream(originItemPath);
        FileCopyUtils.copy(inputStream,originItemOutputStream);
        Path itemPath =  Paths.get(folder,QRCodeManager.ITEM,getTime(),getKey()+"item_scale.png");

        OutputStream os =  Files.newOutputStream(itemPath);
        ScaleRender scaleRender = new ScaleRender(originItemPath.toFile());
        scaleRender.resize(true,558,558);
        scaleRender.render(os);
        inputStream.close();
        originItemOutputStream.close();
        inputStream = Files.newInputStream(itemPath);
        drawImage(inputStream,getQrcodeConfigDetailDO(BizColType.ITEM_IMAGE));
        //String labelShopName = "长按识别二维码或者扫一扫购买"+shopName+"的推荐好物";
        String labelShopName = "长按识别二维码或者扫一扫购买此商品";
        Font whoseShopFont = new Font(fontFamily,whoseShopFontWeight
                ,whoseShopDO.getFontSize());
        g2.setFont(whoseShopFont);
        FontMetrics fm = g2.getFontMetrics();
        int whoseShopFontLength = getBestFontNumber(totalWidth,fm,g2,labelShopName,labelShopName.length());
        if(whoseShopFontLength == labelShopName.length()){
            DrawTextItem item2 =  addText(labelShopName,whoseShopDO);
            textItemList.add(item2);
        }else{
            String whoseShop1 =  labelShopName.substring(0,whoseShopFontLength);
            int whoseShopFontLength2 = getBestFontNumber(totalWidth,fm,g2,labelShopName.substring(whoseShopFontLength),labelShopName.substring(whoseShopFontLength).length());
            String whoseShop2 = labelShopName.substring(whoseShopFontLength,whoseShopFontLength+whoseShopFontLength2);

            QrcodeConfigDetailDO whoseShopDO2 = new QrcodeConfigDetailDO();
            BeanUtils.copyProperties(whoseShopDO,whoseShopDO2);
            whoseShopDO2.setY(97);
            DrawTextItem item2 =  addText(whoseShop1,whoseShopDO);
            DrawTextItem item3 =  addText(whoseShop2,whoseShopDO2);
            textItemList.add(item2);
            textItemList.add(item3);
        }

        Font fontDetail = new Font(fontFamily,detailFontWeight
                ,detailDO.getFontSize());
        g2.setFont(fontDetail);
        fm = g2.getFontMetrics();
        int fontLength = getBestFontNumber(totalWidth,fm,g2,itemName,itemName.length());
        if(fontLength == itemName.length()){
            DrawTextItem item2 =  addText(itemName,getQrcodeConfigDetailDO(BizColType.ITEM_NAME));
            textItemList.add(item2);
        }else{
            String itemName1 =  itemName.substring(0,fontLength);
            int fontLength2 = getBestFontNumber(totalWidth,fm,g2,itemName.substring(fontLength),itemName.substring(fontLength).length());
            String itemName2 = itemName.substring(fontLength,fontLength+fontLength2);
            QrcodeConfigDetailDO itemDetailDO1 =   getQrcodeConfigDetailDO(BizColType.ITEM_NAME);
            QrcodeConfigDetailDO itemDetailDO2 = new QrcodeConfigDetailDO();
            BeanUtils.copyProperties(itemDetailDO1,itemDetailDO2);
            itemDetailDO2.setY(82);
            DrawTextItem item2 =  addText(itemName1,itemDetailDO1);
            DrawTextItem item3 =  addText(itemName2,itemDetailDO2);
            textItemList.add(item2);
            textItemList.add(item3);
        }
        DrawTextItem item3 =  addText("￥"+price,getQrcodeConfigDetailDO(BizColType.ITEM_PRICE));
        textItemList.add(item3);
        textParameter = new DrawTextParameter(textItemList);
        drawText();
        imageRender.compress(new FileOutputStream(getTmpFile(QRCodeManager.ITEM)),0.3f);
        //render();
        inputStream.close();
        originItemPath.toFile().delete();
        itemPath.toFile().delete();
    }

    private int getBestFontNumber(int totalWidth,FontMetrics fm, Graphics2D g2, String text,int length){

        Rectangle2D rc = fm.getStringBounds(text.substring(0,length), g2);
        double itemNameWidth =   rc.getWidth();
        //比文字宽度要长

        if(totalWidth - itemNameWidth < 30  ){
            length--;
            return getBestFontNumber(totalWidth, fm, g2, text, length);
        }
        return length;
    }

}
