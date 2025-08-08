package com.vistora.crawler.model;

import java.util.ArrayList;
import java.util.List;

public class TableMetadata {
    private String tableName;
    private List<ColumnMetadata> columns = new ArrayList<>();
    private List<String> primaryKeys = new ArrayList<>();
    private List<ForeignKeyMetadata> foreignKeys = new ArrayList<>();
    
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<ColumnMetadata> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnMetadata> columns) {
		this.columns = columns;
	}
	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}
	public void setPrimaryKeys(List<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	public List<ForeignKeyMetadata> getForeignKeys() {
		return foreignKeys;
	}
	public void setForeignKeys(List<ForeignKeyMetadata> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

   
}
