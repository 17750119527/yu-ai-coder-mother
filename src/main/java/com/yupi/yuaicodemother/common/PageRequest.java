package com.yupi.yuaicodemother.common;

import lombok.Data;

@Data
public class PageRequest {
    /*
    * 页面大小
    * */
    private int pageSize = 10;

    /*
    * 排序字段
    * */
    private String sortFiled;

    /*
    * 排序顺序（默认降序）
    * */
    private String sortOrder = "descend";
}
