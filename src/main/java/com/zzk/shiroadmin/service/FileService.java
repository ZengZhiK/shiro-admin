package com.zzk.shiroadmin.service;

import com.zzk.shiroadmin.model.entity.SysFile;
import com.zzk.shiroadmin.model.vo.req.FilePageReqVO;
import com.zzk.shiroadmin.model.vo.resp.PageVO;
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

    /**
     * 分页查询文件数据
     *
     * @param vo
     * @param userId
     * @return
     */
    PageVO<SysFile> pageInfo(FilePageReqVO vo, String userId);
}
