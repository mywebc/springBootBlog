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
            return LoginResult.success( "注销成功", true,null);
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> request) {
        String username = request.get("username").toString();
        String password = request.get("password").toString();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return LoginResult.failure("用户不存在");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return LoginResult.success( "login in success", true, userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return LoginResult.failure( "密码不正确");
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
            return LoginResult.failure( "user already exists");
        }
//        login(request, request);
//        return LoginResult.success("注册成功", userService.getUserByUsername(username));
        return LoginResult.success( "注册成功", false);

    }
}
