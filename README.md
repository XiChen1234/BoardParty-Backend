# BoardParty-Backend

TODO:
# 代码优化点分析报告

我已完成对你项目的全面代码审查。这是一个 **Spring Boot + MyBatis-Plus** 的后端项目，整体结构清晰。以下是我发现的主要优化点，按优先级分类：

---

## 🔴 高优先级 - 安全与性能问题

### 1. **密码明文存储 + 明文比较** 
**位置**: [AuthService.java:L39](file:///c:\Document\Project\BoardProject\BoardParty-Backend\src\main\java\com\xichen\Service\AuthService.java#L39)

```java
if (!user.getPassword().equals(password)) {
```

**问题**: 
- 密码是明文存储和比较，这是**严重的安全漏洞**
- 应使用 BCrypt 或其他哈希算法

**建议**: 
- 注册时对密码进行 BCrypt 哈希存储
- 登录时使用 `BCrypt.checkpw(rawPassword, hashedPassword)` 验证

---

### 2. **SQL 注入风险 - 动态查询未使用参数化**
**位置**: [GameService.java:L140](file:///c:\Document\Project\BoardProject\BoardParty-Backend\src\main\java\com\xichen\Service\GameService.java#L140)

```java
tagMapper.insert(newTagList);  // 批量插入标签
```

**问题**: MyBatis-Plus 的 `insert()` 方法在批量插入时，如果列表为空会报错

**建议**: 添加空列表判断，或使用 `saveBatch()` 方法

---

### 3. **JWT 密钥硬编码**
**位置**: [JwtUtil.java:L21-23](file:///c:\Document\Project\BoardProject\BoardParty-Backend\src\main\java\com\xichen\Security\JwtUtil.java#L21-L23)

```java
@Value("${jwt.secret}")
private String secret;
private final Long expire = (long) (1000 * 60 * 60 * 24 * 7);
```

**问题**: 密钥从配置文件读取是好的，但需确保配置中的密钥足够复杂（至少 256 位）

**建议**: 在 `application-prod.yml` 中确保使用足够复杂的密钥

---

### 4. **N+1 查询问题**
**位置**: [GameService.java:L36-82](file:///c:\Document\Project\BoardProject\BoardParty-Backend\src\main\java\com\xichen\Service\GameService.java#L36-L82) 的 `getAllGames()` 方法

```java
// 查询所有启用的游戏信息
List<Game> gameList = gameMapper.selectList(gameWrapper);
// 然后循环查询每个游戏的标签...
```

**问题**: 
- 3 次数据库查询（游戏 → 游戏标签 → 标签）
- 数据量大的情况下性能问题严重

**建议**: 考虑使用 JOIN 查询或 MyBatis-Plus 的联表查询功能，一次性获取所有数据

---

## 🟡 中优先级 - 代码质量

### 5. **Converter 类手动转换太繁琐**
**位置**: 多个 Converter 类

```java
// 每个字段都要手动 set
gameQueryDTO.setId(game.getId());
gameQueryDTO.setName(game.getName());
// ... 重复代码
```

**建议**: 考虑使用 MapStruct 自动化对象映射，或让 DO 类实现 `clone()` 方法

---

### 6. **空指针风险**
**位置**: [GameService.java:L48](file:///c:\Document\Project\BoardProject\BoardParty-Backend\src\main\java\com\xichen\Service\GameService.java#L48)

```java
gameTagWrapper.in(GameTag::getGameId, gameList.stream().map(Game::getId).toList());
```

**问题**: 如果 `gameList` 为空，`.toList()` 没问题，但如果 `gameTagList` 查询失败可能 NPE

**建议**: 使用 `Optional` 包装或提前返回空列表

---

### 7. **Random 对象重复创建**
**位置**: [PunishmentService.java:L32, L61](file:///c:\Document\Project\BoardProject\BoardParty-Backend\src\main\java\com\xichen\Service\PunishmentService.java#L32)

```java
Random random = new Random();
```

**问题**: 每次调用都创建新 Random 对象

**建议**: 使用 `ThreadLocalRandom.current()` 或将 Random 声明为静态常量

---

### 8. **未实现的方法占位符**
**位置**: [GroupController.java:L56-77](file:///c:\Document\Project\BoardProject\BoardParty-Backend\src\main\java\com\xichen\Controller\GroupController.java#L56-L77)

```java
@PostMapping("/join")
public CommonResponse<String> joinGroup() {
    return null;  // TODO
}
```

**问题**: 返回 `null` 可能导致前端解析错误

**建议**: 抛出 `UnsupportedOperationException` 或返回明确的错误响应

---

## 🟢 低优先级 - 最佳实践

### 9. **缺少分页查询**
`getAllGames()` 方法一次性加载所有游戏，无分页

---

### 10. **缺少请求限流**
登录接口 `/auth/login` 无验证码或限流机制，容易被暴力破解

---

### 11. **异常日志可增加追踪 ID**
[GlobalExceptionHandler.java](file:///c:\Document\Project\BoardProject\BoardParty-Backend\src\main\java\com\xichen\Handler\GlobalExceptionHandler.java) 可为每个请求生成唯一追踪 ID，便于问题排查

---

### 12. **常量定义**
[GroupService.java](file:///c:\Document\Project\BoardParty-Backend\src\main\java\com\xichen\Service\GroupService.java#L29-L30) 中有硬编码的默认头像和描述，建议移至配置文件

---

## 📋 优化优先级总结

| 优先级 | 问题 | 影响 |
|--------|------|------|
| 🔴 高 | 密码明文存储 | 安全漏洞 |
| 🔴 高 | N+1 查询 | 性能问题 |
| 🟡 中 | Converter 手动转换 | 维护成本 |
| 🟡 中 | Random 重复创建 | 轻微性能 |
| 🟢 低 | 分页缺失 | 可扩展性 |

---

如果你需要我针对某个具体问题提供详细的修复代码，请告诉我！