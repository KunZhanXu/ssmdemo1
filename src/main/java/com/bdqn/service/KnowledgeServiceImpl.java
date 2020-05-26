package com.bdqn.service;

import com.bdqn.entity.TKnowledge;
import com.bdqn.util.ReadExcel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
//    @Resource
//    private TKnowledgeMapper knowledgeMapper;
    @Override
    public boolean batchImort(String name, MultipartFile file) {
        boolean b = false;
        //创建处理EXCEL
        ReadExcel readExcel=new ReadExcel();
        //解析excel，获取客户信息集合。
        List<TKnowledge> knowledgeList = readExcel.getExcelInfo(name ,file);

        if(knowledgeList != null){
            b = true;
        }
        //迭代添加客户信息（注：实际上这里也可以直接将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加。）
        for(TKnowledge knowledge:knowledgeList){
//            knowledgeMapper.insert(knowledge);
        }
        return b;
    }

    @Override
    public int insert(TKnowledge record) {
//        return knowledgeMapper.insert(record);//插入一条数据
        return 0;
    }
}
