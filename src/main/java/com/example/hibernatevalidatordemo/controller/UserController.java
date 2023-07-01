package com.example.hibernatevalidatordemo.controller;

import com.example.hibernatevalidatordemo.pojo.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    // 用户列表
    private List<User> userList = new ArrayList<>();

    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public String add(@Validated @RequestBody User user) {
        // 用户 id
        Long userId = Long.valueOf(userList.size() + 1);
        // 设置用户 id
        user.setId(userId);
        // 将用户信息添加到用户列表中
        userList.add(user);
        StringBuffer result = new StringBuffer();
        result.append("添加用户 ").append(user.getUsername()).append(" 成功！");
        return result.toString();
    }

    /**
     * 获取用户信息
     *
     * @param id 用户 id
     * @return
     */
    @GetMapping("/info")
    public Map<String, Object> info(@NotNull(message = "用户 id 不能为空") Long id) {
        Map<String, Object> result = new HashMap<>();
        // 查找用户
        Optional<User> findUserResult = userList.stream().filter(user -> user.getId() == id).findFirst();
        if (findUserResult.isPresent()) {
            // 用户存在
            User user = findUserResult.get();
            result.put("userInfo", user);
        } else {
            // 用户不存在
            result.put("userInfo", "不存在该用户！");
        }
        return result;
    }
}
