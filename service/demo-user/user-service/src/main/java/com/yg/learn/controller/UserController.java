package com.yg.learn.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yg.learn.api.dto.e.UserEnterDTO;
import com.yg.learn.api.dto.o.HomePage2DTO;
import com.yg.learn.api.dto.o.HomePageDTO;
import com.yg.learn.api.dto.o.UserOutDTO;
import com.yg.learn.common.core.basic.ResponseResult;
import com.yg.learn.common.core.basic.ResponseResultManager;
import com.yg.learn.domain.User;
import com.yg.learn.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Api(tags = "UserController", description = "用户管理")
@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ApiOperation("获取用户信息")
    @GetMapping("/{id}")
    public ResponseResult<UserOutDTO> getUser(@PathVariable Long id) {
        UserOutDTO user = userService.getDataSourceUser(id);
        if(user == null){
            return ResponseResultManager.setResultError(HttpStatus.HTTP_NOT_FOUND, String.format("ID输入错误 %s", id));
        }
        LOGGER.info("根据id获取用户信息，用户名称为：{}", user.getUsername());
        return ResponseResultManager.setResultSuccess(user);
    }

    @ApiOperation("获取首页信息")
    @GetMapping("/homepage")
    public ResponseResult<HomePageDTO> homePage() {
        HomePageDTO home = userService.gethomePage();
        return ResponseResultManager.setResultSuccess(home);
    }


    @ApiOperation("获取最新首页信息")
    @GetMapping("/newhomepage")
    public ResponseResult<HomePage2DTO> newhomepage() {
        HomePage2DTO home = userService.gethomePage2();
        return ResponseResultManager.setResultSuccess(home);
    }


    @GetMapping("/async")
    public CompletableFuture<String> async() {
        return CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "async";
            }
        );
    }

    @GetMapping("/sync")
    public String sync() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sync";
    }



    @GetMapping("/sentinel")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public ResponseResult<UserOutDTO> getSentinelUser() {
        UserOutDTO user = userService.getDataSourceUser(1L);
        if(user == null){
            return ResponseResultManager.setResultError(HttpStatus.HTTP_NOT_FOUND, String.format("1有问题"));
        }
        LOGGER.info("根据id获取用户信息，用户名称为：{}", user.getUsername());
        return ResponseResultManager.setResultSuccess(user);
    }



    @ApiOperation("保存用户信息")
    @GetMapping("/save")
    public ResponseResult<UserOutDTO> save() {
        UserEnterDTO userEnterDTO = new UserEnterDTO();
        userEnterDTO.setUsername("测试" + RandomUtil.randomNumbers(2));
        userEnterDTO.setPassword("密码" + RandomUtil.randomString(2));
        UserOutDTO userOutDTO = userService.insertData(userEnterDTO);
        return ResponseResultManager.setResultSuccess(userOutDTO);
    }


        /**
         * 这里必须根据byResource进行资源限流,根据url限流无法显示该信息
         * @param exception
         * @return
         */
        public ResponseResult<UserOutDTO> handleException(BlockException exception){
            return ResponseResultManager.setResultError(HttpStatus.HTTP_NOT_FOUND, String.format("限流"));
        }

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return configInfo;
    }


}
