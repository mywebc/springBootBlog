package hello.controller;

import hello.entity.LoginResult;
import hello.entity.Result;
import hello.entity.User;
import hello.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Inject
    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Result auth() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 通过查看username是否存在判断用户是否登录
        User loggedInUser = userService.getUserByUsername(authentication == null ? null : authentication.getName());

        if (loggedInUser == null) {
            return LoginResult.failure("user not login in");
        } else {
            return LoginResult.success("success", true, loggedInUser);
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Object logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 通过查看username是否存在判断用户是否登录
        User loggedInUser = userService.getUserByUsername(username);

        if (loggedInUser == null) {
            return LoginResult.failure("user not login in");
        } else {
            SecurityContextHolder.clearContext();
            return LoginResult.success("注销成功", true, null);
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        UserDetails userDetails;
        try {
            // 根据用户名查找用户相关信息
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return LoginResult.failure("用户不存在");
        }
        // UsernamePasswordAuthenticationToken是Authentication的实现类，封装了用户的信息，包括用户名、密码、权限等，用于鉴权，
        // 是一个不可变对象，创建后不可更改，所以需要重新创建一个对象，然后设置到SecurityContextHolder中，这样才能保证用户登录成功，否则会报错。
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            // 通过AuthenticationManager的authenticate方法实现登录，该方法会调用UserDetailsService的loadUserByUsername方法获取用户信息
            authenticationManager.authenticate(token);
            // 如果鉴权成功，将用户信息存储到SecurityContextHolder中，方便获取用户信息，如用户名、权限等，
            // 这样就可以通过SecurityContextHolder.getContext().getAuthentication()获取用户信息，如用户名、权限等。
            SecurityContextHolder.getContext().setAuthentication(token);
            return LoginResult.success("login in success", true, userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return LoginResult.failure("密码不正确");
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        if (username == null || password == null) {
            return LoginResult.failure("username/password == null");
        }
        if (username.length() < 1 || username.length() > 15) {
            return LoginResult.failure("username/password length invalid");
        }
        if (password.length() < 1 || password.length() > 15) {
            return LoginResult.failure("invalid password");
        }

        try {
            userService.save(username, password);
        } catch (DuplicateKeyException e) {
            return LoginResult.failure("user already exists");
        }
//        login(request, request);
//        return LoginResult.success("注册成功", userService.getUserByUsername(username));
        return LoginResult.success("注册成功", false);

    }
}
