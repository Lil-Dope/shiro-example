package com.cnaidun.police.config.mybatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页工具类
 * @author dongyin
 * 2019-05-30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagedResult<T>{
    private static final long serialVersionUID = 1L;
    /**
     * 数据
     */
    private List<T> dataList;
    /**
     * 当前页
     */
    private long pageNo;
    /**
     * 条数
     */
    private long pageSize;
    /**
     * 总条数
     */
    private long total;
    /**
     * 总页面数目
     */
    private long pages;
}
