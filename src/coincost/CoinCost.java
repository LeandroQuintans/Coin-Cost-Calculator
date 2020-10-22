package coincost;

import java.math.BigDecimal;
import java.util.Iterator;

public class CoinCost {
    private CoinStockpile[] wallet;
    private BigDecimal cost;

    public CoinCost(CoinStockpile[] wallet, BigDecimal cost) {
        this.wallet = wallet.clone();
        this.cost = new BigDecimal(cost.toString());
    }

    public CoinStockpile[] getWallet() {
        return wallet;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Iterator<CoinStockpile[]> payments() {
        return null;
    }

    public static void main(String[] args) {
        
    }
}
