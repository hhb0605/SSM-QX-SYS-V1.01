package cn.qx.common.util;

/**
 * 校验参数工具
 * @author STK_Tofu
 * @date 2019年2月21日
 */
public class CheckValue {

    public static boolean checkPage(Integer a, Integer b) {
        if (a != null && a != 0 && b != null && b != 0) {
            return true;
        }
        return false;
    }

    public static boolean checkPageIds(Integer a, Integer b, Integer c) {
        if (a != null && a != 0 && b != null && b != 0 && c != null && c != 0) {
            return true;
        }
        return false;
    }

    public static boolean checkId(Long id) {
        if (id != null && id != 0) {
            return true;
        }
        return false;
    }

    public static boolean checkObj(Object obj) {
        if (obj != null) {
            return true;
        }
        return false;
    }

    public static boolean checkIds(Long... ids) {
        if (ids != null && ids.length > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean checkString(String string) {
        if(string != null && !string.equals("")) {
            return true;
        }
        return false;
    }
}
