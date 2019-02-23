package cn.qx.common.utils;

import java.util.List;

import cn.qx.common.vo.PageObject;

public class PageUtils<T> {
	/**
	 * 泛型类
	 * 在泛型方法的左边加上<T>
	 * @param rowCount
	 * @param pageSize
	 * @param pageCurrent
	 * @param records
	 * @return
	 */
    public static <T>PageObject<T> newPageObject(
    		Integer rowCount,
    		Integer pageSize,
    		Integer pageCurrent,
    		List<T> records) {
    	PageObject<T> po = new PageObject<>();
		po.setPageCount((rowCount-1)/pageSize+1);
		po.setPageCurrent(pageCurrent);
		po.setPageSize(pageSize);
		po.setRecords(records);
		po.setRowCount(rowCount);
		return po;
    }
}
