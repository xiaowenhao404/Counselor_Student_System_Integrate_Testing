package service;

import db.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AuthService的单元测试类
 * 
 * 测试类命名：被测试类名 + Test
 * 测试方法命名：test + 方法名（驼峰命名）
 */
@DisplayName("AuthService单元测试")
class AuthServiceTest {

    private AuthService authService;

    // 测试数据常量 - 使用真实数据格式
    private static final String TEST_STUDENT_ID = "202383290001"; // 从data.sql中获取的真实学号
    private static final String TEST_STUDENT_PASSWORD = "123456"; // 对应的密码
    private static final String TEST_STUDENT_NAME = "张三"; // 对应的姓名
    private static final String TEST_STUDENT_ID_NOT_EXIST = "TEST999"; // 不存在的学号
    private static final String TEST_STUDENT_WRONG_PASSWORD = "wrong123"; // 错误密码

    private static final String TEST_COUNSELOR_ID = "223807001"; // 从data.sql中获取的真实工号
    private static final String TEST_COUNSELOR_PASSWORD = "123456"; // 对应的密码
    private static final String TEST_COUNSELOR_NAME = "张明"; // 对应的姓名
    private static final String TEST_COUNSELOR_ID_NOT_EXIST = "TEST999"; // 不存在的工号
    private static final String TEST_COUNSELOR_WRONG_PASSWORD = "wrong123"; // 错误密码

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "123321";
    private static final String ADMIN_WRONG_PASSWORD = "wrong123";

    @BeforeEach
    void setUp() {
        // 在每个测试方法执行前初始化
        authService = new AuthService();
    }

    @AfterEach
    void tearDown() {
        // 测试后清理（如果需要）
        // 由于使用真实数据，不需要清理
    }

    // ==================== 学生登录测试 ====================

    @Test
    @DisplayName("测试学生登录 - 正确凭据")
    void testLogin_Student_Success() {
        // 测试场景：使用正确的学号和密码登录
        AuthService.LoginResult result = authService.login(
            TEST_STUDENT_ID, 
            TEST_STUDENT_PASSWORD, 
            AuthService.UserType.STUDENT
        );

        assertNotNull(result, "登录结果不应为null");
        assertTrue(result.isSuccess(), "登录应该成功");
        assertEquals(AuthService.UserType.STUDENT, result.getUserType(), "用户类型应该是STUDENT");
        assertEquals(TEST_STUDENT_ID, result.getUserId(), "用户ID应该匹配学号");
        assertEquals(TEST_STUDENT_NAME, result.getUserName(), "用户名应该匹配姓名");
    }

    @Test
    @DisplayName("测试学生登录 - 错误密码")
    void testLogin_Student_WrongPassword() {
        // 测试场景：使用正确的学号但错误的密码
        AuthService.LoginResult result = authService.login(
            TEST_STUDENT_ID, 
            TEST_STUDENT_WRONG_PASSWORD, 
            AuthService.UserType.STUDENT
        );

        assertNotNull(result, "登录结果不应为null");
        assertFalse(result.isSuccess(), "登录应该失败");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "用户类型应该是NONE");
        assertNull(result.getUserId(), "用户ID应该为null");
        assertNull(result.getUserName(), "用户名应该为null");
    }

    @Test
    @DisplayName("测试学生登录 - 不存在的学号")
    void testLogin_Student_NotExist() {
        // 测试场景：使用不存在的学号
        AuthService.LoginResult result = authService.login(
            TEST_STUDENT_ID_NOT_EXIST, 
            TEST_STUDENT_PASSWORD, 
            AuthService.UserType.STUDENT
        );

        assertNotNull(result, "登录结果不应为null");
        assertFalse(result.isSuccess(), "登录应该失败");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "用户类型应该是NONE");
        assertNull(result.getUserId(), "用户ID应该为null");
        assertNull(result.getUserName(), "用户名应该为null");
    }

    // ==================== 辅导员登录测试 ====================

    @Test
    @DisplayName("测试辅导员登录 - 正确凭据")
    void testLogin_Counselor_Success() {
        // 测试场景：使用正确的工号和密码登录
        AuthService.LoginResult result = authService.login(
            TEST_COUNSELOR_ID, 
            TEST_COUNSELOR_PASSWORD, 
            AuthService.UserType.COUNSELOR
        );

        assertNotNull(result, "登录结果不应为null");
        assertTrue(result.isSuccess(), "登录应该成功");
        assertEquals(AuthService.UserType.COUNSELOR, result.getUserType(), "用户类型应该是COUNSELOR");
        assertEquals(TEST_COUNSELOR_ID, result.getUserId(), "用户ID应该匹配工号");
        assertEquals(TEST_COUNSELOR_NAME, result.getUserName(), "用户名应该匹配姓名");
    }

    @Test
    @DisplayName("测试辅导员登录 - 错误密码")
    void testLogin_Counselor_WrongPassword() {
        // 测试场景：使用正确的工号但错误的密码
        AuthService.LoginResult result = authService.login(
            TEST_COUNSELOR_ID, 
            TEST_COUNSELOR_WRONG_PASSWORD, 
            AuthService.UserType.COUNSELOR
        );

        assertNotNull(result, "登录结果不应为null");
        assertFalse(result.isSuccess(), "登录应该失败");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "用户类型应该是NONE");
        assertNull(result.getUserId(), "用户ID应该为null");
        assertNull(result.getUserName(), "用户名应该为null");
    }

    @Test
    @DisplayName("测试辅导员登录 - 不存在的工号")
    void testLogin_Counselor_NotExist() {
        // 测试场景：使用不存在的工号
        AuthService.LoginResult result = authService.login(
            TEST_COUNSELOR_ID_NOT_EXIST, 
            TEST_COUNSELOR_PASSWORD, 
            AuthService.UserType.COUNSELOR
        );

        assertNotNull(result, "登录结果不应为null");
        assertFalse(result.isSuccess(), "登录应该失败");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "用户类型应该是NONE");
        assertNull(result.getUserId(), "用户ID应该为null");
        assertNull(result.getUserName(), "用户名应该为null");
    }

    // ==================== 管理员登录测试 ====================

    @Test
    @DisplayName("测试管理员登录 - 正确凭据")
    void testLogin_Admin_Success() {
        // 测试场景：使用正确的管理员用户名和密码
        AuthService.LoginResult result = authService.login(
            ADMIN_USERNAME, 
            ADMIN_PASSWORD, 
            AuthService.UserType.ADMIN
        );

        assertNotNull(result, "登录结果不应为null");
        assertTrue(result.isSuccess(), "登录应该成功");
        assertEquals(AuthService.UserType.ADMIN, result.getUserType(), "用户类型应该是ADMIN");
        assertEquals(ADMIN_USERNAME, result.getUserId(), "用户ID应该匹配管理员用户名");
        assertEquals("管理员", result.getUserName(), "用户名应该是'管理员'");
    }

    @Test
    @DisplayName("测试管理员登录 - 错误密码")
    void testLogin_Admin_WrongPassword() {
        // 测试场景：使用正确的管理员用户名但错误的密码
        AuthService.LoginResult result = authService.login(
            ADMIN_USERNAME, 
            ADMIN_WRONG_PASSWORD, 
            AuthService.UserType.ADMIN
        );

        assertNotNull(result, "登录结果不应为null");
        assertFalse(result.isSuccess(), "登录应该失败");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "用户类型应该是NONE");
        assertNull(result.getUserId(), "用户ID应该为null");
        assertNull(result.getUserName(), "用户名应该为null");
    }

    @Test
    @DisplayName("测试管理员登录 - 错误的用户名")
    void testLogin_Admin_WrongUsername() {
        // 测试场景：使用错误的管理员用户名
        AuthService.LoginResult result = authService.login(
            "wrongadmin", 
            ADMIN_PASSWORD, 
            AuthService.UserType.ADMIN
        );

        assertNotNull(result, "登录结果不应为null");
        assertFalse(result.isSuccess(), "登录应该失败");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "用户类型应该是NONE");
        assertNull(result.getUserId(), "用户ID应该为null");
        assertNull(result.getUserName(), "用户名应该为null");
    }

    // ==================== 登录失败返回NONE类型测试 ====================

    @Test
    @DisplayName("测试登录失败返回NONE类型 - 学生类型")
    void testLogin_NoneType_Student() {
        // 测试场景：学生登录失败应返回NONE类型
        AuthService.LoginResult result = authService.login(
            TEST_STUDENT_ID_NOT_EXIST, 
            TEST_STUDENT_PASSWORD, 
            AuthService.UserType.STUDENT
        );

        assertNotNull(result, "登录结果不应为null");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "失败时用户类型应该是NONE");
        assertFalse(result.isSuccess(), "isSuccess()应该返回false");
    }

    @Test
    @DisplayName("测试登录失败返回NONE类型 - 辅导员类型")
    void testLogin_NoneType_Counselor() {
        // 测试场景：辅导员登录失败应返回NONE类型
        AuthService.LoginResult result = authService.login(
            TEST_COUNSELOR_ID_NOT_EXIST, 
            TEST_COUNSELOR_PASSWORD, 
            AuthService.UserType.COUNSELOR
        );

        assertNotNull(result, "登录结果不应为null");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "失败时用户类型应该是NONE");
        assertFalse(result.isSuccess(), "isSuccess()应该返回false");
    }

    // ==================== LoginResult类测试 ====================

    @Test
    @DisplayName("测试LoginResult - 成功登录结果")
    void testLoginResult_Success() {
        // 测试场景：验证成功登录的LoginResult对象
        AuthService.LoginResult result = authService.login(
            TEST_STUDENT_ID, 
            TEST_STUDENT_PASSWORD, 
            AuthService.UserType.STUDENT
        );

        assertNotNull(result, "登录结果不应为null");
        assertTrue(result.isSuccess(), "isSuccess()应该返回true");
        assertEquals(AuthService.UserType.STUDENT, result.getUserType(), "getUserType()应该返回STUDENT");
        assertEquals(TEST_STUDENT_ID, result.getUserId(), "getUserId()应该返回学号");
        assertEquals(TEST_STUDENT_NAME, result.getUserName(), "getUserName()应该返回姓名");
    }

    @Test
    @DisplayName("测试LoginResult - 失败登录结果")
    void testLoginResult_Failure() {
        // 测试场景：验证失败登录的LoginResult对象
        AuthService.LoginResult result = authService.login(
            TEST_STUDENT_ID, 
            TEST_STUDENT_WRONG_PASSWORD, 
            AuthService.UserType.STUDENT
        );

        assertNotNull(result, "登录结果不应为null");
        assertFalse(result.isSuccess(), "isSuccess()应该返回false");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "getUserType()应该返回NONE");
        assertNull(result.getUserId(), "getUserId()应该返回null");
        assertNull(result.getUserName(), "getUserName()应该返回null");
    }

    // ==================== 边界情况测试 ====================

    @Test
    @DisplayName("测试登录 - NONE用户类型")
    void testLogin_NoneUserType() {
        // 测试场景：使用NONE用户类型登录
        AuthService.LoginResult result = authService.login(
            TEST_STUDENT_ID, 
            TEST_STUDENT_PASSWORD, 
            AuthService.UserType.NONE
        );

        assertNotNull(result, "登录结果不应为null");
        assertFalse(result.isSuccess(), "登录应该失败");
        assertEquals(AuthService.UserType.NONE, result.getUserType(), "用户类型应该是NONE");
        assertNull(result.getUserId(), "用户ID应该为null");
        assertNull(result.getUserName(), "用户名应该为null");
    }

    // ==================== 数据库异常情况测试 ====================
    // 注意：由于使用真实DAO，数据库异常情况较难模拟
    // 可以通过断开数据库连接来测试，但这里不进行破坏性测试
    // 实际项目中可以使用Mockito来模拟异常情况

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

