# AuthService 单元测试用例说明

## 测试类信息
- **测试类名**: `AuthServiceTest`
- **被测试类**: `AuthService`
- **测试文件路径**: `src/test/java/service/AuthServiceTest.java`
- **测试用例总数**: 15个
- **创建日期**: 2025-12-15

## 测试环境
- **测试数据库**: counselor_student_system
- **测试数据标识**: 
  - 使用真实数据（从data.sql中获取）
  - `202383290001` - 测试学生（张三，密码：123456）
  - `223807001` - 测试辅导员（张明，密码：123456）
  - `admin` - 管理员（硬编码，密码：123321）
- **测试策略**: 方案A - 使用真实DAO + 测试数据库（更接近真实场景）
- **测试框架**: JUnit 5

## 用例编号说明
- **编号格式**: TC + 三位数字
- **TC含义**: Test Case（测试用例）
- **编号范围**: TC001 - TC015

## 测试用例清单

### 1. 学生登录测试

#### 用例 TC001: 测试学生登录 - 正确凭据
- **测试方法**: `testLogin_Student_Success()`
- **测试类型**: 正常情况测试
- **前置条件**: 
  - 数据库中存在学号为 202383290001 的学生（从data.sql中获取）
  - 该学生的密码为 "123456"
  - 该学生的姓名为 "张三"
- **测试步骤**:
  1. 调用 `authService.login("202383290001", "123456", UserType.STUDENT)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回true
  4. 验证getUserType()返回STUDENT
  5. 验证getUserId()返回"202383290001"
  6. 验证getUserName()返回"张三"
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 学号 | 202383290001 | 从data.sql中获取的真实学号 |
| 密码 | 123456 | 对应的密码 |
| 用户类型 | STUDENT | 学生类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = true
  - getUserType() = STUDENT
  - getUserId() = "202383290001"
  - getUserName() = "张三"
- **实际结果**: ✅ 通过
- **备注**: 验证正常学生登录场景，使用真实数据

#### 用例 TC002: 测试学生登录 - 错误密码
- **测试方法**: `testLogin_Student_WrongPassword()`
- **测试类型**: 异常情况测试
- **前置条件**: 
  - 数据库中存在学号为 202383290001 的学生
  - 该学生的密码为 "123456"
- **测试步骤**:
  1. 调用 `authService.login("202383290001", "wrong123", UserType.STUDENT)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回false
  4. 验证getUserType()返回NONE
  5. 验证getUserId()返回null
  6. 验证getUserName()返回null
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 学号 | 202383290001 | 真实学号（正确） |
| 密码 | wrong123 | 错误密码 |
| 用户类型 | STUDENT | 学生类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = false
  - getUserType() = NONE
  - getUserId() = null
  - getUserName() = null
- **实际结果**: ✅ 通过
- **备注**: 验证密码验证功能

#### 用例 TC003: 测试学生登录 - 不存在的学号
- **测试方法**: `testLogin_Student_NotExist()`
- **测试类型**: 边界情况测试
- **前置条件**: 数据库中不存在学号为 TEST999 的学生
- **测试步骤**:
  1. 调用 `authService.login("TEST999", "123456", UserType.STUDENT)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回false
  4. 验证getUserType()返回NONE
  5. 验证getUserId()返回null
  6. 验证getUserName()返回null
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 学号 | TEST999 | 不存在的学号 |
| 密码 | 123456 | 任意密码 |
| 用户类型 | STUDENT | 学生类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = false
  - getUserType() = NONE
  - getUserId() = null
  - getUserName() = null
- **实际结果**: ✅ 通过
- **备注**: 验证不存在的用户处理

### 2. 辅导员登录测试

#### 用例 TC004: 测试辅导员登录 - 正确凭据
- **测试方法**: `testLogin_Counselor_Success()`
- **测试类型**: 正常情况测试
- **前置条件**: 
  - 数据库中存在工号为 223807001 的辅导员（从data.sql中获取）
  - 该辅导员的密码为 "123456"
  - 该辅导员的姓名为 "张明"
- **测试步骤**:
  1. 调用 `authService.login("223807001", "123456", UserType.COUNSELOR)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回true
  4. 验证getUserType()返回COUNSELOR
  5. 验证getUserId()返回"223807001"
  6. 验证getUserName()返回"张明"
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 工号 | 223807001 | 从data.sql中获取的真实工号 |
| 密码 | 123456 | 对应的密码 |
| 用户类型 | COUNSELOR | 辅导员类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = true
  - getUserType() = COUNSELOR
  - getUserId() = "223807001"
  - getUserName() = "张明"
- **实际结果**: ✅ 通过
- **备注**: 验证正常辅导员登录场景，使用真实数据

#### 用例 TC005: 测试辅导员登录 - 错误密码
- **测试方法**: `testLogin_Counselor_WrongPassword()`
- **测试类型**: 异常情况测试
- **前置条件**: 
  - 数据库中存在工号为 223807001 的辅导员
  - 该辅导员的密码为 "123456"
- **测试步骤**:
  1. 调用 `authService.login("223807001", "wrong123", UserType.COUNSELOR)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回false
  4. 验证getUserType()返回NONE
  5. 验证getUserId()返回null
  6. 验证getUserName()返回null
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 工号 | 223807001 | 真实工号（正确） |
| 密码 | wrong123 | 错误密码 |
| 用户类型 | COUNSELOR | 辅导员类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = false
  - getUserType() = NONE
  - getUserId() = null
  - getUserName() = null
- **实际结果**: ✅ 通过
- **备注**: 验证密码验证功能

#### 用例 TC006: 测试辅导员登录 - 不存在的工号
- **测试方法**: `testLogin_Counselor_NotExist()`
- **测试类型**: 边界情况测试
- **前置条件**: 数据库中不存在工号为 TEST999 的辅导员
- **测试步骤**:
  1. 调用 `authService.login("TEST999", "123456", UserType.COUNSELOR)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回false
  4. 验证getUserType()返回NONE
  5. 验证getUserId()返回null
  6. 验证getUserName()返回null
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 工号 | TEST999 | 不存在的工号 |
| 密码 | 123456 | 任意密码 |
| 用户类型 | COUNSELOR | 辅导员类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = false
  - getUserType() = NONE
  - getUserId() = null
  - getUserName() = null
- **实际结果**: ✅ 通过
- **备注**: 验证不存在的用户处理

### 3. 管理员登录测试

#### 用例 TC007: 测试管理员登录 - 正确凭据
- **测试方法**: `testLogin_Admin_Success()`
- **测试类型**: 正常情况测试
- **前置条件**: 
  - 管理员凭据硬编码在AuthService中
  - 管理员用户名为 "admin"
  - 管理员密码为 "123321"
- **测试步骤**:
  1. 调用 `authService.login("admin", "123321", UserType.ADMIN)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回true
  4. 验证getUserType()返回ADMIN
  5. 验证getUserId()返回"admin"
  6. 验证getUserName()返回"管理员"
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 用户名 | admin | 硬编码的管理员用户名 |
| 密码 | 123321 | 硬编码的管理员密码 |
| 用户类型 | ADMIN | 管理员类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = true
  - getUserType() = ADMIN
  - getUserId() = "admin"
  - getUserName() = "管理员"
- **实际结果**: ✅ 通过
- **备注**: 验证硬编码的管理员登录

#### 用例 TC008: 测试管理员登录 - 错误密码
- **测试方法**: `testLogin_Admin_WrongPassword()`
- **测试类型**: 异常情况测试
- **前置条件**: 
  - 管理员用户名为 "admin"
  - 正确的管理员密码为 "123321"
- **测试步骤**:
  1. 调用 `authService.login("admin", "wrong123", UserType.ADMIN)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回false
  4. 验证getUserType()返回NONE
  5. 验证getUserId()返回null
  6. 验证getUserName()返回null
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 用户名 | admin | 正确的管理员用户名 |
| 密码 | wrong123 | 错误密码 |
| 用户类型 | ADMIN | 管理员类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = false
  - getUserType() = NONE
  - getUserId() = null
  - getUserName() = null
- **实际结果**: ✅ 通过
- **备注**: 验证管理员密码验证功能

#### 用例 TC009: 测试管理员登录 - 错误的用户名
- **测试方法**: `testLogin_Admin_WrongUsername()`
- **测试类型**: 异常情况测试
- **前置条件**: 
  - 正确的管理员用户名为 "admin"
  - 正确的管理员密码为 "123321"
- **测试步骤**:
  1. 调用 `authService.login("wrongadmin", "123321", UserType.ADMIN)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回false
  4. 验证getUserType()返回NONE
  5. 验证getUserId()返回null
  6. 验证getUserName()返回null
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 用户名 | wrongadmin | 错误的管理员用户名 |
| 密码 | 123321 | 正确的管理员密码 |
| 用户类型 | ADMIN | 管理员类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = false
  - getUserType() = NONE
  - getUserId() = null
  - getUserName() = null
- **实际结果**: ✅ 通过
- **备注**: 验证管理员用户名验证功能

### 4. 登录失败返回NONE类型测试

#### 用例 TC010: 测试登录失败返回NONE类型 - 学生类型
- **测试方法**: `testLogin_NoneType_Student()`
- **测试类型**: 边界情况测试
- **前置条件**: 数据库中不存在学号为 TEST999 的学生
- **测试步骤**:
  1. 调用 `authService.login("TEST999", "123456", UserType.STUDENT)`
  2. 验证返回的LoginResult对象不为null
  3. 验证getUserType()返回NONE
  4. 验证isSuccess()返回false
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 学号 | TEST999 | 不存在的学号 |
| 密码 | 123456 | 任意密码 |
| 用户类型 | STUDENT | 学生类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - getUserType() = NONE
  - isSuccess() = false
- **实际结果**: ✅ 通过
- **备注**: 验证失败时返回NONE类型

#### 用例 TC011: 测试登录失败返回NONE类型 - 辅导员类型
- **测试方法**: `testLogin_NoneType_Counselor()`
- **测试类型**: 边界情况测试
- **前置条件**: 数据库中不存在工号为 TEST999 的辅导员
- **测试步骤**:
  1. 调用 `authService.login("TEST999", "123456", UserType.COUNSELOR)`
  2. 验证返回的LoginResult对象不为null
  3. 验证getUserType()返回NONE
  4. 验证isSuccess()返回false
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 工号 | TEST999 | 不存在的工号 |
| 密码 | 123456 | 任意密码 |
| 用户类型 | COUNSELOR | 辅导员类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - getUserType() = NONE
  - isSuccess() = false
- **实际结果**: ✅ 通过
- **备注**: 验证失败时返回NONE类型

### 5. LoginResult类测试

#### 用例 TC012: 测试LoginResult - 成功登录结果
- **测试方法**: `testLoginResult_Success()`
- **测试类型**: 正常情况测试
- **前置条件**: 
  - 数据库中存在学号为 202383290001 的学生
  - 该学生的密码为 "123456"
- **测试步骤**:
  1. 调用 `authService.login("202383290001", "123456", UserType.STUDENT)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回true
  4. 验证getUserType()返回STUDENT
  5. 验证getUserId()返回"202383290001"
  6. 验证getUserName()返回"张三"
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 学号 | 202383290001 | 真实学号 |
| 密码 | 123456 | 真实密码 |
| 用户类型 | STUDENT | 学生类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = true
  - getUserType() = STUDENT
  - getUserId() = "202383290001"
  - getUserName() = "张三"
- **实际结果**: ✅ 通过
- **备注**: 验证LoginResult类的所有getter方法

#### 用例 TC013: 测试LoginResult - 失败登录结果
- **测试方法**: `testLoginResult_Failure()`
- **测试类型**: 异常情况测试
- **前置条件**: 
  - 数据库中存在学号为 202383290001 的学生
  - 该学生的密码为 "123456"
- **测试步骤**:
  1. 调用 `authService.login("202383290001", "wrong123", UserType.STUDENT)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回false
  4. 验证getUserType()返回NONE
  5. 验证getUserId()返回null
  6. 验证getUserName()返回null
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 学号 | 202383290001 | 真实学号（正确） |
| 密码 | wrong123 | 错误密码 |
| 用户类型 | STUDENT | 学生类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = false
  - getUserType() = NONE
  - getUserId() = null
  - getUserName() = null
- **实际结果**: ✅ 通过
- **备注**: 验证失败时LoginResult类的所有getter方法

### 6. 边界情况测试

#### 用例 TC014: 测试登录 - NONE用户类型
- **测试方法**: `testLogin_NoneUserType()`
- **测试类型**: 边界情况测试
- **前置条件**: 无
- **测试步骤**:
  1. 调用 `authService.login("202383290001", "123456", UserType.NONE)`
  2. 验证返回的LoginResult对象不为null
  3. 验证isSuccess()返回false
  4. 验证getUserType()返回NONE
  5. 验证getUserId()返回null
  6. 验证getUserName()返回null
- **输入数据**:

| 字段名 | 字段值 | 说明 |
|--------|--------|------|
| 用户名 | 202383290001 | 任意用户名 |
| 密码 | 123456 | 任意密码 |
| 用户类型 | NONE | NONE类型 |

- **预期结果**: 
  - 返回LoginResult对象（不为null）
  - isSuccess() = false
  - getUserType() = NONE
  - getUserId() = null
  - getUserName() = null
- **实际结果**: ✅ 通过
- **备注**: 验证NONE用户类型的处理

### 7. 测试环境验证

#### 用例 TC015: 测试数据库连接 - 验证测试环境
- **测试方法**: `testDatabaseConnection()`
- **测试类型**: 环境验证测试
- **前置条件**: 数据库服务正在运行
- **测试步骤**:
  1. 调用 `DatabaseConnection.getConnection()`
  2. 验证连接对象不为null
  3. 验证连接未关闭（isClosed()返回false）
  4. 关闭连接
- **输入数据**: 无（环境验证）
- **预期结果**: 
  - 连接对象不为null
  - 连接状态为打开（isClosed() = false）
- **实际结果**: ✅ 通过
- **备注**: 验证测试环境配置正确

## 测试覆盖率

- **方法覆盖率**: 100% (2/2个公共方法)
  - `login()`: 已测试 ✅
  - `LoginResult`类方法: 已测试 ✅
- **行覆盖率**: 待使用IDEA Coverage工具查看
- **分支覆盖率**: 待使用IDEA Coverage工具查看
  - 学生登录成功/失败分支 ✅
  - 辅导员登录成功/失败分支 ✅
  - 管理员登录成功/失败分支 ✅
  - NONE类型处理分支 ✅

## 测试总结

### 测试结果统计
- **总测试用例数**: 15
- **通过**: 15
- **失败**: 0
- **错误**: 0
- **跳过**: 0
- **通过率**: 100%

### 测试覆盖场景
- ✅ **正常情况测试**: 4个用例（TC001, TC004, TC007, TC012）
- ✅ **异常情况测试**: 5个用例（TC002, TC005, TC008, TC009, TC013）
- ✅ **边界情况测试**: 5个用例（TC003, TC006, TC010, TC011, TC014）
- ✅ **环境验证测试**: 1个用例（TC015）

### 测试方法覆盖
- ✅ `login()`方法 - STUDENT类型: 3个测试用例（TC001-TC003）
- ✅ `login()`方法 - COUNSELOR类型: 3个测试用例（TC004-TC006）
- ✅ `login()`方法 - ADMIN类型: 3个测试用例（TC007-TC009）
- ✅ `login()`方法 - NONE类型: 2个测试用例（TC010, TC011, TC014）
- ✅ `LoginResult`类: 2个测试用例（TC012, TC013）
- ✅ `testDatabaseConnection()`: 1个测试用例（TC015）

### 发现的问题
- 无

### 改进建议
- 无

## 附录

### 测试数据说明
- **202383290001**: 测试学生（张三），从data.sql中获取的真实数据
- **223807001**: 测试辅导员（张明），从data.sql中获取的真实数据
- **admin**: 管理员，硬编码在AuthService中
- **TEST999**: 不存在的用户标识，用于边界测试

### 测试数据清理
- 由于使用真实数据，不需要清理测试数据
- 测试方法独立，不依赖执行顺序

### 真实数据参考
测试使用的真实数据来自data.sql：

| 字段名 | 真实数据示例 | 说明 |
|--------|------------|------|
| 学号 | 202383290001 | 2023级计算机科学与技术专业学生 |
| 密码 | 123456 | 默认密码 |
| 姓名 | 张三 | 学生姓名 |
| 工号 | 223807001 | 辅导员工号 |
| 密码 | 123456 | 默认密码 |
| 姓名 | 张明 | 辅导员姓名 |
| 管理员用户名 | admin | 硬编码 |
| 管理员密码 | 123321 | 硬编码 |

**注意**: 本测试使用真实数据（从data.sql中获取），以确保测试接近真实场景。

