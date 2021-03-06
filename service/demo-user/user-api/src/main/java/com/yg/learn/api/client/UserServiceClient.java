package com.yg.learn.api.client;

import com.yg.learn.api.client.fallback.UserServiceFallbackClient;
import com.yg.learn.api.constant.UserServerNameConstant;
import com.yg.learn.api.dto.UserDTO;
import com.yg.learn.api.dto.o.UserOutDTO;
import com.yg.learn.common.core.basic.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * SysUserServiceClient
		 */
@FeignClient(contextId = "userServiceClient",value = UserServerNameConstant.USER_SERVICE, fallback = UserServiceFallbackClient.class)
public interface UserServiceClient {

	@GetMapping("/user/{id}")
	ResponseResult<UserOutDTO> getUser(@PathVariable Long id);

	@GetMapping("/user/async")
	CompletableFuture<String> async();

	@GetMapping("/user/sync")
	String sync();




}
