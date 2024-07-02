package hello.configuration;


import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Inject;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Inject
    private UserService userService;
//
//    @Inject
//    public WebSecurityConfig(UserService userService) {
//        this.userService = userService;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/", "/auth/**").permitAll();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    // 通过重载，配置user-detail服务
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义的UserDetailsService，用于从数据库中获取用户信息，比如密码，权限等，这里使用我们自己实现的UserService
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    // 通过@Bean注解，将BCryptPasswordEncoder注册到Spring容器中,这是spring官方提供的服务
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // 返回一个BCryptPasswordEncoder实例,用于密码加密
        return new BCryptPasswordEncoder();
    }

}
