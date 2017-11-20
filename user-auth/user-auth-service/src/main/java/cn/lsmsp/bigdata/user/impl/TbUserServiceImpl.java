package cn.lsmsp.bigdata.user.impl;

import cn.lsmsp.bigdata.user.TbUserService;
import cn.lsmsp.bigdata.user.dao.TbUserDao;
import cn.lsmsp.common.pojo.user.TbUser;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    private TbUserDao tbUserDao;

    @Autowired
    private PasswordService passwordService;

    @Override
    public void saveUser(String username, String password, String email) {
        TbUser user = new TbUser();
        String encryptPassword = passwordService.encryptPassword(password);
        user.setEmail(email);
        user.setPassword(encryptPassword);
        user.setUsername(username);
        Date date = new Date();
        user.setCreatedTime(date);
        user.setUpdatedTime(date);
        tbUserDao.saveUser(user);
    }
}
