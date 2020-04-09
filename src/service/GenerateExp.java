package service;

import po.Expression;
import po.Fraction;
import po.Num;
import po.Result;
import util.CalculateUtil;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author InX
 */
public class GenerateExp {


    /**生成一个表达式
     * 输入：
     *      numRange    生成数的范围
     *      opList      操作列表，有哪些操作（加减乘除）
     *@author InX
     */
    public Expression generateAExp(int numRange, List<Integer> opList){
        GenerateExp g = new GenerateExp();

        Num exp1 = new Num();
        exp1.setNumOne(g.generateNum(numRange));
        exp1.setNumTwo(g.generateNum(numRange));
        exp1.setOperation(g.generateOp(opList));

        Num exp2 = new Num();
        exp2.setNumOne(g.generateNum(numRange));
        exp2.setNumTwo(g.generateNum(numRange));
        exp2.setOperation(g.generateOp(opList));

        Expression exp = new Expression();
        exp.setExceptionOne(exp1);
        exp.setExceptionTwo(exp2);
        exp.setExceptionOp(g.generateOp(opList));

        return exp;
    }
    /**
     * 生成结果集
     * @author InX
     */
    public List<Result> generateExpList(int numRange, int questionNum, List<Integer> opList) throws Exception {
        List<Result> questionList = new ArrayList<>();
        GenerateExp generateTool = new GenerateExp();
        Expression exp = generateTool.generateAExp(numRange,opList);
        for(int i = 0; i < questionNum; i++){
            exp = generateTool.generateAExp(numRange,opList);
            Result result = new Result();
            result.setExpression(generateTool.expToString(exp));
            //储存结果
            //result.setResult();
            String resultTemp = Fraction.resultFormat(CalculateUtil.calculate(result.getExpression()));
            if(resultTemp.contains("-")){
                i--;
                continue;
            }
            result.setResult(resultTemp);
            result.setId(i+1);
            result.setCorrect(true);
            questionList.add(result);
        }
        return questionList;
    }

    /**生成一个数
     *  输入：
     *      numRange    生成数的范围
     *@author InX
     */
    public String generateNum(int numRange){
        Random random = new Random();
        if(random.nextInt(2) == 0){
            //自然数
            return String.valueOf(random.nextInt(numRange-1)+1);
        }else {
            //生成一个分数
            //0:转换为分数；    1：转换为带分数
            //分数
            int numOne = random.nextInt(numRange-1)+1;
            int numTwo = random.nextInt(numRange-1)+1;
            if (numOne > numTwo) {
                //数1大于数2
                //随机成分数形式或带分数形式
                //0：分数形式   1：带分数形式
                if (random.nextInt(2) == 0) {
                    return numOne + "/" + numTwo;
                } else {
                    //带分数
                    //刚好整除
                    if (numOne % numTwo == 0) {
                        return numOne + "/" + numTwo;
                    }
                    int numerator = numOne % numTwo;
                    int denominator = numOne / numTwo;
                    //return
                    return denominator + "'" + numerator + "/" + numTwo;
                }
            } else if(numOne < numTwo){
                //数1小于数2
                return numOne + "/" + numTwo;
            }else{
                return String.valueOf(1);
            }
        }
    }

    /**检测输入的操作类型(加减乘除)，生成对应操作
     *@author InX
     */
    public int generateOp(List<Integer> opList){
        Random random = new Random();
        return opList.get(random.nextInt(opList.size()));
    }

    /**表达式转字符串
     * 将Expression转为String，用以图形界面输出
     *@author InX
     */
    public String expToString(Expression expression){
        /*String result = expression.getExceptionOne().getNumOne()
                +StringUtil.getOpChar(expression.getExceptionOne().getOperation())
                +expression.getExceptionOne().getNumTwo()
                +StringUtil.getOpChar(expression.getExceptionOp())
                +expression.getExceptionTwo().getNumOne()
                +StringUtil.getOpChar(expression.getExceptionOp())
                +expression.getExceptionTwo().getNumTwo();
        return result;*/
        Random random = new Random();
        if(random.nextInt(2) == 0){//三操作符
            int b1 = random.nextInt(2); //括号
            int b2 = random.nextInt(2); //括号
            StringBuilder result = new StringBuilder();
            result.append(expression.getExceptionOne().getNumOne()
                    +StringUtil.getOpChar(expression.getExceptionOne().getOperation())
                    +expression.getExceptionOne().getNumTwo());
            if(b1 == 0){
                result.insert(0,"(");
                result.append(")");
            }
            result.append(StringUtil.getOpChar(expression.getExceptionOp()));
            if(b2 == 0){
                result.append("(");
                result.append(expression.getExceptionTwo().getNumOne()
                        +StringUtil.getOpChar(expression.getExceptionOp())
                        +expression.getExceptionTwo().getNumTwo());
                result.append(")");
            }else{
                result.append(expression.getExceptionTwo().getNumOne()
                        +StringUtil.getOpChar(expression.getExceptionOp())
                        +expression.getExceptionTwo().getNumTwo());
            }
            return result.toString();
        }else {//两操作符
            String result = expression.getExceptionOne().getNumOne()
                    +StringUtil.getOpChar(expression.getExceptionOne().getOperation())
                    +expression.getExceptionOne().getNumTwo()
                    +StringUtil.getOpChar(expression.getExceptionOp())
                    +expression.getExceptionTwo().getNumOne();
            return result;
        }
    }
}
