package hello.service;

import javax.inject.Inject;

public class OrderService {

    // orderService 依赖 userService 以前使用  @Autowire @Resource 现在可以使用@Inject
    private UserService userService;

    @Inject
    public OrderService(UserService userService){
        this.userService = userService;
    }

}
