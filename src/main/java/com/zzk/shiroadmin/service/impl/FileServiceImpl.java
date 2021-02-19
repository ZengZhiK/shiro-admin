package com.zzk.shiroadmin.service.impl;

import com.zzk.shiroadmin.common.exception.BusinessException;
import com.zzk.shiroadmin.common.exception.enums.BusinessExceptionType;
import com.zzk.shiroadmin.mapper.SysFileMapper;
import com.zzk.shiroadmin.model.entity.SysFile;
import com.zzk.shiroadmin.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件 业务实现类
 *
 * @author zzk
 * @create 2021-02-19 13:55
 */
@Service
public class FileServiceImpl implements FileService {
    @Value("${file.path}")
    private String filePath;

    @Value("${file.base-url}")
    private String baseUrl;

    @Autowired
    private SysFileMapper sysFileMapper;

    @Override
    public void upload(MultipartFile file, String userId, Integer type) {
        String originalFilename = file.getOriginalFilename();
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        String fileId = UUID.randomUUID().toString();
        String fileName = fileId + "." + extensionName;
        File destFile = new File(filePath + fileName);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new BusinessException(BusinessExceptionType.FILE_UPLOAD_ERROR);
        }

        String fileUrl = baseUrl + fileName;

        SysFile sysFile = new SysFile();
        sysFile.setId(UUID.randomUUID().toString());
        sysFile.setFileName(fileName);
        sysFile.setOriginalName(originalFilename);
        sysFile.setExtensionName(extensionName);
        sysFile.setCreateId(userId);
        sysFile.setType(type);
        sysFile.setFileUrl(fileUrl);
        sysFile.setSize(FileUtils.byteCountToDisplaySize(file.getSize()));
        int i = sysFileMapper.insertSelective(sysFile);
        if (i != 1) {
            throw new BusinessException(BusinessExceptionType.DATA_ERROR);
        }
    }
}
