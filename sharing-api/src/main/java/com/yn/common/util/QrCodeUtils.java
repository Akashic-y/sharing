package com.yn.common.util;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.ws.commons.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * 二维码工具类
 *
 * @Author kzh
 * @Description
 * @Date 19-4-1 下午4:50
 */
public class QrCodeUtils {

    private static final Logger log = LoggerFactory.getLogger(QrCodeUtils.class);

    /**
     * 创建二维码
     *
     * @param content 二维码封装内容
     * @param logo    中心logo（未支持）
     * @param weight  宽度
     * @param height  高度
     * @return
     */
    public static BufferedImage createImage(String content, String logo, Integer weight, Integer height) {

        /**
         * 1、如果高度和宽度都有指定，使用指定的高宽
         * 2、如果高宽都未指定，使用默认的高宽300x300
         * 3、只指定了高宽中的一个，则高宽相等
         */
        if (weight != null && height != null) {

        } else if (weight == null && height == null) {
            weight = 300;
            height = 300;
        } else {
            weight = (weight == null ? height : weight);
            height = (height == null ? weight : height);
        }
        // 相关设置
        HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
        // L(7%) M(15%) Q(25%) H(30%)
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 二维码边界空白大小1,2,3,4，默认4，最大
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, weight, height, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            return image;

        } catch (WriterException e) {
            log.error("生成二维码失败, 错误信息:{}", JSON.toJSONString(e));
            throw new RuntimeException("生成二维码失败");
        }
    }

    /**
     * 输出二维码到流中
     * 在本项目中输出到流中会出现换行，导致输出乱码，原因不详，建议使用base64输出形式{@link QrCodeUtils#writeToBase64(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)}
     *
     * @param content
     * @param logo
     * @param stream
     * @param weight
     * @param height
     */
    public static void writeToStream(String content, String logo, OutputStream stream, Integer weight, Integer height) {
        try {
            BufferedImage image = createImage(content, logo, weight, height);
            // 输出图片为png格式
            ImageIO.write(image, "png", stream);
        } catch (IOException e) {
            log.error("生成二维码失败, 写入二维码到流失败，错误信息:{}", JSON.toJSONString(e));
            throw new RuntimeException("生成二维码失败");
        }
    }

    /**
     * 以base64形式输出二维码
     *
     * @param content
     * @param logo
     * @param weight
     * @param height
     * @return
     */
    public static String writeToBase64(String content, String logo, Integer weight, Integer height) {
        try {
            BufferedImage image = createImage(content, logo, weight, height);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 输出图片为png格式
            ImageIO.write(image, "png", bos);
            return "data:image/png;base64," + Base64.encode(bos.toByteArray());
        } catch (IOException e) {
            log.error("生成二维码失败, 二维码转base64失败，错误信息:{}", JSON.toJSONString(e));
            throw new RuntimeException("生成二维码失败");
        }
    }
}
