package com.bdqn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
 /**
  * 时间处理
  * @author XiaoZhu
  *
  */
public class DateConverter implements Converter<String, Date>{

    @Override
    public Date convert(String source) {
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      try {
               return simpleDateFormat.parse(source);

          } catch (Exception e) { 
               e.printStackTrace(); 
          } 
          return null;
    }

}