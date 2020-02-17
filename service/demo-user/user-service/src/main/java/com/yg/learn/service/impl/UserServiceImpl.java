package com.yg.learn.service.impl;

import com.yg.learn.api.dto.UserDTO;
import com.yg.learn.api.dto.e.UserEnterDTO;
import com.yg.learn.api.dto.o.UserOutDTO;
import com.yg.learn.common.utils.BeanUtils;
import com.yg.learn.dao.UserMapper;
import com.yg.learn.domain.User;
import com.yg.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private UserMapper userMapper;
    private List<User> userList;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserOutDTO getDataSourceUser(Long id) {
        Optional<User> one = userMapper.findById(id);
        if(!one.isPresent()){
            return null;
        }
        User user = one.get();
        System.out.println(user);
        UserOutDTO userOutDTO = BeanUtils.transfrom(UserOutDTO.class, user);
        return userOutDTO;
    }

    @Override
    public UserOutDTO insertData(UserEnterDTO userEnterDTO) {
        User user = new User();
        user.setUsername(userEnterDTO.getUsername());
        user.setPassword(userEnterDTO.getPassword());
        User save = userMapper.save(user);
        UserOutDTO userOutDTO = BeanUtils.transfrom(UserOutDTO.class, save);
        return userOutDTO;
    }


    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        userList.add(new User(1L, "macro", "123456"));
        userList.add(new User(2L, "andy", "123456"));
        userList.add(new User(3L, "mark", "123456"));
    }
}
