package com.zzk.shiroadmin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件 业务接口
 *
 * @author zzk
 * @create 2021-02-19 13:55
 */
public interface FileService {
    void upload(MultipartFile file, String userId, Integer type);
}
