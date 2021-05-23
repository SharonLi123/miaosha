package top.safun.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.safun.miaosha.dao.MiaoshaUserDao;
import top.safun.miaosha.domain.MiaoshaUser;
import top.safun.miaosha.exception.GlobalException;
import top.safun.miaosha.result.CodeMessage;
import top.safun.miaosha.result.Result;
import top.safun.miaosha.util.MD5Util;
import top.safun.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserService {

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    public MiaoshaUser getById(long id){
        return miaoshaUserDao.getById(id);
    }

    public boolean login(LoginVo loginVo){
        if (loginVo==null){
            throw new GlobalException(CodeMessage.SERVER_ERROR);
        }
        String mobile=loginVo.getMobile();
        String password=loginVo.getPassword();

        MiaoshaUser user=getById(Long.parseLong(mobile));
        if (user==null){
            throw new GlobalException(CodeMessage.MOBILE_NOT_EXIST);
        }

        String dbPass=user.getPassword();
        String salt=user.getSalt();
        String calcPass= MD5Util.formPassToDBPass(password,salt);
        if (!dbPass.equals(calcPass)){
            throw new GlobalException(CodeMessage.PASSWORD_ERROR);
        }
        return true;
    }
}
