package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.common.utils.JodaUtils;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:02
 */
public abstract class Document extends EntityBase {

    private MultipartFile file;
    //上传到服务器的文件名
    private String uploadFileName;
    //存放到服务器的文件名
    private String actualFileName;
    //上传时间
    private DateTime uploadTime;
    //文件大小，保存保存的是B
    private int fileSize;

    public Document() {
    }

    public Document(MultipartFile file) {
        this.file = file;
        this.uploadFileName = file != null ? file.getOriginalFilename() : "";
        String[] tokens = StringUtils.delimitedListToStringArray(uploadFileName, ".");
        this.actualFileName = CHStringUtils.getRandomString(10) + "." + tokens[tokens.length -1];
        this.uploadTime = JodaUtils.currentTime();
        this.fileSize = Long.valueOf(file.getSize()).intValue();
    }

    public void changeFile(MultipartFile file) {
        this.file = file;
        this.uploadFileName = file != null ? file.getOriginalFilename() : "";
        String[] tokens = StringUtils.delimitedListToStringArray(uploadFileName, ".");
        this.actualFileName = CHStringUtils.getRandomString(10) + "." + tokens[tokens.length -1];
        this.uploadTime = JodaUtils.currentTime();
        this.fileSize = Long.valueOf(file.getSize()).intValue();
    }

    /**
     * return size of picture which is K byte
     */
    public static String getFileSize(int fileSize) {
        double howManyMByte = Double.valueOf(fileSize) / 1024;
        return String.format("%.2f", howManyMByte);
    }

    /***********************************************GET/SET******************************************************/

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getActualFileName() {
        return actualFileName;
    }

    public void setActualFileName(String actualFileName) {
        this.actualFileName = actualFileName;
    }

    public DateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(DateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
}
