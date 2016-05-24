/**
 * Copyright © 2016陕西天润有限责任公司. All rights reserved.
 *
 * @Title: StringHandle.java
 * @Prject: trmap-personal
 * @Package: com.trgis.trmap.personal.util
 * @Description: TODO
 * @author: chlei  
 * @date: 2016年3月9日 下午6:21:04
 * @version: V1.0  
 */
package com.trgis.trmap.personal.util;

/**
 * @ClassName: StringHandle
 * @Description: TODO
 * @author: chlei
 * @date: 2016年3月9日 下午6:21:04
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

public class StringHandle {
	

	/**
	 * 判断字段是否为非空
	 * @param obj
	 * @return boolean
	 */
	 public static boolean isNotEmpty(String obj) {
	        return obj != null && !obj.trim().equals("") && !obj.equals("null");
	  }
	 
	 /**
		 * 判断字段是否为空
		 * @param obj
		 * @return boolean
		 */
	 public static boolean isEmpty(String obj) {
	        return obj == null || obj.equals("") || obj.equals("null");
	  }
	 /**
	     * 去除html代码
	     * @param inputString
	     * @return
	     */
    public static String HtmltoText(String inputString) {
    	if (isEmpty(inputString)) {
			return "";
		}
        String htmlStr = inputString; //含html标签的字符串
        String textStr ="";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;          
        java.util.regex.Pattern p_ba;
        java.util.regex.Matcher m_ba;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[.[^<]]*>|&\\w+;"; //定义HTML标签的正则表达式
            String patternStr = "\\s+";
            
            p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签
         
            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签
            
            p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll(""); //过滤空格
            textStr = htmlStr;
        }catch(Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }          
        return textStr;//返回文本字符串
     }
    /**
     * Object类型转化为String
     * 
     * @param obj
     *          Object参数类型
     * @return 返回String类型
     */
    public static String objectToString(Object obj) {
      return objectToString(obj, "");
    }

    /**
     * Object类型转化为String
     * 
     * @param obj
     *          Object参数类型
     * @param defValue
     *          默认值
     * 
     * @return 返回String类型
     */
    public static String objectToString(Object obj, String defValue) {

      try {
        return obj.toString();
      } catch (Exception ex) {
        return defValue;
      }
    }

    /**
     * Object类型转化为int
     * 
     * @param obj
     *          Object参数类型
     * @return 返回int类型
     */
    public static int objectToInt(Object obj) {
      return objectToInt(obj, -1);
    }

    /**
     * Object类型转化为int
     * 
     * @param obj
     *          Object参数类型
     * @param defValue
     *          默认值
     * 
     * @return 返回int类型
     */
    public static int objectToInt(Object obj, int defValue) {
      try {
        return Integer.parseInt(obj.toString());
      } catch (Exception ex) {
        return defValue;
      }
    }

    /**
     * Object类型转化为Long
     * 
     * @param obj
     *          Object参数类型
     * @return 返回Long类型
     */
    public static Long objectToLong(Object obj) {
      return objectToLong(obj, new Long(-1));
    }

    /**
     * Object类型转化为Long
     * 
     * @param obj
     *          Object参数类型
     * @param defValue
     *          默认值
     * 
     * @return 返回Long类型
     */
    public static Long objectToLong(Object obj, Long defValue) {
      try {
        if (obj == null) {
          return defValue;
        }
        return new Long(obj.toString());
      } catch (Exception ex) {
        return defValue;
      }
    }

    /**
     * 将格式化的金额字符串，转换成double型。
     * 
     * @param obj
     * @return
     */
    public static Double objectToCurrency(Object obj, Double defValue) {
      try {
        return new Double(obj.toString().replaceAll(",", ""));
      } catch (Exception ex) {
        return defValue;
      }
    }

    /**
     * 将格式化的金额字符串，转换成double型。 如不能转换，则返回null
     * 
     * @param obj
     * @return
     */
    public static Double objectToCurrency(Object obj) {
      return objectToCurrency(obj, null);
    }

    /**
     * 将格式化的金额字符串，转换成double型。
     * 
     * @param str
     *          金额字符串。如123,345.23
     * @param defValue
     * @return
     */
    public static Double stringToCurrency(String str, Double defValue) {
      try {
        return new Double(str.replaceAll(",", ""));
      } catch (Exception ex) {
        return defValue;
      }
    }

    /**
     * 将格式化的金额字符串，转换成double型。 如不能转换，则返回null
     * 
     * @param str
     *          金额字符串。如123,345.23
     * @return
     */
    public static Double stringToCurrency(String str) {
      return stringToCurrency(str, null);
    }

    public static Double objectToDouble(Object value) {
      return objectToDouble(value, null);
    }

    public static Double objectToDouble(Object value, Double defValue) {
      if (value == null) {
        return defValue;
      }
      try {
        return new Double(value.toString());
      } catch (Exception e) {
        return defValue;
      }
    }

    public static double objectTodouble(Object value) {
      return objectToDouble(value, 0);
    }

    public static double objectToDouble(Object value, double defValue) {
      if (value == null) {
        return defValue;
      }
      try {
        return new Double(value.toString()).doubleValue();
      } catch (Exception e) {
        return defValue;
      }
    }

    public static Object doubleToObject(Double value) {
      return doubleToObject(value, null);
    }

    public static Object doubleToObject(Double value, Object defValue) {
      if (value == null) {
        return defValue;
      }
      try {
        return value.toString();
      } catch (Exception e) {
        return defValue;
      }
    }
    
	/**
	 * 判断可变参数是否为空
	 * @param params
	 * @return true 有为空项,false 没有非空项
	 */
    public static boolean checkEmpty(String...params){
		for(String obj:params){
			if(StringHandle.isEmpty(obj)) return true;
		}
		return false;
		
	}
    
    /**
     * 判断对象是否为空
     * @param obj
     * @return true 不空,false 空
     */
    public static boolean isNotEmpty(Object obj) {
        if (obj != null) {
          if (obj instanceof String)
            return ((String) obj).trim().length() > 0;
          if (obj instanceof StringBuffer)
            return ((StringBuffer) obj).toString().trim().length() > 0;
          if (obj instanceof List)
            return ((List) obj).size() > 0;
          if (obj instanceof Vector)
            return ((Vector) obj).size() > 0;
          if (obj instanceof HashMap)
            return ((HashMap) obj).size() > 0;
          if (obj instanceof Iterator)
            return ((Iterator) obj).hasNext();
          return true;
        }
        return false;
   }
   
    public static boolean isEmpty(Object obj) {
        return !isNotEmpty(obj);
      }
    
}

