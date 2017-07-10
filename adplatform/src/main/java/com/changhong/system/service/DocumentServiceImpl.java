package com.changhong.system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * User: Jack Wang
 * Date: 16-9-26
 * Time: 下午5:31
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

    @Value("${application.file.upload.path}")
    private String baseStorePath;

    public void afterPropertiesSet() throws Exception {
        Assert.hasText(baseStorePath, "the basic store path not configure");
    }

}
