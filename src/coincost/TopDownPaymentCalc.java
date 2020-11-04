package coincost;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

public class TopDownPaymentCalc implements Iterator<CoinPile>, IPaymentCalc {
    private CoinCost cc;
    private CoinPile unusedCoinPile;
    private Set<BigDecimal> cpKeySet;
    private CoinPile currentCoinPile;

    public TopDownPaymentCalc(CoinCost cc) {
        this.cc = cc;
        unusedCoinPile = new CoinPile(cc.getWallet());
        cpKeySet = unusedCoinPile.keySet();
        currentCoinPile = new CoinPile();
        start(cpKeySet.iterator());
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
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public CoinPile next() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<CoinPile> payments() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
