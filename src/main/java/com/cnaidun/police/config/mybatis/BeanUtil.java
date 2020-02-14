package com.cnaidun.police.config.mybatis;



import java.util.List;

/***
 * @author dongyin
 * 2019-5-30
 */
public class BeanUtil {
	public static <T> PagedResult<T> toPagedResult(List<T> datas, PageRequest pageRequest) {
		PagedResult<T> result = new PagedResult<T>();
		if (pageRequest != null) {
			result.setPageNo(pageRequest.getCurrent());
			result.setPageSize(pageRequest.getSize());
			result.setDataList(datas);
			result.setTotal(pageRequest.getTotal());
			result.setPages(pageRequest.getPages());
		} else {
			result.setPageNo(1);
			result.setPageSize(datas.size());
			result.setDataList(datas);
			result.setTotal(datas.size());
		}
		return result;
	}
}
