package Interview.javaCode.zzke;
/**
 * @Author: gin
 * @Date: Created in 下午3:26 19-7-11
 * @Description:
 */
public class B5_2 {
    public static void main(String[] args) {
        int MAX = 1000000 - 1;
        int[] tempn = new int[9];
        int[] num = new int[10];
        int[] answer = new int[10];
        int NUM = 1;
        int n;
        num[0] = 0;
        for (int i = 1; i < 10; i++) {
            NUM *= i;
            tempn[i - 1] = NUM;
            num[i] = i;
        }
        for (int i = 8; i >= 0; i--) {
            n = MAX / tempn[i];
            int k = 0;
            int m = 8 - i;
            while (n > 0) {
                n--;
                k++;
                while (num[k] == -1)
                    k++;
            }
            answer[m] = num[k];
            num[k] = -1;
            MAX %= tempn[i];
        }
        for (int i = 0; i < 10; i++) {
            if (num[i] != -1) {
                answer[9] = num[i];
                break;
            }
        }
        for (int a : answer) {
            System.out.print(a);
        }
    }
}

