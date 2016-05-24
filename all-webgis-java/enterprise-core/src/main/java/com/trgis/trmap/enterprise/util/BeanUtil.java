package com.trgis.trmap.enterprise.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.math.NumberUtils;


public class BeanUtil {

  /**
     * 把HashMap对象的内容转换为某个 bean 类的对象 HashMap格式 (name=property value==属性值)
     * 
     * @param hm
     * @param className
     * @return
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.Exception
     */
  public static Object HashMapToObject(HashMap<?, ?> hm, String className)
      throws ClassNotFoundException, Exception {
    Object obj = Class.forName(className).newInstance();
    Iterator<?> keySetIterator = hm.keySet().iterator();
    Iterator<?> valuesIterator = hm.values().iterator();
    while (keySetIterator.hasNext()) {
      String beanProperty = null;
      Object objColumn = keySetIterator.next();
      Object objValue = valuesIterator.next();
      beanProperty = (String) objColumn;

      BeanUtils.setProperty(obj, beanProperty, objValue);
    }
    return obj;
  }

  /**
     * <str>字符串 转换成 整型，如果不能转换，则返回默认值<defInt>
     * 
     * @param str
     * @param defInt
     * @return
     */
  public static int StrToInt(String str, int defInt) {
    try {
      return Integer.parseInt(str);
    } catch (Exception e) {
      return defInt;
    }
  }

  /**
     * 对象转换成int值，转换失败则返回0
     * 
     * @param obj
     * @return
     */
  public static int toInt(Object obj) {
    return toInt(obj, 0);
  }

  /**
     * 对象转换成int值，转换失败则返回默认值
     * 
     * @param obj
     * @param defInt
     * @return
     */
  public static int toInt(Object obj, int defInt) {
    try {
      return Integer.parseInt(obj.toString());
    } catch (Exception e) {
      return defInt;
    }
  }

  /**
     * 对象转换成double，如果失败则返回0.0
     * 
     * @param obj
     * @return
     */
  public static double toDouble(Object obj) {
    return toDouble(obj, 0.00);
  }
  
  /**
     * 对象转换成double，如果失败则返回defDbl
     * 
     * @param obj
     * @param defDbl
     * @return
     */
  public static double toDouble(Object obj, double defDbl) {
    try {
      return Double.parseDouble(obj.toString());
    } catch (Exception e) {
      return defDbl;
    }
  }
	 /**
	 * @param value
	 * @return
	 */
	public static boolean isNumber(String value) {
		  try {
			  if(NumberUtils.isNumber(value))
				  return true;
			  else 
				  return false;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }
	public static void main(String[] args) {
		System.out.println(BeanUtil.isNumber("ry"));
	}
  public static String toStr(Object obj) {
	  if(obj == null || "".equals(obj)){
		  return "";
	  }
	    return toString(obj);
  }
  /**
     * 判断对象是否为null
     * 
     * @param obj
     * @return
     */
  public static boolean isNull(Object obj) {
    return obj == null;
  }

  /**
     * 判断对象是否不为null
     * 
     * @param obj
     * @return
     */
  public static boolean isNotNull(Object obj) {
    return obj != null;
  }

  /**
     * 判断对象内容是否为 空
     * 
     * @param obj
     * @return
     */
  public static boolean isEmpty(Object obj) {
    return !isNotEmpty(obj);
  }

  /**
     * 判断对象内容是否为 非空
     * 
     * @param obj
     * @return
     */
  public static boolean isNotEmpty(Object obj) {
    if (obj != null) {
      if (obj instanceof String)
        return ((String) obj).trim().length() > 0;
      if (obj instanceof StringBuffer)
        return ((StringBuffer) obj).toString().trim().length() > 0;
      if (obj instanceof List)
        return ((List<?>) obj).size() > 0;
      if (obj instanceof Vector)
        return ((Vector<?>) obj).size() > 0;
      if (obj instanceof HashMap)
        return ((HashMap<?, ?>) obj).size() > 0;
      if (obj instanceof Iterator)
        return ((Iterator<?>) obj).hasNext();
      return true;
    }
    return false;
  }
  public static String toString(Object obj) {
	    return toString(obj, "");
	  }

  public static String toString(Object obj, String def) {
	    try {
	      return obj.toString();
	    } catch (Exception ex) {
	      return def;
	    }
	  }
  public static Date formatDate(int n ) {
	  Calendar c = Calendar.getInstance();
	  c.add(Calendar.YEAR, n);
	  return c.getTime();
  }
  public static String datetostring(Date date){
	  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	  String sdate = df.format(date);
	  return sdate;
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
}