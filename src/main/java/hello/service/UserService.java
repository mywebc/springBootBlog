package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
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