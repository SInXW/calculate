package po;

/**
 * @author InX
 */
public class Fraction {
    private long Numerator; // 分子
    private long Denominator; // 分母

    public Fraction(long numerator, long denominator) {
        this.Numerator = numerator;
        if (denominator == 0) {
            throw new ArithmeticException("分母不能为零");
        } else {
            this.Denominator = denominator;
        }
        change();
    }

    public Fraction() {
        this(0, 1);
    }

    public long getNumerator() {
        return Numerator;
    }

    public void setNumerator(long numerator) {
        Numerator = numerator;
    }

    public long getDenominator() {
        return Denominator;
    }

    public void setDenominator(long denominator) {
        Denominator = denominator;
    }

    private Fraction change() {
        long gcd = this.gcd(this.Numerator, this.Denominator);
        if(gcd < 0) gcd = -gcd;
        this.Numerator /= gcd;
        this.Denominator /= gcd;
        return this;
    }

    /**
     * 最大公因数
     *
     * @param a
     * @param b
     * @return
     */
    private long gcd(long a, long b) {
        long mod = a % b;
        if (mod == 0) {
            return b;
        } else {
            return gcd(b, mod);
        }
    }

    /**
     * 四则运算
     * @return
     */
    public Fraction add(Fraction one, Fraction second) {
        return new Fraction(one.Numerator * second.Denominator + second.Numerator * one.Denominator,
                one.Denominator * second.Denominator);
    }

    public Fraction sub(Fraction one, Fraction second) {
        return new Fraction(one.Numerator * second.Denominator - second.Numerator * one.Denominator,
                one.Denominator * second.Denominator);
    }

    public Fraction multiply(Fraction one, Fraction second) {
        return new Fraction(one.Numerator*second.Numerator,
                one.Denominator * second.Denominator);
    }

    public Fraction devide(Fraction one, Fraction second) {
        return new Fraction(one.Numerator*second.Denominator,
                one.Denominator * second.Numerator);
    }

    /**操作数转分数形式
     *@author InX
     */
    public Fraction toFraction(String num){
        if(num.contains("/")){//分数
            if(num.contains("'")){//带分数
                //以分号'/'分割，如：带分数1'2/3分割为1'2     3
                String[] sp = num.split("/");
                //以符号'''分割，如：1'2分割为1    2
                String[] ca = sp[0].split("'");
                Fraction fraction = new Fraction();
                //分子
                fraction.setNumerator(Long.valueOf(ca[0])*Long.valueOf(sp[1])+Long.valueOf(ca[1]));
                //分母
                fraction.setDenominator(Long.valueOf(sp[1]));
                return fraction;
            }else{//真分数
                String[] sp = num.split("/");
                Fraction fraction = new Fraction();
                fraction.setNumerator(Long.valueOf(sp[0]));
                fraction.setDenominator(Long.valueOf(sp[1]));
                return fraction;
            }

        }else{//整数,将表达式中的整数也转为分数
            Fraction fraction = new Fraction();
            fraction.setNumerator(Long.valueOf(num));
            fraction.setDenominator(1);
            return fraction;
        }
    }
    /**
     *@author InX
     */
    /**检测操作数是否为分数
     *@author InX
     */
    public boolean hasFraction(String num1, String num2){
        return num1.contains("/")||num2.contains("/");
    }
    @Override
    public String toString() {
        return String.format("%d/%d", this.Numerator, this.Denominator);
    }
    /**结果形式转化
     * 真分数返回
     * 假分数转为带分数
     *@author InX
     */
    public static String resultFormat(String num){
        String[] sp = num.split("/");
        int n1 = Integer.parseInt(sp[0]);
        int n2 = Integer.parseInt(sp[1]);
        if(n1 >= n2){
            if(n1 % n2 == 0){
                return String.valueOf(n1/n2);
            }
            int d = n1/n2;
            return d+"'"+n1%n2+"/"+n2;
        }else{
            if(n1 == 0){
                return String.valueOf(0);
            }
            return num;
        }
    }

    public static void main(String[] args) {
        String s = "1/8";
        Fraction.resultFormat(s);

    }
}
