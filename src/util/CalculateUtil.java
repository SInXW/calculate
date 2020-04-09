package util;

import po.Fraction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CalculateUtil {

    /**递归实现的对字符串的计算
     *@author InX
     */
    public static String calculate(String exp) throws NumberFormatException, Exception {
        //计算过程中中出现"- -"
        exp = exp.startsWith("- -") ? exp.substring(2) : exp;
        //首位"- -"去掉
        exp = exp.replaceAll("- -", "+ ");
        //中间"- -"变"+ "
        exp = exp.replaceAll("\\+ -", "- ");
        if (exp.matches("-{0,1}[0-9]+(['][0-9]+){0,1}+([/][0-9]+){0,1}+([.][0-9]+){0,1}"))//匹配一个有效数，即不存在运算符
            return exp;
        /*表示每次递归计算完一步后的表达式*/
        String newExpr = null;

        // 第一步：去括号至无括号
        if (exp.contains("(")) {
            //最后一个左括号的位置
            int lIndex = exp.lastIndexOf("(");
            //左括号对应的右括号位置
            int rIndex = exp.indexOf(")", lIndex);
            //截取括号内容
            String subExpr = exp.substring(lIndex + 1, rIndex);
            newExpr = exp.substring(0, lIndex) + calculate(subExpr) //调用本身，计算括号中表达式结果
                    + exp.substring(rIndex + 1);
            return calculate(newExpr);
        }

        // 第二步：去乘除至无乘除
        if (exp.contains("×") || exp.contains("÷")) {
            //匹配一个乘除运算
            Pattern p = Pattern.compile("-{0,1}[0-9]+(['][0-9]+){0,1}+([/][0-9]+){0,1}+([.][0-9]+){0,1}[ ][×÷][ ][0-9]+(['][0-9]+){0,1}+([/][0-9]+){0,1}+([.][0-9]+){0,1}");
            Matcher m = p.matcher(exp);
            if (m.find()) {
                /*第一个乘除表达式*/
                String temp = m.group();
                //拆分字符串，获得两个操作数
                String[] a = temp.split("[ ][×÷][ ]");
                Fraction fraction = new Fraction();
                //操作数转为分数形式
                Fraction fraction1 = fraction.toFraction(a[0]);
                Fraction fraction2 = fraction.toFraction(a[1]);

                if(temp.charAt(a[0].length()+1) == '×'){//乘法运算
                    newExpr = exp.substring(0, m.start())
                            + fraction.multiply(fraction1, fraction2).toString()
                            + exp.substring(m.end());
                }else{//除法运算
                    newExpr = exp.substring(0, m.start())
                            + fraction.devide(fraction1, fraction2).toString()
                            + exp.substring(m.end());
                }
            }
            return calculate(newExpr);
        }

        // 第三步：去加减至无加减
        if (exp.contains("+") || exp.contains("-")) {
            //匹配一个乘除运算
            Pattern p = Pattern.compile("-{0,1}[0-9]+(['][0-9]+){0,1}+([/][0-9]+){0,1}+([.][0-9]+){0,1}[ ][+-][ ][0-9]+(['][0-9]+){0,1}+([/][0-9]+){0,1}+([.][0-9]+){0,1}");
            Matcher m = p.matcher(exp);
            if (m.find()) {
                /*第一个加减表达式*/
                String temp = m.group();
                //拆分字符串，获得两个操作数
                String[] a = temp.split("\\b[ ][+-][ ]", 2);
                Fraction fraction = new Fraction();
                //操作数转为分数形式
                Fraction fraction1 = fraction.toFraction(a[0]);
                Fraction fraction2 = fraction.toFraction(a[1]);

                if(temp.charAt(a[0].length()+1) == '+'){//加法运算
                    newExpr = exp.substring(0, m.start())
                            + fraction.add(fraction1, fraction2).toString()
                            + exp.substring(m.end());
                }else{//减法运算
                    newExpr = exp.substring(0, m.start())
                            + fraction.sub(fraction1, fraction2).toString()
                            + exp.substring(m.end());
                }
            }
            return calculate(newExpr);
        }
        throw new Exception("Error!");
    }
}
