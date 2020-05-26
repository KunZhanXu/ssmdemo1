package com.bdqn.controller;

import com.bdqn.service.KnowledgeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("/web/knowledge")
public class KnowledgeController {
    @Resource
    private KnowledgeService knowledgeService;

    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(value = "filename") MultipartFile file,
                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("开始上传文件");
        //判断文件是否为空
        if (file == null) {
            return null;
        }
        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size = file.getSize();
        if (name == null || ("").equals(name) && size == 0) {
            return null;
        }

        //批量导入。参数：文件名，文件。
        boolean b = knowledgeService.batchImort(name, file);
//        boolean b = true;
        if (b) {
            String Msg = "批量导入EXCEL成功！";
            request.getSession().setAttribute("msg", Msg);
        } else {
            String Msg = "批量导入EXCEL失败！";
            request.getSession().setAttribute("msg", Msg);
        }
        return "web/knowledge";
    }
 
    @RequestMapping(value = "/fileDown", method = RequestMethod.GET)
    public void down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //模拟文件，myfile.txt为需要下载的文件
        String fileName = request.getSession().getServletContext().getRealPath("static/file/知识库导入模板.xls");
        System.out.println(fileName);
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
        //假如以中文名下载的话
        String filename = "知识库导入模板.xls";
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }
}