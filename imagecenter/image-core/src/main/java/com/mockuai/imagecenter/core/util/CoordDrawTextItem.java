package com.mockuai.imagecenter.core.util; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.alibaba.simpleimage.render.DrawTextItem;

import java.awt.*;

/**
 * Created by hy on 2016/5/23.
 */
public class CoordDrawTextItem extends DrawTextItem {
    // 主要的文本占图片宽度的百分比,比如0.85,0.95
    protected float textWidthPercent;

    protected int xFactor;
    protected int yFactor;
    /**
     * @param text
     * @param fontColor
     * @param fontShadowColor
     * @param font
     * @param minFontSize
     * @param textWidthPercent
     */
    public CoordDrawTextItem(String text, Color fontColor, Color fontShadowColor, Font font, int minFontSize,
                             float textWidthPercent, int xFactor, int yFactor){
        super(text, fontColor, fontShadowColor, font, minFontSize);
        this.textWidthPercent = textWidthPercent;
        this.xFactor = xFactor;
        this.yFactor = yFactor;
    }

    /* (non-Javadoc)
     * @see com.alibaba.simpleimage.render.DrawTextItem#drawText(java.awt.Graphics2D, int, int)
     */
    @Override
    public void drawText(Graphics2D graphics, int width, int height) {
      

        graphics.setFont(getFont());
        graphics.setColor(fontColor);
        graphics.drawString(text,  xFactor, yFactor);
    }

}
