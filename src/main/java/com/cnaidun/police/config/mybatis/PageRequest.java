package com.cnaidun.police.config.mybatis;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * @uthor: dongyin
 * @date: 2019/05/30
 */
public class PageRequest extends Pagination {

    public PageRequest(int current, int size) {
        this(current, size, false);
    }

    public PageRequest(int current, int size, boolean searchCount) {
        this(current, size, searchCount,true);
    }

    public PageRequest(int current, int size, boolean searchCount, boolean openSort) {
        super(current, size, searchCount, openSort);
    }
}
