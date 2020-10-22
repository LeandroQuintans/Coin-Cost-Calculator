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

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();

        for (CoinStockpile pouch : wallet) {
            sBuilder.append(pouch.toString());
            sBuilder.append(' ');
        }

        sBuilder.append(cost);

        return sBuilder.toString();
    }
    public static void main(String[] args) {
        
    }
}
