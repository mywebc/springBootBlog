package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    // 使用@Mock注解，模拟一个BCryptPasswordEncoder对象
    @Mock
    BCryptPasswordEncoder mockBCryptPasswordEncoder;
    // 使用@Mock注解，模拟一个UserMapper对象
    @Mock
    UserMapper mockWrapper;
    // 使用@InjectMocks注解，将UserService对象注入到测试类中
    @InjectMocks
    UserService userService;

    @Test
    public void testSave() {
        // given 当调用mockBCryptPasswordEncoder的encode方法时，返回"myEncodedPassword"
        when(mockBCryptPasswordEncoder.encode("myPassword")).thenReturn("myEncodedPassword");
        // when 调用userService的save方法
        userService.save("myUser", "myPassword");
        // then 验证是否调用了mockWrapper的save方法
        Mockito.verify(mockWrapper).save("myUser", "myEncodedPassword");
    }

    @Test
    public void testGetUserByUsername() {
        // when 调用userService的getUserByUsername方法
        userService.getUserByUsername("myUsername");
        // then 验证是否调用了mockWrapper的findUserByUsername方法
        Mockito.verify(mockWrapper).findUserByUsername("myUsername");
    }

    @Test
    public void throwExceptionWhenUserNotFound() {
        // when 调用userService的loadUserByUsername方法
        //  Assertions.assertThrows 用于验证是否抛出了指定的异常
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("myUser"));
    }

    @Test
    public void returnUserDetailsWhenUserFound() {
        // given 当调用mockWrapper的findUserByUsername方法时，返回一个User对象
        when(mockWrapper.findUserByUsername("myUser")).thenReturn(new User(123, "myUser", "myEncodedPassword"));
        // when 调用userService的loadUserByUsername方法
        UserDetails userDetails = userService.loadUserByUsername("myUser");
        // then 验证返回的UserDetails对象的属性值
        Assertions.assertEquals("myUser", userDetails.getUsername());
        Assertions.assertEquals("myEncodedPassword", userDetails.getPassword());
    }
}