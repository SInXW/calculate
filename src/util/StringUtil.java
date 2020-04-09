package util;

/**
 * @author InX
 */
public class StringUtil {
    /**根据输入值，返回操作符号
     *@author SXH
     */
    public static String getOpChar(int op){
        switch (op){
            case 1:
                return " + ";
            case 2:
                return " - ";
            case 3:
                return " × ";
            case 4:
                return " ÷ ";
        }
        return "";
    }

    /**计算字符串算式并返回结果
     *@author SXH
     */
    public static int caculateString(String expression){
        return 0;
    }
}
