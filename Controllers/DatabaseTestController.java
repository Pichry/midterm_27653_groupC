package com.smartsalon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DatabaseTestController - Test database connection and tables
 */
@RestController
@RequestMapping("/api/db-test")
public class DatabaseTestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/connection")
    public ResponseEntity<Map<String, Object>> testConnection() {
        Map<String, Object> result = new HashMap<>();
        
        try (Connection connection = dataSource.getConnection()) {
            result.put("status", "✅ DATABASE CONNECTED!");
            result.put("database", connection.getCatalog());
            result.put("url", connection.getMetaData().getURL());
            
            // Check if tables exist
            List<String> tables = new ArrayList<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables " +
                     "WHERE table_schema = 'public' ORDER BY table_name")) {
                
                while (rs.next()) {
                    tables.add(rs.getString("table_name"));
                }
            }
            
            result.put("tables", tables);
            result.put("tableCount", tables.size());
            
            if (tables.contains("provinces") && tables.contains("users")) {
                result.put("tablesStatus", "✅ TABLES EXIST!");
            } else {
                result.put("tablesStatus", "❌ TABLES MISSING!");
            }
            
        } catch (Exception e) {
            result.put("status", "❌ DATABASE ERROR!");
            result.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> testData() {
        Map<String, Object> result = new HashMap<>();
        
        try (Connection connection = dataSource.getConnection()) {
            
            // Count provinces
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM provinces")) {
                if (rs.next()) {
                    result.put("provinces", rs.getInt("count"));
                }
            }
            
            // Count districts
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM districts")) {
                if (rs.next()) {
                    result.put("districts", rs.getInt("count"));
                }
            }
            
            // Count villages
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM villages")) {
                if (rs.next()) {
                    result.put("villages", rs.getInt("count"));
                }
            }
            
            // Count users
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM users")) {
                if (rs.next()) {
                    result.put("users", rs.getInt("count"));
                }
            }
            
            result.put("status", "✅ DATA LOADED!");
            
        } catch (Exception e) {
            result.put("status", "❌ DATA ERROR!");
            result.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(result);
    }
}