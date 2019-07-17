import java.math.BigInteger;
import java.util.Date;

/**
 * @Author: gin
 * @Date: Created in 下午1:49 19-7-11
 * @Description:
 */
public class B3 {
    public static void main(String[] args) {
//        final double sqrtFive=Math.sqrt(5);
//        BigInteger n=new BigInteger("100");
//        long result=(long) (1/sqrtFive*(Math.pow(((1+sqrtFive)/2),n)-Math.pow(((1-sqrtFive)/2),n)));
//        System.out.println(result);
//        result=(BigInteger) (1/sqrtFive*(Math.pow(((1+sqrtFive)/2),n)-Math.pow(((1-sqrtFive)/2),n)));
//        System.out.println(result);
        Date s = new Date();
        int n= (int) Math.pow(10,9);
        BigInteger a=new BigInteger("0");
        BigInteger b=new BigInteger("1");
        BigInteger c=new BigInteger("0");
        BigInteger d=new BigInteger("1000000007");
        for(int i=2;i<=n;++i){
            c = a.add(b).mod(d);
            a=b;
            b=c;
        }
        System.out.println(c);
        Date o=new Date();
        System.out.println(o.getTime()-s.getTime());
    }
}
