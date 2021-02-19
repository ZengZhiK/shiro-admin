package com.zzk.shiroadmin.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件 业务接口
 *
 * @author zzk
 * @create 2021-02-19 13:55
 */
public interface FileService {
    /**
     * 文件上传
     *
     * @param file
     * @param userId
     * @param type
     */
    String upload(MultipartFile file, String userId, Integer type);

    /**
     * 文件下载
     *
     * @param fileId
     * @param response
     */
    void download(String fileId, HttpServletResponse response);

    /**
     * 文件删除
     *
     * @param fileUrl
     */
    void deleteByFileUrl(String fileUrl);
}
