package com.vistora.crawler.service;

import com.vistora.crawler.model.TableMetadata;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ModelGeneratorService {

    private static final String OUTPUT_DIR = "src/main/java/com/vistora/crawler/generated/";

    public void generateModels(List<TableMetadata> tables) {
        for (TableMetadata table : tables) {
            generateModel(table);
        }
    }

    private void generateModel(TableMetadata table) {
        String className = toCamelCase(table.getTableName(), true);
        StringBuilder sb = new StringBuilder();
        sb.append("package com.vistora.crawler.generated;\n\n");
        sb.append("public class ").append(className).append(" {\n\n");

        // Fields
        table.getColumns().forEach(col -> {
            sb.append("    private ").append(mapSqlTypeToJava(col.getType()))
              .append(" ").append(toCamelCase(col.getName(), false)).append(";\n");
        });

        sb.append("\n");

        // Getters & Setters
        table.getColumns().forEach(col -> {
            String fieldType = mapSqlTypeToJava(col.getType());
            String fieldName = toCamelCase(col.getName(), false);
            String methodName = toCamelCase(col.getName(), true);

            // Getter
            sb.append("    public ").append(fieldType).append(" get").append(methodName).append("() {\n")
              .append("        return ").append(fieldName).append(";\n")
              .append("    }\n\n");

            // Setter
            sb.append("    public void set").append(methodName).append("(").append(fieldType)
              .append(" ").append(fieldName).append(") {\n")
              .append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n")
              .append("    }\n\n");
        });

        sb.append("}\n");

        try (FileWriter writer = new FileWriter(OUTPUT_DIR + className + ".java")) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toCamelCase(String input, boolean capitalizeFirst) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = capitalizeFirst;
        for (char c : input.toCharArray()) {
            if (c == '_' || c == ' ') {
                nextUpper = true;
            } else {
                sb.append(nextUpper ? Character.toUpperCase(c) : Character.toLowerCase(c));
                nextUpper = false;
            }
        }
        return sb.toString();
    }

    private String mapSqlTypeToJava(String sqlType) {
        sqlType = sqlType.toUpperCase();
        switch (sqlType) {
            case "INT":
            case "INTEGER":
            case "SMALLINT":
                return "int";
            case "BIGINT":
                return "long";
            case "FLOAT":
            case "REAL":
                return "float";
            case "DOUBLE":
            case "DECIMAL":
            case "NUMERIC":
                return "double";
            case "BOOLEAN":
                return "boolean";
            case "DATE":
                return "java.sql.Date";
            case "TIMESTAMP":
                return "java.sql.Timestamp";
            default:
                return "String";
        }
    }
}
