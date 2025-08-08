// package: com.vistora.crawler.service

package com.vistora.crawler.service;

import com.vistora.crawler.config.DatabaseConnector;
import com.vistora.crawler.model.ColumnMetadata;
import com.vistora.crawler.model.ForeignKeyMetadata;
import com.vistora.crawler.model.TableMetadata;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseCrawlerService {

    private final DatabaseConnector dbConnector;

    public DatabaseCrawlerService(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public List<TableMetadata> extractMetadata() {
        List<TableMetadata> tables = new ArrayList<>();

        try (Connection conn = dbConnector.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();

            ResultSet tablesRS = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tablesRS.next()) {
                String tableName = tablesRS.getString("TABLE_NAME");
                TableMetadata table = new TableMetadata();
                table.setTableName(tableName);

                // Extract columns
                ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                while (columns.next()) {
                    table.getColumns().add(new ColumnMetadata(
                            columns.getString("COLUMN_NAME"),
                            columns.getString("TYPE_NAME"),
                            columns.getInt("COLUMN_SIZE"),
                            columns.getInt("NULLABLE") == DatabaseMetaData.columnNullable
                    ));
                }

                // Primary Keys
                ResultSet pkRS = metaData.getPrimaryKeys(null, null, tableName);
                while (pkRS.next()) {
                    table.getPrimaryKeys().add(pkRS.getString("COLUMN_NAME"));
                }

                // Foreign Keys
                ResultSet fkRS = metaData.getImportedKeys(null, null, tableName);
                while (fkRS.next()) {
                    table.getForeignKeys().add(new ForeignKeyMetadata(
                            fkRS.getString("FKCOLUMN_NAME"),
                            fkRS.getString("PKTABLE_NAME"),
                            fkRS.getString("PKCOLUMN_NAME")
                    ));
                }

                tables.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }

    public List<TableMetadata> crawlDatabase() {
        return extractMetadata(); // ✅ Now returns actual metadata
    }


}

