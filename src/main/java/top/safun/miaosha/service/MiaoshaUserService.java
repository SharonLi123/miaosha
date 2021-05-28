package top.safun.miaosha.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.safun.miaosha.dao.MiaoshaUserDao;
import top.safun.miaosha.domain.MiaoshaUser;
import top.safun.miaosha.exception.GlobalException;
import top.safun.miaosha.redis.MiaoshaUserKey;
import top.safun.miaosha.redis.RedisService;
import top.safun.miaosha.result.CodeMessage;
import top.safun.miaosha.util.MD5Util;
import top.safun.miaosha.util.UUIDUtil;
import top.safun.miaosha.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public static final String COOKIE_NAME_TOKEN="token";

    public MiaoshaUser getById(long id){
        return miaoshaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response,LoginVo loginVo){
        if (loginVo==null){
            throw new GlobalException(CodeMessage.SERVER_ERROR);
        }
        String mobile=loginVo.getMobile();
        String password=loginVo.getPassword();

        MiaoshaUser user=getById(Long.parseLong(mobile));
        if (user==null){
            throw new GlobalException(CodeMessage.MOBILE_NOT_EXIST);
        }

        //验证密码
        String dbPass=user.getPassword();
        String salt=user.getSalt();
        String calcPass= MD5Util.formPassToDBPass(password,salt);
        if (!dbPass.equals(calcPass)){
            throw new GlobalException(CodeMessage.PASSWORD_ERROR);
        }


        //生成token
        String token=UUIDUtil.uuid();
        //设置cookies
        addCookie(response,user,token);
        return true;
    }

    public MiaoshaUser getByToken(HttpServletResponse response,String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user=redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        if (user==null){
            return null;
        }
        addCookie(response,user,token);
        return user;
    }

    public void addCookie(HttpServletResponse response,MiaoshaUser user,String token){
        if (user==null){
            return;
        }

        if(StringUtils.isEmpty(token)){
            return;
        }
        redisService.set(MiaoshaUserKey.token,token,user);
        //创建cookie
        Cookie cookie=new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        //将cookie加入response中
        response.addCookie(cookie);
    }
}
