package org.landy.springtestdemo.controller;


import org.junit.jupiter.api.Test;
import org.landy.springtestdemo.domain.User;
import org.landy.springtestdemo.service.UserRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@link UserController} 测试
 *
 * @author Landy
 * @copyright Landy
 * @since 2018/1/18
 */
@SpringJUnitConfig(classes = {UserController.class, UserControllerTest.TestConfiguration.class})
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void testFindAll() {
        // 这个返回结果 UserController#findAll() -> UserRemoteService#findAll();
        // 实际调用时 Mock UserRemoteService Bean
        List<User> users = userController.findAll();
        assertEquals(1, users.size());
        assertEquals("Landy", users.get(0).getName());
    }


    /**
     * Test 配置
     */
    @Configuration
    public static class TestConfiguration {

        // 通过 Mockito Mock UserRemoteService 作为 Spring Bean
        @Bean
        public UserRemoteService userRemoteService() {
            //Mock代理对象
            UserRemoteService userRemoteService = mock(UserRemoteService.class);
            User user = new User();
            user.setId(1L);
            user.setName("Landy");
            when(userRemoteService.findAll()).thenReturn(Arrays.asList(user));
            return userRemoteService;
        }
    }
}
