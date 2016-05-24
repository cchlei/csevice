package com.trgis.trmap.hstore;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class BeanUtil {



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


}