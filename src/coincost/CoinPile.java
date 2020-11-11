package coincost;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

public class CoinPile {
    private TreeMap<BigDecimal, Integer> coinPile;

    public CoinPile() {
        coinPile = new TreeMap<BigDecimal, Integer>();
    }

    public CoinPile(Map<? extends BigDecimal, ? extends Integer> m) {
        coinPile = new TreeMap<BigDecimal, Integer>(m);
    }

    public CoinPile(CoinPile cp) {
        coinPile = new TreeMap<BigDecimal, Integer>(cp.coinPile);
    }

    public Integer get(BigDecimal key) {
        return coinPile.get(key);
    }

    public Integer put(BigDecimal key, Integer value) {
        if (value < 0)
            throw new RuntimeException(); // TODO make custom exception for negative amount
        if (value == 0)
            return coinPile.remove(key);
        return coinPile.put(key, value);
    }

    public NavigableSet<BigDecimal> descendingKeySet() {
        return coinPile.descendingKeySet();
    }

    public Set<BigDecimal> keySet() {
        return coinPile.keySet();
    }

    public NavigableSet<BigDecimal> navigableKeySet() {
        return coinPile.navigableKeySet();
    }
    
    public int size() {
        return coinPile.size();
    }

    public BigDecimal getKeyTotal(BigDecimal key) {
        return key.multiply(new BigDecimal(coinPile.get(key)));
    }

    public BigDecimal getTotal() {
        BigDecimal result = new BigDecimal(0);
        for (BigDecimal key : coinPile.keySet())
            result = result.add(getKeyTotal(key));
        return result;
    }
    
    public Integer addAmount(BigDecimal key, Integer amount) {
        Integer currentAmount = coinPile.containsKey(key) ? coinPile.get(key) : 0;
        return put(key, currentAmount + amount);
    }
    
    public Integer subAmount(BigDecimal key, Integer amount) throws RuntimeException {
        Integer currentAmount = coinPile.containsKey(key) ? coinPile.get(key) : 0;
        return put(key, currentAmount - amount);
    }

    public boolean isPileEmpty(BigDecimal key) {
        return !coinPile.containsKey(key) || coinPile.get(key) == 0;
    }

    @Override
    public String toString() {
        return coinPile.toString();
    }

}
