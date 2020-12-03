package coincost;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coincost.exceptions.CoinDownsizeImpossibleException;
import coincost.exceptions.NegativeCoinAmountException;

public class TopDownPaymentCalc implements IPaymentCalc {
    private CoinCost cc;
    private Wallet unusedCoinPile;
    private Wallet currentCoinPile;
    private Set<Wallet> payments;

    public TopDownPaymentCalc(CoinCost cc) {
        this.cc = cc;
        unusedCoinPile = new Wallet(cc.getWallet());
        currentCoinPile = new Wallet();
        payments = new HashSet<>();
    }

    private void start(Iterator<BigDecimal> itr) {
        do {
            BigDecimal key = itr.next();
            int amount = cc.getCost().subtract(currentCoinPile.getFullTotal()).divideToIntegralValue(key).intValue();
            try {
                unusedCoinPile.subAmount(key, amount);
            } catch (NegativeCoinAmountException e) {
                amount = unusedCoinPile.get(key);
                unusedCoinPile.subAmount(key, amount);
            }
            currentCoinPile.addAmount(key, amount);
        } while (currentCoinPile.getFullTotal().compareTo(cc.getCost()) < 0 && itr.hasNext());
    }

    private Iterator<BigDecimal> prepare(BigDecimal key) throws CoinDownsizeImpossibleException {
        Set<BigDecimal> keySet = unusedCoinPile.keySet();
        keySet.removeIf(x -> x.compareTo(key) >= 0);

        if (unusedCoinPile.getKeySetTotal(keySet).compareTo(key) < 0)
            throw new CoinDownsizeImpossibleException();

        currentCoinPile.subAmount(key, 1);
        unusedCoinPile.addAmount(key, 1);

        Iterator<BigDecimal> result = new Wallet(unusedCoinPile).descendingKeySet().iterator();
        BigDecimal nextKey = result.next();
        while (nextKey.compareTo(key) > 0 && result.hasNext())
            nextKey = result.next();

        return result;
    }

    @Override
    public Set<Wallet> payments() { // TODO handling not enough money and when you can't get exact cost
        start(cc.getWallet().descendingKeySet().iterator());
        payments.add(new Wallet(currentCoinPile));
        
        Set<BigDecimal> unprocessedKeys = cc.getWallet().descendingKeySet();
        BigDecimal currentKey;
        Iterator<BigDecimal> unusedCPItr;
        do {
            currentKey = unprocessedKeys.iterator().next();
            try {
                unusedCPItr = prepare(currentKey);
                start(unusedCPItr);
                payments.add(new Wallet(currentCoinPile));
            }
            catch (CoinDownsizeImpossibleException e) {
                unprocessedKeys.remove(currentKey);
            }
        } while(unprocessedKeys.size() > 1);

        return payments;
    }
    
}
