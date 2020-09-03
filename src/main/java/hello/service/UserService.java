package hello.service;

import hello.mapper.UserMapper;

import javax.inject.Inject;

public class UserService {
    private UserMapper userMapper;
    // 将UserMapper注入进来
    @Inject
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserById(Integer id) {
        return userMapper.findUserById(id);
    }
}
