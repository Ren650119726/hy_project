package com.mockuai.imagecenter.core.manager.impl;


import com.alibaba.simpleimage.render.DrawTextItem;
import com.alibaba.simpleimage.render.DrawTextParameter;
import com.google.common.collect.Lists;
import com.mockuai.imagecenter.core.domain.BizColType;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.BaseRender;
import com.mockuai.imagecenter.core.manager.QRCodeManager;
import com.mockuai.imagecenter.core.util.HttpUtil;
import com.mockuai.imagecenter.core.util.KeyGenerator;
import com.mockuai.imagecenter.core.util.ScaleRender;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *  店铺二维码
 * Created by 冠生
 * on 2016/5/25.
 */
public class ShopRender extends BaseRender {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopRender.class.getName());



    public ShopRender(String id, List<QrcodeConfigDetailDO> allDetailDOList, String content,String bgImage) throws IOException, ImageException {
        super(id, allDetailDOList,content, bgImage );
    }

    @Override
    protected int getImageType() {
        return BufferedImage.TYPE_INT_ARGB;
    }



    @Override
    public String getKey() {
        return KeyGenerator.generatorKey(QRCodeManager.SHOP,id);
    }

    public void render(String avatar,String shopName) throws IOException, ImageException {
        QrcodeConfigDetailDO avatarDetail =    getQrcodeConfigDetailDO(BizColType.AVATAR);

        InputStream inputStream = null;
         //用户没有设置头像  使用默认头像
        if(StringUtils.isBlank(avatar)){
            inputStream = new FileInputStream(avatarDetail.getImgPath());
        }else{
            try {
                inputStream = new ByteArrayInputStream( HttpUtil.downloadFile(avatar));
            }catch (Exception e){
                LOGGER.error("",e);
                LOGGER.info("读取默认头像");
                inputStream = new FileInputStream(avatarDetail.getImgPath());
            }
        }
       Path avatarPath =  Paths.get(folder,QRCodeManager.SHOP,getTime(),getKey()+"_avatar.png");
       createParentDirctory(avatarPath.toString());
       OutputStream os =  Files.newOutputStream(avatarPath);
        //缩放图片
        ScaleRender scaleRender = new ScaleRender(inputStream);
        scaleRender.resize(true,avatarDetail.getWidth(),avatarDetail.getHeight());
        scaleRender.makeShape(20);
        scaleRender.render(os);
        inputStream.close();
        inputStream = Files.newInputStream(avatarPath);
        drawImage(inputStream,getQrcodeConfigDetailDO(BizColType.AVATAR));
        DrawTextItem item1 =  addText(shopName+"二维码",getQrcodeConfigDetailDO(BizColType.WHOSE_QRCODE));
        DrawTextItem item2 =   addText(shopName,getQrcodeConfigDetailDO(BizColType.SHOP_NAME));
        textParameter = new DrawTextParameter(Lists.newArrayList(item1,item2));
        drawText();
        //render();
        //降低png 图片质量
        imageRender.repaints(new FileOutputStream(getTmpFile(QRCodeManager.SHOP)));
        inputStream.close();
        avatarPath.toFile().delete();
    }

}
