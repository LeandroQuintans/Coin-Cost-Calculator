package coincost;

import java.math.BigDecimal;

public class CoinStockpile {
    private BigDecimal value;
    private int amount;

    public CoinStockpile(String value, int amount) {
        this.value = new BigDecimal(value);
        this.amount = amount;
    }
    
    public CoinStockpile(BigDecimal value, int amount) {
        this(value.toString(), amount);
    }

    public BigDecimal getValue() {
        return value;
    }

    public int getAmount() {
        return amount;
    }

}
