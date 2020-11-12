package coincost;

import java.math.BigDecimal;
import java.util.List;

public class CoinCost {
    private CoinPile wallet;
    private BigDecimal cost;

    public CoinCost(CoinPile wallet, BigDecimal cost) {
        this.wallet = new CoinPile(wallet);
        this.cost = new BigDecimal(cost.toString());
    }

    public CoinPile getWallet() {
        return wallet;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public List<CoinPile> payments() {
        return new TopDownPaymentCalc(this).payments();
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(wallet.toString());

        sBuilder.append(cost);

        return sBuilder.toString();
    }
}
