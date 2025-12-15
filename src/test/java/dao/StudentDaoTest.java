package dao;

import db.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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

    @BeforeEach
    void setUp() {
        // 在每个测试方法执行前初始化
        studentDao = new StudentDaoImpl();
    }

    @Test
    @DisplayName("测试根据学号和密码查询学生 - 正常情况")
    void testGetStudentByIdAndPassword() {
        // TODO: 实现测试逻辑
        // 测试场景：
        // 1. 使用正确的学号和密码查询
        // 2. 使用错误的密码查询
        // 3. 使用不存在的学号查询
    }

    @Test
    @DisplayName("测试根据学号查询学生 - 正常情况")
    void testGetStudentById() {
        // TODO: 实现测试逻辑
        // 测试场景：
        // 1. 查询存在的学生
        // 2. 查询不存在的学生
        // 3. 传入null值
        // 4. 传入空字符串
    }

    @Test
    @DisplayName("测试添加学生 - 正常情况")
    void testAddStudent() {
        // TODO: 实现测试逻辑
        // 测试场景：
        // 1. 添加新学生（正常情况）
        // 2. 添加重复学号的学生（应该失败）
        // 3. 传入null值
    }

    @Test
    @DisplayName("测试更新学生信息 - 正常情况")
    void testUpdateStudent() {
        // TODO: 实现测试逻辑
        // 测试场景：
        // 1. 更新存在的学生信息
        // 2. 更新不存在的学生（应该失败）
        // 3. 传入null值
    }

    @Test
    @DisplayName("测试删除学生 - 正常情况")
    void testDeleteStudent() {
        // TODO: 实现测试逻辑
        // 测试场景：
        // 1. 删除存在的学生
        // 2. 删除不存在的学生（应该返回false）
        // 3. 传入null值
    }

    @Test
    @DisplayName("测试根据班级编号统计学生人数 - 正常情况")
    void testGetStudentCountByClassId() {
        // TODO: 实现测试逻辑
        // 测试场景：
        // 1. 统计有学生的班级
        // 2. 统计没有学生的班级（应该返回0）
        // 3. 传入null值
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

