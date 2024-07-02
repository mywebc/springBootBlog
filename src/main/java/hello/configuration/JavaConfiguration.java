package hello.configuration;

import org.springframework.context.annotation.Configuration;
// 我们可以在这个文件里声明bean，然后在其他地方使用@Autowired（或者构造函数）注解来注入这些bean
// 我们也可以直接使用注解的方式来声明bean，比如在hello.service.UserService上加上@Component/@Service注解
@Configuration
public class JavaConfiguration {

//    @Bean
//    public UserService userService(UserMapper userMapper) {
//        return new UserService(userMapper);
//    }
}
