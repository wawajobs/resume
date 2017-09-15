package com.resume.files;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

public abstract class BaseFile {
    /** 日志记录器*/
    private static Logger logger = LoggerFactory.getLogger(BaseFile.class);
    
    private String fileName;
    
    private String absoluteDir;
    private String relativeDir;
    
    public BaseFile(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * 
     * 保存文件
     * @param is 文件输入流
     * @return 通过nginx转发或者软链实现的文件访问地址的后半段，如：/files/homework/feosd09234jdvsef.jpg
     * @throws IOException
     */
    public String saveFile(InputStream is) throws IOException{
        relativeDir = getModulePath() + calculatePath();
        FileUtils.copyInputStreamToFile(is, new File(PropertyHolder.returnBaseFiles() + relativeDir + fileName));
        absoluteDir = PropertyHolder.returnBaseFiles() + relativeDir;
        return relativeDir + fileName;
    }
    
    private String calculatePath() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String date = String.valueOf(calendar.get(Calendar.DATE));
        
        return year + "/" + month + "/" + date + "/";
    }
    
    public String getAbsoluteFilePath() {
        return absoluteDir + fileName;
    }
    
    public static String getAbsoluteFilePath(String relativeFilePath) {
        if (StringUtils.isBlank(relativeFilePath)) {
            throw new IllegalArgumentException("数据库表中文件路径不能为空");
        }
        return PropertyHolder.returnBaseFiles() + relativeFilePath;
    }

    /**
     * 生成缩略图方法
     * @param srcImageName 原图名称
     * @return 通过nginx转发或者软链实现的文件访问地址的后半段，如：/files/homework/feosd09234jdvsef.jpg
     */
    public String saveThumbnail() {
        if (StringUtils.isBlank(fileName)) {
            logger.info("fileName is empty");
            return "";
        }
        if (StringUtils.isBlank(absoluteDir)) {
            logger.info("absoluteDir is empty");
            return "";
        }
        // 缩略图名称
        String thumbnailName = getThumbnailName(fileName);
        // 缩略图后缀，沒有.
        String suffix = getThumbnailSuffix(fileName);
        File t_file = new File(absoluteDir);// 上传图片路径由配置文件读取
        if (!t_file.exists()) {
            t_file.mkdirs();
        }

        try {
            File fi = new File(absoluteDir + fileName); // 大图文件
            File fo = new File(absoluteDir + thumbnailName); // 将要转换出的小图文件
            int nw = 194;
            /*
             * AffineTransform 类表示 2D 仿射变换，它执行从 2D 坐标到其他 2D
             * 坐标的线性映射，保留了线的“直线性”和“平行性”。可以使用一系 列平移、缩放、翻转、旋转和剪切来构造仿射变换。
             */
            AffineTransform transform = new AffineTransform();
            BufferedImage bis = ImageIO.read(fi); // 读取图片
            int w = bis.getWidth();
            int h = bis.getHeight();
            // double scale = (double)w/h;
            int nh = (nw * h) / w;
            double sx = (double) nw / w;
            double sy = (double) nh / h;
            transform.setToScale(sx, sy); // setToScale(double sx, double sy)
                                            // 将此变换设置为缩放变换。
            /*
             * AffineTransformOp类使用仿射转换来执行从源图像或 Raster 中 2D 坐标到目标图像或 Raster 中 2D
             * 坐标的线性映射。所使用的插值类型由构造方法通过 一个 RenderingHints 对象或通过此类中定义的整数插值类型之一来指定。
             * 如果在构造方法中指定了 RenderingHints 对象，则使用插值提示和呈现
             * 的质量提示为此操作设置插值类型。要求进行颜色转换时，可以使用颜色 呈现提示和抖动提示。 注意，务必要满足以下约束：源图像与目标图像
             * 必须不同。 对于 Raster 对象，源图像中的 band 数必须等于目标图像中 的 band 数。
             */
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            /*
             * TYPE_3BYTE_BGR 表示一个具有 8 位 RGB 颜色分量的图像， 对应于 Windows 风格的 BGR
             * 颜色模型，具有用 3 字节存 储的 Blue、Green 和 Red 三种颜色。
             */
            ato.filter(bis, bid);
            ImageIO.write(bid, suffix, fo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "";
        }

        return  PropertyHolder.returnBaseFiles() + relativeDir + thumbnailName;
    }

    private static final String defaultFormat = "png";
    
    /**
     * 获取缩略图后缀
     * @param srcImageName
     * @return
     */
    private String getThumbnailSuffix(String srcImageName) {
        String suffix = "";
        if(StringUtils.isBlank(srcImageName) || srcImageName.indexOf(".") == -1){
            logger.info("srcImageName is empty");
            return defaultFormat;
        }
        
        suffix = srcImageName.substring(srcImageName.lastIndexOf(".")+1);
        return suffix;
    }
    
    /**
     * 获取缩略图名称
     * 
     * @param srcImageName
     * @return
     */
    private String getThumbnailName(String srcImageName) {
        String newName = "";
        if (srcImageName.indexOf(".") == -1) {
            newName = srcImageName + "_thumbnail." + defaultFormat;
        } else {
            String prefix = srcImageName.substring(0, srcImageName.lastIndexOf("."));
            String suffix = srcImageName.substring(srcImageName.lastIndexOf("."));
            newName = prefix + "_thumbnail" + suffix;
        }

        return newName;
    }
    
    /**
     * 功能模块所对应的路径，不需要"/"开头
     * @return
     */
    protected abstract String getModulePath();
    
    @Component
    public static class PropertyHolder implements ApplicationContextAware {
        
        @Value("${baseFiles}") 
        private String baseFiles;
        
        private static ApplicationContext ctx;
        @Override
        public void setApplicationContext(ApplicationContext appContext) throws BeansException {
            ctx = appContext;
        }

        public String getBaseFiles() {
            return baseFiles;
        }


        public static String returnBaseFiles() {
            return ((PropertyHolder)ctx.getBean(PropertyHolder.class)).getBaseFiles();
        }
        
    }
}

