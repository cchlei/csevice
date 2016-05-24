package com.trgis.trmap.hstore.service.query;

import java.util.List;

import com.trgis.trmap.hstore.model.EntMapProperties;

/**
 * postgresql 原生翻页支持 后期加入BTree索引提高查询性能
 * 
 * limit [pagesize] offset [pagesize*pageno] 分页单位和数据偏移量
 * 
 * @author zhangqian
 *
 */
public class Page {

	// 当前页
	private int pageno;

	// 分页单位
	private int pagesize;

	// 第一页
	private int firstPage = 1;

	// 最后一页
	private int lastPage;

	// 上一页
	private int frontPage;

	// 下一页
	private int nextPage;

	// 总页数
	private int pagecount;

	// 总记录数
	private int recordCount;

	private List<?> resultList;

	public Page(int pageno, int pagesize) {
		super();
		this.pageno = pageno;
		this.pagesize = pagesize;
	}

	/**
	 * 刷新数据
	 */
	public void flush() {
		// 如果当前页小于1则重置当前页为1
		if (pageno <= 1) {
			pageno = 1;
		}
		// 总页数
		pagecount = (recordCount % pagesize) == 0 ? recordCount / pagesize : recordCount / pagesize + 1;
		if(pageno > pagecount){
			pageno = pagecount;
		}
		// 最后一页
		lastPage = pagecount;

		// 前一页
		if (pageno > 1) {
			frontPage = pageno - 1;
		} else if (pageno <= 1) {
			pageno = 1;
			frontPage = 1;
		}

		// 下一页
		if (pageno == lastPage) {
			nextPage = lastPage;
		} else {
			nextPage = pageno + 1;
		}

	}

	private Object getOffset() {
		return pagesize * (pageno - 1);
	}

	public String pageSql() {
		flush();
		return String.format(" limit %d offset %d ", this.pagesize, getOffset());
	}

	@Override
	public String toString() {
		return String.format("首页[%d] 上一页[%d] 当前页[%d] 下一页[%d] 尾页 [%d]", this.firstPage, this.frontPage, this.pageno,
				this.nextPage, this.lastPage);
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getFrontPage() {
		return frontPage;
	}

	public void setFrontPage(int frontPage) {
		this.frontPage = frontPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

	public static void main(String[] args) {
		System.out.println(30 % 10);
	}

}
