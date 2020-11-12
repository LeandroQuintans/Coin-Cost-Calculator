package coincost;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TopDownPaymentCalc implements IPaymentCalc {
    private CoinCost cc;
    private CoinPile unusedCoinPile;
    private CoinPile currentCoinPile;
    private List<CoinPile> payments;

    public TopDownPaymentCalc(CoinCost cc) {
        this.cc = cc;
        unusedCoinPile = new CoinPile(cc.getWallet());
        currentCoinPile = new CoinPile();
        payments = new LinkedList<>();
    }

    private void start(Iterator<BigDecimal> itr) {
        do {
            BigDecimal key = itr.next();
            int amount = cc.getCost().subtract(currentCoinPile.getFullTotal()).divideToIntegralValue(key).intValue();
            try {
                unusedCoinPile.subAmount(key, amount);
            } catch (Exception e) {
                amount = unusedCoinPile.get(key);
                unusedCoinPile.subAmount(key, amount);
            }
            currentCoinPile.addAmount(key, amount);
        } while (currentCoinPile.getFullTotal().compareTo(cc.getCost()) < 0 && itr.hasNext());

        // return currentCoinPile;
    }

    private Iterator<BigDecimal> prepare(BigDecimal key) {
        Set<BigDecimal> keySet = unusedCoinPile.keySet();
        keySet.removeIf(x -> x.compareTo(key) >= 0);

        if (unusedCoinPile.getKeySetTotal(keySet).compareTo(key) < 0)
            return null;

        currentCoinPile.subAmount(key, 1);
        unusedCoinPile.addAmount(key, 1);

        Iterator<BigDecimal> result = new CoinPile(unusedCoinPile).descendingKeySet().iterator();
        BigDecimal nextKey = result.next();
        while (nextKey.compareTo(key) > 0 && result.hasNext())
            nextKey = result.next();

        return result;
    }

    @Override
    public List<CoinPile> payments() {
        start(unusedCoinPile.keySet().iterator());
        payments.add(new CoinPile(currentCoinPile));

        // while (next(cpKeySet.iterator()))


        return payments;
    }
    
}
