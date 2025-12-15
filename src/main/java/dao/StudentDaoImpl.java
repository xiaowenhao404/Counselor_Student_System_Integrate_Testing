package dao;

import db.DatabaseConnection;
import entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDaoImpl implements StudentDao {

    /**
     * 安全关闭数据库资源
     */
    private static void closeResources(Connection connection, PreparedStatement ps, ResultSet rs) {
        DatabaseConnection.closeConnection(connection);
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // 资源关闭失败，记录但不抛出异常
                System.err.println("关闭PreparedStatement失败: " + e.getMessage());
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // 资源关闭失败，记录但不抛出异常
                System.err.println("关闭ResultSet失败: " + e.getMessage());
            }
        }
    }

    /**
     * 从ResultSet中提取日期字段，处理null值
     */
    private static java.time.LocalDate extractDateOfBirth(ResultSet rs, String columnName) throws SQLException {
        java.sql.Date sqlDate = rs.getDate(columnName);
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }

    /**
     * 从ResultSet中创建Student对象
     */
    private static Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getString("学生学号"));
        student.setMajorId(rs.getString("专业编号"));
        student.setGradeNumber(rs.getString("年级编号"));
        student.setClassId(rs.getString("班级编号"));
        student.setName(rs.getString("姓名"));
        student.setGender(rs.getString("性别"));
        student.setDateOfBirth(extractDateOfBirth(rs, "出生日期"));
        student.setPhoneNumber(rs.getString("手机号码"));
        student.setPassword(rs.getString("密码"));
        return student;
    }

    @Override
    public Student getStudentByIdAndPassword(String studentId, String password) throws SQLException {
        String sql = "SELECT 学生学号, 专业编号, 年级编号, 班级编号, 姓名, 性别, 出生日期, 手机号码, 密码 FROM 学生 WHERE 学生学号 = ? AND 密码 = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Student student = null;

        try {
            connection = DatabaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, studentId);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                student = mapResultSetToStudent(rs);
            }
        } finally {
            closeResources(connection, ps, rs);
        }
        return student;
    }

    @Override
    public Student getStudentById(String studentId) throws SQLException {
        String sql = "SELECT 学生学号, 专业编号, 年级编号, 班级编号, 姓名, 性别, 出生日期, 手机号码, 密码 FROM 学生 WHERE 学生学号 = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Student student = null;

        try {
            connection = DatabaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                student = mapResultSetToStudent(rs);
            }
        } finally {
            closeResources(connection, ps, rs);
        }
        return student;
    }

    @Override
    public boolean addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO 学生 (学生学号, 专业编号, 年级编号, 班级编号, 姓名, 性别, 出生日期, 手机号码, 密码) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, student.getStudentId());
            ps.setString(2, student.getMajorId());
            ps.setString(3, student.getGradeNumber());
            ps.setString(4, student.getClassId());
            ps.setString(5, student.getName());
            ps.setString(6, student.getGender());
            ps.setDate(7, student.getDateOfBirth() != null ? java.sql.Date.valueOf(student.getDateOfBirth()) : null);
            ps.setString(8, student.getPhoneNumber());
            ps.setString(9, student.getPassword());
            
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    @Override
    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE 学生 SET 专业编号 = ?, 年级编号 = ?, 班级编号 = ?, 姓名 = ?, 性别 = ?, 出生日期 = ?, 手机号码 = ?, 密码 = ? WHERE 学生学号 = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, student.getMajorId());
            ps.setString(2, student.getGradeNumber());
            ps.setString(3, student.getClassId());
            ps.setString(4, student.getName());
            ps.setString(5, student.getGender());
            ps.setDate(6, student.getDateOfBirth() != null ? java.sql.Date.valueOf(student.getDateOfBirth()) : null);
            ps.setString(7, student.getPhoneNumber());
            ps.setString(8, student.getPassword());
            ps.setString(9, student.getStudentId());
            
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    @Override
    public boolean deleteStudent(String studentId) throws SQLException {
        String sql = "DELETE FROM 学生 WHERE 学生学号 = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, studentId);
            
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    @Override
    public int getStudentCountByClassId(String classId) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM 学生 WHERE 班级编号 = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, classId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
                return 0;
            }
        }
    }
}