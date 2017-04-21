package com.workful.templates;

import java.util.ArrayList;

public class SearchObj {
	private ArrayList<SearchResult> list;
	private int rows;
	public ArrayList<SearchResult> getList() {
		return list;
	}
	public void setList(ArrayList<SearchResult> list) {
		this.list = list;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
}
