package dao;

import db.DatabaseConnection;
import entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StudentDaoImpl的单元测试类
 * 
 * 测试类命名：被测试类名 + Test
 * 测试方法命名：test + 方法名（驼峰命名）
 */
@DisplayName("StudentDaoImpl单元测试")
class StudentDaoTest {

    private StudentDaoImpl studentDao;
    
    // 测试数据常量
    private static final String TEST_STUDENT_ID = "TEST001";
    private static final String TEST_STUDENT_ID_2 = "TEST002";
    private static final String TEST_STUDENT_ID_NOT_EXIST = "TEST999";
    private static final String TEST_MAJOR_ID = "8329";  // 从data.sql中获取的有效专业编号
    private static final String TEST_GRADE_NUMBER = "2023";  // 从data.sql中获取的有效年级编号
    private static final String TEST_CLASS_ID = "1";  // 从data.sql中获取的有效班级编号
    private static final String TEST_PASSWORD = "test123";
    private static final String TEST_WRONG_PASSWORD = "wrong123";

    @BeforeEach
    void setUp() {
        // 在每个测试方法执行前初始化
        studentDao = new StudentDaoImpl();
        
        // 准备测试数据：插入一个测试学生
        try {
            insertTestStudent(TEST_STUDENT_ID, "测试学生1", TEST_PASSWORD);
        } catch (SQLException e) {
            // 如果插入失败（可能已存在），忽略错误
            // 这样测试可以独立运行，不依赖执行顺序
        }
    }

    @AfterEach
    void tearDown() {
        // 在每个测试方法执行后清理测试数据
        try {
            deleteTestStudent(TEST_STUDENT_ID);
            deleteTestStudent(TEST_STUDENT_ID_2);
        } catch (SQLException e) {
            // 清理失败不影响测试结果
            System.err.println("清理测试数据失败: " + e.getMessage());
        }
    }

    /**
     * 插入测试学生数据的辅助方法
     */
    private void insertTestStudent(String studentId, String name, String password) throws SQLException {
        String sql = "INSERT INTO 学生 (学生学号, 专业编号, 年级编号, 班级编号, 姓名, 性别, 出生日期, 手机号码, 密码) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, studentId);
            ps.setString(2, TEST_MAJOR_ID);
            ps.setString(3, TEST_GRADE_NUMBER);
            ps.setString(4, TEST_CLASS_ID);
            ps.setString(5, name);
            ps.setString(6, "男");
            ps.setDate(7, java.sql.Date.valueOf(LocalDate.of(2000, 1, 1)));
            ps.setString(8, "13800000000");
            ps.setString(9, password);
            
            ps.executeUpdate();
        }
    }

    /**
     * 删除测试学生数据的辅助方法
     */
    private void deleteTestStudent(String studentId) throws SQLException {
        String sql = "DELETE FROM 学生 WHERE 学生学号 = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, studentId);
            ps.executeUpdate();
        }
    }

    @Test
    @DisplayName("测试根据学号和密码查询学生 - 正确凭据")
    void testGetStudentByIdAndPassword_Success() throws SQLException {
        // 测试场景：使用正确的学号和密码查询
        Student student = studentDao.getStudentByIdAndPassword(TEST_STUDENT_ID, TEST_PASSWORD);
        
        assertNotNull(student, "应该能查询到学生");
        assertEquals(TEST_STUDENT_ID, student.getStudentId(), "学号应该匹配");
        assertEquals("测试学生1", student.getName(), "姓名应该匹配");
        assertEquals(TEST_PASSWORD, student.getPassword(), "密码应该匹配");
    }

    @Test
    @DisplayName("测试根据学号和密码查询学生 - 错误密码")
    void testGetStudentByIdAndPassword_WrongPassword() throws SQLException {
        // 测试场景：使用正确的学号但错误的密码
        Student student = studentDao.getStudentByIdAndPassword(TEST_STUDENT_ID, TEST_WRONG_PASSWORD);
        
        assertNull(student, "错误密码应该返回null");
    }

    @Test
    @DisplayName("测试根据学号和密码查询学生 - 不存在的学号")
    void testGetStudentByIdAndPassword_NotExist() throws SQLException {
        // 测试场景：使用不存在的学号
        Student student = studentDao.getStudentByIdAndPassword(TEST_STUDENT_ID_NOT_EXIST, TEST_PASSWORD);
        
        assertNull(student, "不存在的学号应该返回null");
    }

    @Test
    @DisplayName("测试根据学号查询学生 - 存在的学生")
    void testGetStudentById_Exists() throws SQLException {
        // 测试场景：查询存在的学生
        Student student = studentDao.getStudentById(TEST_STUDENT_ID);
        
        assertNotNull(student, "应该能查询到学生");
        assertEquals(TEST_STUDENT_ID, student.getStudentId(), "学号应该匹配");
        assertEquals("测试学生1", student.getName(), "姓名应该匹配");
        assertEquals(TEST_MAJOR_ID, student.getMajorId(), "专业编号应该匹配");
        assertEquals(TEST_GRADE_NUMBER, student.getGradeNumber(), "年级编号应该匹配");
        assertEquals(TEST_CLASS_ID, student.getClassId(), "班级编号应该匹配");
    }

    @Test
    @DisplayName("测试根据学号查询学生 - 不存在的学生")
    void testGetStudentById_NotExists() throws SQLException {
        // 测试场景：查询不存在的学生
        Student student = studentDao.getStudentById(TEST_STUDENT_ID_NOT_EXIST);
        
        assertNull(student, "不存在的学号应该返回null");
    }

    @Test
    @DisplayName("测试根据学号查询学生 - 空字符串")
    void testGetStudentById_EmptyString() throws SQLException {
        // 测试场景：传入空字符串
        Student student = studentDao.getStudentById("");
        
        assertNull(student, "空字符串应该返回null");
    }

    @Test
    @DisplayName("测试添加学生 - 正常情况")
    void testAddStudent_Success() throws SQLException {
        // 测试场景：添加新学生（正常情况）
        Student newStudent = new Student();
        newStudent.setStudentId(TEST_STUDENT_ID_2);
        newStudent.setMajorId(TEST_MAJOR_ID);
        newStudent.setGradeNumber(TEST_GRADE_NUMBER);
        newStudent.setClassId(TEST_CLASS_ID);
        newStudent.setName("测试学生2");
        newStudent.setGender("女");
        newStudent.setDateOfBirth(LocalDate.of(2001, 5, 15));
        newStudent.setPhoneNumber("13900000000");
        newStudent.setPassword("password123");
        
        boolean result = studentDao.addStudent(newStudent);
        
        assertTrue(result, "添加学生应该成功");
        
        // 验证学生确实被添加
        Student addedStudent = studentDao.getStudentById(TEST_STUDENT_ID_2);
        assertNotNull(addedStudent, "添加的学生应该能查询到");
        assertEquals("测试学生2", addedStudent.getName(), "姓名应该匹配");
    }

    @Test
    @DisplayName("测试添加学生 - 重复学号")
    void testAddStudent_DuplicateId() {
        // 测试场景：添加重复学号的学生（应该失败）
        Student duplicateStudent = new Student();
        duplicateStudent.setStudentId(TEST_STUDENT_ID);  // 使用已存在的学号
        duplicateStudent.setMajorId(TEST_MAJOR_ID);
        duplicateStudent.setGradeNumber(TEST_GRADE_NUMBER);
        duplicateStudent.setClassId(TEST_CLASS_ID);
        duplicateStudent.setName("重复学生");
        duplicateStudent.setPassword("password123");
        
        // 应该抛出SQLException（违反主键约束）
        assertThrows(SQLException.class, () -> {
            studentDao.addStudent(duplicateStudent);
        }, "重复学号应该抛出SQLException");
    }

    @Test
    @DisplayName("测试更新学生信息 - 正常情况")
    void testUpdateStudent_Success() throws SQLException {
        // 测试场景：更新存在的学生信息
        Student student = studentDao.getStudentById(TEST_STUDENT_ID);
        assertNotNull(student, "测试前应该存在该学生");
        
        // 修改学生信息
        student.setName("更新后的姓名");
        student.setPhoneNumber("13999999999");
        student.setGender("女");
        
        boolean result = studentDao.updateStudent(student);
        
        assertTrue(result, "更新学生应该成功");
        
        // 验证更新是否生效
        Student updatedStudent = studentDao.getStudentById(TEST_STUDENT_ID);
        assertNotNull(updatedStudent, "更新后应该能查询到学生");
        assertEquals("更新后的姓名", updatedStudent.getName(), "姓名应该已更新");
        assertEquals("13999999999", updatedStudent.getPhoneNumber(), "手机号码应该已更新");
        assertEquals("女", updatedStudent.getGender(), "性别应该已更新");
    }

    @Test
    @DisplayName("测试更新学生信息 - 不存在的学生")
    void testUpdateStudent_NotExists() throws SQLException {
        // 测试场景：更新不存在的学生（应该返回false）
        Student nonExistentStudent = new Student();
        nonExistentStudent.setStudentId(TEST_STUDENT_ID_NOT_EXIST);
        nonExistentStudent.setMajorId(TEST_MAJOR_ID);
        nonExistentStudent.setGradeNumber(TEST_GRADE_NUMBER);
        nonExistentStudent.setClassId(TEST_CLASS_ID);
        nonExistentStudent.setName("不存在的学生");
        nonExistentStudent.setPassword("password123");
        
        boolean result = studentDao.updateStudent(nonExistentStudent);
        
        assertFalse(result, "更新不存在的学生应该返回false");
    }

    @Test
    @DisplayName("测试删除学生 - 正常情况")
    void testDeleteStudent_Success() throws SQLException {
        // 测试场景：删除存在的学生
        // 先验证学生存在
        Student student = studentDao.getStudentById(TEST_STUDENT_ID);
        assertNotNull(student, "删除前应该存在该学生");
        
        boolean result = studentDao.deleteStudent(TEST_STUDENT_ID);
        
        assertTrue(result, "删除学生应该成功");
        
        // 验证学生确实被删除
        Student deletedStudent = studentDao.getStudentById(TEST_STUDENT_ID);
        assertNull(deletedStudent, "删除后应该查询不到学生");
        
        // 重新插入，以便@AfterEach清理
        insertTestStudent(TEST_STUDENT_ID, "测试学生1", TEST_PASSWORD);
    }

    @Test
    @DisplayName("测试删除学生 - 不存在的学生")
    void testDeleteStudent_NotExists() throws SQLException {
        // 测试场景：删除不存在的学生（应该返回false）
        boolean result = studentDao.deleteStudent(TEST_STUDENT_ID_NOT_EXIST);
        
        assertFalse(result, "删除不存在的学生应该返回false");
    }

    @Test
    @DisplayName("测试删除学生 - 空字符串")
    void testDeleteStudent_EmptyString() throws SQLException {
        // 测试场景：传入空字符串
        boolean result = studentDao.deleteStudent("");
        
        assertFalse(result, "空字符串应该返回false");
    }

    @Test
    @DisplayName("测试根据班级编号统计学生人数 - 有学生的班级")
    void testGetStudentCountByClassId_WithStudents() throws SQLException {
        // 测试场景：统计有学生的班级
        // 确保测试学生在该班级中
        int count = studentDao.getStudentCountByClassId(TEST_CLASS_ID);
        
        assertTrue(count >= 1, "该班级应该至少有1个学生（测试学生）");
    }

    @Test
    @DisplayName("测试根据班级编号统计学生人数 - 空班级")
    void testGetStudentCountByClassId_EmptyClass() throws SQLException {
        // 测试场景：统计没有学生的班级（使用一个不存在的班级编号）
        int count = studentDao.getStudentCountByClassId("999");
        
        assertEquals(0, count, "不存在的班级应该返回0");
    }

    @Test
    @DisplayName("测试根据班级编号统计学生人数 - 空字符串")
    void testGetStudentCountByClassId_EmptyString() throws SQLException {
        // 测试场景：传入空字符串
        int count = studentDao.getStudentCountByClassId("");
        
        assertEquals(0, count, "空字符串应该返回0");
    }

    @Test
    @DisplayName("测试数据库连接 - 验证测试环境")
    void testDatabaseConnection() {
        // 验证测试环境数据库连接是否正常
        try {
            var connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "数据库连接不应为null");
            assertFalse(connection.isClosed(), "数据库连接应该是打开的");
            connection.close();
        } catch (SQLException e) {
            fail("数据库连接失败: " + e.getMessage());
        }
    }
}
