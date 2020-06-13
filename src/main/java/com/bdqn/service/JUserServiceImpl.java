package com.bdqn.service;

import com.bdqn.dao.JUserMapper;
import com.bdqn.entity.JUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JUserServiceImpl implements JUserService {

    @Resource
    private JUserMapper jUserMapper;

    /**
     * 新签部查询接口
     * @param jUser
     * @return
     */
    @Override
    public JUser userLogin(JUser jUser) {
        return this.jUserMapper.userLogin(jUser);
    }

    /**
     * 总部查询接口
     * @param jUser
     * @return
     */
    @Override
    public JUser zbUserLogin(JUser jUser){
        return this.jUserMapper.zbUserLogin(jUser);
    }

    @Override
    public List<JUser> selectLoginAll() {
        return this.jUserMapper.selectLoginAll();
    }

    @Override
    public List<JUser> selectUserByCname(String name) {
        return this.jUserMapper.selectUserByUname(name);
    }

    /**
     * 根据手机号查询用户名(Realname)
     * @param phone
     * @return
     */
    @Override
    public String queryNameByPhone(String phone) {
        JUser juser =  this.jUserMapper.queryNameByPhone(phone);
        if(juser == null){
            return null;
        }
        return juser.getRealname();
    }
    /**
     * 根据手机号查询用户名(JUser)
     * @param phone
     * @return
     */
    public JUser queryNameByPhone1(String phone) {
        JUser juser =  this.jUserMapper.queryNameByPhone(phone);
        return juser;
    }

    /**
     * 根据用户id查询用户的信息
     * @param id
     * @return
     */
    @Override
    public JUser selectUserById(int id) {
        return this.jUserMapper.selectUserById(id);
    }

    /**
     * 根据扫码人的手机查询是否有这个人
     * @param juserPhone
     * @return
     */
    @Override
    public JUser queryJUserByPhone(String juserPhone) {
        JUser jUser = jUserMapper.queryJUserByPhone(juserPhone);
        return jUser;
    }
}
