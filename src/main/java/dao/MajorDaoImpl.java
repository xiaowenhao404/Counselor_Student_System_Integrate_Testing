package dao;

import db.DatabaseConnection;
import entity.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MajorDaoImpl implements MajorDao {

    @Override
    public Map<String, String> getAllMajors() throws SQLException {
        Map<String, String> majors = new LinkedHashMap<>();
        String sql = "SELECT 专业编号, 专业名称 FROM 专业 ORDER BY 专业编号";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                majors.put(rs.getString("专业名称"), rs.getString("专业编号"));
            }
        }
        return majors;
    }
}