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

    public void start(Iterator<BigDecimal> itr) {
        do {
            BigDecimal key = itr.next();
            int amount = cc.getCost().subtract(currentCoinPile.getTotal()).divideToIntegralValue(key).intValue();
            try {
                unusedCoinPile.subAmount(key, amount);
            } catch (Exception e) {
                amount = unusedCoinPile.get(key);
                unusedCoinPile.subAmount(key, amount);
            }
            currentCoinPile.addAmount(key, amount);
        } while (currentCoinPile.getTotal().compareTo(cc.getCost()) < 0 && itr.hasNext());

        // return currentCoinPile;
    }

    public void prepare(Iterator<BigDecimal> itr) {
        
    }

    @Override
    public List<CoinPile> payments() {
        start(unusedCoinPile.keySet().iterator());
        payments.add(new CoinPile(currentCoinPile));

        // while (next(cpKeySet.iterator()))


        return payments;
    }
    
}
