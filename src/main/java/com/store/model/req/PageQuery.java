package com.store.model.req;

/**
 * 查询参数
 */
public class PageQuery {
	private Integer offset;
	// 每页条数
	private Integer limit;
	
	private Integer page;

	/*public Query(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		this.offset = Integer.parseInt(params.get("offset").toString());
		this.limit = Integer.parseInt(params.get("limit").toString());
		this.put("offset", offset);
		this.put("page", offset / limit + 1);
		this.put("limit", limit);
	}*/

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		if(this.limit != null && this.offset != null) {
			page=offset / limit + 1;
			return page;
		}
		return 1;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
}
