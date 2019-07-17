import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @Author: gin
 * @Date: Created in 下午2:15 19-7-11
 * @Description:
 */
public class B5 {
    public static void main(String[] args) {
        int a[] = new int[10];  // 所求排列组合
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        final int FAC[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};   // 阶乘
        int x = 1000000-1;
        int flag=0;
        for (int i = 0; i < 10; i++) {
            int r = x % FAC[10 - 1 - i];
            int t = x / FAC[10 - 1 - i];

            a[i]=list.get(t);
//            System.out.println(t);
//            x=x-t*FAC[10 - 1 - i];
            x=r;
//            System.out.println(x);
            list.remove(t);
//            System.out.println(list);
        }
        for (int i = 0; i < 10; i++) {
            System.out.print(a[i]);
        }
    }
}