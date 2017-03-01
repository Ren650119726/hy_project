package com.mockuai.imagecenter.core.domain;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.render.DrawTextParameter;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.WatermarkParameter;
import com.alibaba.simpleimage.render.WriteParameter;
import com.mockuai.imagecenter.core.util.Watermark;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yindingyu on 16/5/23.
 */
public class MockuaiImageParameter implements Serializable{


    //图片源
    private InputStream inputStream;

    //图片输出流
    private OutputStream outputStream;

    //图片输出格式
    private ImageFormat imageFormat;

    //图片输出参数
    private WriteParameter writeParameter;

    //大小参数
    private ScaleParameter scaleParameter;

    //文字参数
    private DrawTextParameter drawTextParameter;

    //水印参数
    private List<WatermarkParameter> watermarkParameterList;

    private List<Watermark> watermarkList ;


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ImageFormat getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(ImageFormat imageFormat) {
        this.imageFormat = imageFormat;
    }

    public WriteParameter getWriteParameter() {
        return writeParameter;
    }

    public void setWriteParameter(WriteParameter writeParameter) {
        this.writeParameter = writeParameter;
    }

    public ScaleParameter getScaleParameter() {
        return scaleParameter;
    }

    public void setScaleParameter(ScaleParameter scaleParameter) {
        this.scaleParameter = scaleParameter;
    }

    public DrawTextParameter getDrawTextParameter() {
        return drawTextParameter;
    }

    public void setDrawTextParameter(DrawTextParameter drawTextParameter) {
        this.drawTextParameter = drawTextParameter;
    }

    public List<WatermarkParameter> getWatermarkParameterList() {
        return watermarkParameterList;
    }

    public void setWatermarkParameterList(List<WatermarkParameter> watermarkParameterList) {
        this.watermarkParameterList = watermarkParameterList;
    }

    public List<Watermark> getWatermarkList() {
        return watermarkList;
    }

    public void setWatermarkList(List<Watermark> watermarkList) {
        this.watermarkList = watermarkList;
    }
}
