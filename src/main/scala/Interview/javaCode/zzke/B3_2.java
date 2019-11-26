package Interview.javaCode.zzke;

/**
 * @Author: chenjinxin
 * @Date: Created in 下午4:00 19-9-29
 * @Description:
 */

public class B3_2 {
    final static long mod = 1000000007;

    public static Pair make_pair(long a, long b) {
        return new Pair(a, b);
    }

    public static Pair ope(Pair a, Pair b) {
        return make_pair((a.first * b.first + a.second * b.second) % mod, (a.first * b.second + a.second * b.first + a.second * b.second) % mod);
    }

    public static long F(long y) {
        Pair ans = make_pair(1, 0);
        Pair x = make_pair(0, 1);
        while (y != 0) {
            if (y % 2 == 1) ans = ope(ans, x);
            x = ope(x, x);
            y = y >> 1;
        }
        return ans.first + ans.second;
    }

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        long n = 1000000000;
        long result = 0;
        result = F(n - 1) % mod;
        System.out.println(result);
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
    }
}

class Pair {
    long first;
    long second;

    public Pair(long first, long second) {
        this.first = first;
        this.second = second;
    }

    public long getFirst() {
        return first;
    }

    public void setFirst(long first) {
        this.first = first;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
