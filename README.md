//项目直接在终端中输入mvn clean javafx:run指令即可运行

# 项目结构说明

```
项目根目录/
├── db/                  # 数据库相关脚本和说明
│   ├── schema.sql       # 数据库结构定义（表、视图、触发器）
│   ├── data.sql         # 初始数据插入脚本
│   └── README.md        # 数据库脚本说明
├── src/
│   └── main/
│       ├── java/
│       │   ├── db/      # 数据库连接相关代码
│       │   ├── dao/     # 数据访问层（DAO）
│       │   ├── entity/  # 实体类（与数据库表结构对应）
│       │   ├── service/ # 业务逻辑层
│       │   └── ui/      # 界面控制器和主程序入口
│       └── resources/
│           ├── db.properties      # 数据库连接配置
│           └── ui/               # FXML界面、CSS样式、图片等资源
├── pom.xml               # Maven项目配置文件
├── README.md             # 项目说明（本文件）
└── ...                   # 其他配置或临时文件
```

- **db/schema.sql**：数据库结构定义，包括表、视图、触发器等。
- **db/data.sql**：数据库初始数据插入脚本。
- **src/main/java/db/**：数据库连接相关代码（如DatabaseConnection.java）。
- **src/main/java/dao/**：数据访问层，负责与数据库交互。
- **src/main/java/entity/**：实体类，映射数据库表结构。
- **src/main/java/service/**：业务逻辑层。
- **src/main/java/ui/**：界面控制器、主程序入口等。
- **src/main/resources/ui/**：FXML界面文件、CSS样式、图片等资源。
- **pom.xml**：Maven项目依赖和构建配置。

---

## 测试说明

本项目正在进行集成与测试工作，包括：

- **版本控制**：使用Git进行版本管理，远程仓库已配置
- **代码分析**：使用SonarLint进行静态代码分析
- **单元测试**：使用JUnit 5进行白盒测试
- **功能测试**：进行黑盒测试和边界值测试
- **持续集成**：配置GitHub Actions实现自动化构建和测试
- **性能测试**：使用JVisualVM和JMeter进行性能分析

测试相关文档和结果将逐步添加到项目中。

