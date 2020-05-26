package com.bdqn.service;

import com.bdqn.entity.TKnowledge;
import org.springframework.web.multipart.MultipartFile;

public interface KnowledgeService {

    public boolean batchImort(String name, MultipartFile file);

    public int insert(TKnowledge record);
}
