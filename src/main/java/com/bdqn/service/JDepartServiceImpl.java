package com.bdqn.service;

import com.bdqn.dao.JDepartMapper;
import com.bdqn.dao.JUserMapper;
import com.bdqn.entity.JDepart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JDepartServiceImpl implements JDepartService {
    @Autowired
    private JDepartMapper jDepartMapper;

    @Override
    public JDepart queryDepartById(Integer departId) {
        return jDepartMapper.selectDepartById(departId);
    }
}
