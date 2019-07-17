package Intermixing.java;

/**
 * @Author: gin
 * @Date: Created in 上午10:33 19-7-15
 * @Description:
 */
public class Investment {
    private String investmentName;
    private InvestmentType investmentType;

    public Investment(String name, InvestmentType type) {
        investmentName = name;
        investmentType = type;
    }

    public int yield() {
        return 0;
    }
}
