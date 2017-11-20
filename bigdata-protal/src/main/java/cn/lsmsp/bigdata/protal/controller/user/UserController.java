package cn.lsmsp.bigdata.protal.controller.user;

import cn.lsmsp.bigdata.user.TbUserService;
import cn.lsmsp.common.pojo.BigdataResult;
import cn.lsmsp.common.pojo.user.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {

    @Autowired
    private TbUserService tbUserService;

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String getUser(Model model,@PathVariable Integer id) {

        return "/user/register";
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    public BigdataResult addUser(TbUser user) {
        tbUserService.saveUser(user.getUsername(),user.getPassword(),user.getEmail());
        return BigdataResult.ok();
    }

}
