# CI配置说明文档

## 概述

本文档说明辅导员学生信息交流管理系统的GitHub Actions持续集成（CI）配置。

## CI工作流配置

### 工作流文件位置
`.github/workflows/ci.yml`

### 工作流名称
CI - Build and Test

## 触发条件

CI工作流在以下情况下自动触发：

1. **Push事件**：当代码推送到以下分支时触发
   - `main` 分支
   - `master` 分支

2. **Pull Request事件**：当创建或更新针对以下分支的Pull Request时触发
   - `main` 分支
   - `master` 分支

## 运行环境

- **操作系统**：Ubuntu Latest（最新版Ubuntu）
- **Java版本**：Java 23（使用Temurin发行版）
- **构建工具**：Maven（使用系统Maven，通过actions/setup-java@v3自动配置）

## 构建步骤

CI工作流包含以下步骤：

### Step 1: Checkout代码
- **Action**: `actions/checkout@v3`
- **功能**：从GitHub仓库检出源代码到工作目录

### Step 2: 设置Java 23环境
- **Action**: `actions/setup-java@v3`
- **配置**：
  - Java版本：23
  - 发行版：Temurin（Eclipse Temurin OpenJDK）
  - Maven缓存：启用（自动缓存Maven依赖，加速后续构建）

### Step 3: Maven构建
- **命令**：`mvn clean compile`
- **功能**：
  - 清理之前的构建产物（`clean`）
  - 编译源代码（`compile`）

### Step 4: 运行测试
- **命令**：`mvn test`
- **功能**：
  - 运行所有JUnit 5测试用例
  - 生成测试报告到 `target/surefire-reports/`

### Step 5: 上传测试报告
- **Action**: `actions/upload-artifact@v3`
- **条件**：`if: always()`（无论测试成功或失败都上传）
- **配置**：
  - 报告名称：`test-reports`
  - 报告路径：`target/surefire-reports/`
  - 保留天数：7天

## 测试执行说明

### 测试框架
- **框架**：JUnit 5
- **运行器**：Maven Surefire Plugin

### 测试范围
CI会自动运行所有符合以下命名规则的测试类：
- `**/*Test.java`
- `**/*Tests.java`

### 测试报告
- **位置**：`target/surefire-reports/`
- **格式**：XML格式（Surefire报告）
- **查看方式**：在GitHub Actions运行完成后，可在Artifacts中下载测试报告

## CI运行结果记录

### 运行状态说明
- ✅ **成功**：所有步骤执行成功，代码编译通过，所有测试通过
- ❌ **失败**：任何步骤失败（编译失败、测试失败等）

### 查看运行结果
1. 访问GitHub仓库的"Actions"标签页
2. 选择"CI - Build and Test"工作流
3. 查看具体的运行记录和日志

## 问题排查记录

### 常见问题及解决方案

#### 1. Java 23不可用
**问题**：GitHub Actions中Java 23可能不可用
**解决方案**：
- 如果遇到Java 23不可用的问题，可以降级到Java 21
- 修改`.github/workflows/ci.yml`中的`java-version`为`'21'`

#### 2. 数据库连接失败
**问题**：测试需要数据库连接，但CI环境中没有数据库
**解决方案**：
- 使用Docker容器运行MySQL数据库
- 或跳过需要数据库的测试（使用`@Disabled`注解）
- 或使用测试容器（Testcontainers）框架

#### 3. Maven依赖下载失败
**问题**：Maven无法从中央仓库下载依赖
**解决方案**：
- 检查网络连接
- 确认`pom.xml`中配置了正确的仓库地址
- 使用Maven缓存加速构建（已配置）

#### 4. 构建时间过长
**问题**：CI构建时间过长
**解决方案**：
- 已启用Maven缓存，依赖会自动缓存
- 考虑使用Maven Wrapper确保版本一致性
- 优化测试用例，减少不必要的测试

#### 5. JavaFX模块问题
**问题**：JavaFX在CI环境中可能无法正常运行
**解决方案**：
- CI环境中主要运行测试，不运行UI应用
- 如果测试涉及JavaFX，考虑使用TestFX框架或Mock UI组件

## 配置更新记录

| 日期 | 更新内容 | 更新人 |
|------|---------|--------|
| 2025-12-15 | 初始CI配置创建 | System |

## 注意事项

1. **Java版本**：项目使用Java 23，如果GitHub Actions不支持，需要降级到Java 21
2. **数据库依赖**：当前测试可能需要数据库连接，CI环境中需要配置数据库或跳过数据库相关测试
3. **构建时间**：首次构建时间可能较长，后续构建会利用缓存加速
4. **测试报告**：测试报告会保留7天，可在GitHub Actions页面下载查看
5. **分支保护**：建议在GitHub仓库设置中启用分支保护，要求CI通过后才能合并代码

## 相关文档

- [GitHub Actions文档](https://docs.github.com/en/actions)
- [Maven文档](https://maven.apache.org/guides/)
- [JUnit 5文档](https://junit.org/junit5/docs/current/user-guide/)

