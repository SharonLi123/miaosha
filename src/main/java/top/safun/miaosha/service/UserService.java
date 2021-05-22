package top.safun.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.safun.miaosha.dao.UserMapper;
import top.safun.miaosha.domain.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> getAllUser(){
        return userMapper.selectAllUser();
    }
}
