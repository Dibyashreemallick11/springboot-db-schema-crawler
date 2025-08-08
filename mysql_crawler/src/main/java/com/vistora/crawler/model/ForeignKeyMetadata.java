package com.vistora.crawler.model;

public class ForeignKeyMetadata {
    private String columnName;
    private String referencedTableName;
    private String referencedColumnName;
    
   
    public ForeignKeyMetadata(String columnName, String referencedTableName, String referencedColumnName) {
   
		this.columnName = columnName;
		this.referencedTableName = referencedTableName;
		this.referencedColumnName = referencedColumnName;
	}
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getReferencedTableName() {
		return referencedTableName;
	}

	public void setReferencedTableName(String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}

	public String getReferencedColumnName() {
		return referencedColumnName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}

	

   
}
