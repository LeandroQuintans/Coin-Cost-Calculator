package coincost;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.NavigableSet;

public abstract class SimplePaymentCalc implements Iterator<CoinPile> {
    private BigDecimal cost;
    private CoinPile unusedCoinPile;
    private NavigableSet<BigDecimal> cpKeySet;
    private CoinPile currentCoinPile;

    public SimplePaymentCalc(CoinCost cc) {
        cost = cc.getCost();
        unusedCoinPile = new CoinPile(cc.getWallet());
        cpKeySet = unusedCoinPile.navigableKeySet();
        currentCoinPile = new CoinPile();
    }

    public void start(Iterator<BigDecimal> itr) {
        do {
            BigDecimal key = itr.next();
            int amount = cost.subtract(currentCoinPile.getTotal()).divideToIntegralValue(key).intValue();
            try {
                unusedCoinPile.subAmount(key, amount);
            } catch (Exception e) {
                amount = unusedCoinPile.get(key);
                unusedCoinPile.subAmount(key, amount);
            }
            currentCoinPile.addAmount(key, amount);
        } while (currentCoinPile.getTotal().compareTo(cost) < 0 && itr.hasNext());
    }
}
