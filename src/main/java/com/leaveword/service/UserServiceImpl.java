package com.leaveword.service;

import com.alibaba.fastjson.JSON;
import com.leaveword.utils.CommonTools;
import com.leaveword.utils.Response;
import com.leaveword.domain.User;
import com.leaveword.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Response getUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> new Response("0", JSON.toJSONString(user))).orElseGet(() -> new Response("-1", "用户不存在"));
    }

    @Override
    public Response userRegister(String userName, String userPassword) {
        if(CommonTools.isEmpty(userName))
            return new Response("-1","用户名不能为空");
        if(CommonTools.isEmpty(userPassword))
            return new Response("-1","用户密码不能为空");
        try {
            if(userRepository.findByUserName(userName)!=null)
                return new Response("-1","此用户名已经存在");
            User user = new User();
            user.setUserName(userName);
            user.setUserPassword(userPassword);
            user.setRegisterTime(CommonTools.getCurrentTime());
            user = userRepository.save(user);
            user.setUserPassword("");
            return new Response("0", JSON.toJSONString(user));
        }catch (Exception e){
            return new Response("-1", "插入用户异常");
        }
    }

    @Override
    public Response userLogin(String userName, String userPassword) {
        if(CommonTools.isEmpty(userName))
            return new Response("-1","用户名不能为空");
        if(CommonTools.isEmpty(userPassword))
            return new Response("-1","用户密码不能为空");
        User user = userRepository.findByUserName(userName);
        if(user != null){
            if(user.getUserPassword().equals(userPassword)) {
                user.setUserPassword("");
                return new Response("0", JSON.toJSONString(user));
            }
            else
                return new Response("-1","密码错误");
        }
        else
            return new Response("-1","用户不存在");
    }

    public String get123(){
        return "0";
    }
}
