package com.zzk.shiroadmin.common.utils;

import com.github.pagehelper.Page;
import com.zzk.shiroadmin.model.vo.resp.PageVO;

import java.util.List;

/**
 * @author zzk
 * @create 2021-02-05 19:52
 */
public class PageUtils {
    public static <T> PageVO<T> getPageVO(List<T> list) {
        PageVO<T> result = new PageVO<>();
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            result.setTotalRows(page.getTotal());
            result.setTotalPages(page.getPages());
            result.setPageNum(page.getPageNum());
            result.setCurPageSize(page.getPageSize());
            result.setPageSize(page.size());
            result.setList(page.getResult());
        }
        return result;
    }
}
