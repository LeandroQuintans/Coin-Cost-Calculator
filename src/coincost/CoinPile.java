package coincost;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

import coincost.exceptions.NegativeCoinAmountException;

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
            throw new NegativeCoinAmountException();
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

    // public Collection<Integer> values() {
    //     return coinPile.values();
    // }
    
    public int size() {
        return coinPile.size();
    }

    public BigDecimal getKeyTotal(BigDecimal key) {
        return key.multiply(new BigDecimal(coinPile.get(key)));
    }

    public BigDecimal getKeySetTotal(Set<BigDecimal> keySet) {
        BigDecimal result = new BigDecimal(0);
        for (BigDecimal key : keySet)
            result = result.add(getKeyTotal(key));
        return result;
    }

    public BigDecimal getFullTotal() {
        return getKeySetTotal(coinPile.keySet());
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

    public Integer emptyPile(BigDecimal key) {
        return coinPile.remove(key);
    }

    public void empty() {
        for (BigDecimal key : keySet())
            emptyPile(key);
    }

    @Override
    public String toString() {
        return coinPile.toString();
    }

    // TODO Implement a working equals
    // @Override 
    // public boolean equals(Object other) {
    //     if (other == this) return true;
    //     if (other == null) return false;
    //     if (getClass() != other.getClass()) return false;
    //     CoinPile that = (CoinPile) other;

    //     Collection<Integer> thisValues = this.coinPile.values();
    //     Collection<Integer> thatValues = that.coinPile.values();

    //     return this.coinPile.equals(that.coinPile) && thisValues.equals(thatValues); // doesn't work, find way to see if 2 collections are equal
    // }

}
