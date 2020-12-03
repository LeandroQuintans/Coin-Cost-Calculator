package coincost;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

import coincost.exceptions.NegativeCoinAmountException;

public class Wallet {
    private TreeMap<BigDecimal, Integer> wallet;

    public Wallet() {
        wallet = new TreeMap<BigDecimal, Integer>();
    }

    public Wallet(Map<? extends BigDecimal, ? extends Integer> m) {
        wallet = new TreeMap<BigDecimal, Integer>(m);
    }

    public Wallet(Wallet cp) {
        wallet = new TreeMap<BigDecimal, Integer>(cp.wallet);
    }

    public Integer get(BigDecimal key) {
        return wallet.get(key);
    }

    public Integer put(BigDecimal key, Integer value) {
        if (value < 0)
            throw new NegativeCoinAmountException();
        if (value == 0)
            return wallet.remove(key);
        return wallet.put(key, value);
    }

    public NavigableSet<BigDecimal> descendingKeySet() {
        return wallet.descendingKeySet();
    }

    public Set<BigDecimal> keySet() {
        return wallet.keySet();
    }

    public NavigableSet<BigDecimal> navigableKeySet() {
        return wallet.navigableKeySet();
    }

    // public Collection<Integer> values() {
    //     return wallet.values();
    // }
    
    public int size() {
        return wallet.size();
    }

    public BigDecimal firstKey() {
        return wallet.firstKey();
    }

    public BigDecimal lowerKey(BigDecimal key) {
        return wallet.lowerKey(key);
    }

    public BigDecimal higherKey(BigDecimal key) {
        return wallet.higherKey(key);
    }

    public BigDecimal lastKey() {
        return wallet.lastKey();
    }

    public Wallet headMap(BigDecimal toKey, boolean inclusive) {
        return new Wallet(wallet.headMap(toKey, inclusive));
    }

    public Wallet headMap(BigDecimal toKey) {
        return new Wallet(wallet.headMap(toKey));
    }

    public Wallet subWallet(BigDecimal fromKey, boolean fromInclusive, BigDecimal toKey, boolean toInclusive) {
        return new Wallet(wallet.subMap(fromKey, fromInclusive, toKey, toInclusive));
    }

    public Wallet subWallet(BigDecimal fromKey, BigDecimal toKey) {
        return new Wallet(wallet.subMap(fromKey, toKey));
    }

    public Wallet tailMap(BigDecimal fromKey, boolean inclusive) {
        return new Wallet(wallet.tailMap(fromKey, inclusive));
    }

    public Wallet tailMap(BigDecimal fromKey) {
        return new Wallet(wallet.tailMap(fromKey));
    }

    public BigDecimal getKeyTotal(BigDecimal key) {
        return key.multiply(new BigDecimal(wallet.get(key)));
    }

    public BigDecimal getKeySetTotal(Set<BigDecimal> keySet) {
        BigDecimal result = new BigDecimal(0);
        for (BigDecimal key : keySet)
            result = result.add(getKeyTotal(key));
        return result;
    }

    public BigDecimal getFullTotal() {
        return getKeySetTotal(wallet.keySet());
    }
    
    public Integer addAmount(BigDecimal key, Integer amount) {
        Integer currentAmount = wallet.containsKey(key) ? wallet.get(key) : 0;
        return put(key, currentAmount + amount);
    }
    
    public Integer subAmount(BigDecimal key, Integer amount) throws RuntimeException {
        Integer currentAmount = wallet.containsKey(key) ? wallet.get(key) : 0;
        return put(key, currentAmount - amount);
    }

    public boolean isPileEmpty(BigDecimal key) {
        return !wallet.containsKey(key) || wallet.get(key) == 0;
    }

    public Integer emptyPile(BigDecimal key) {
        return wallet.remove(key);
    }

    public void empty() {
        for (BigDecimal key : keySet())
            emptyPile(key);
    }

    public Wallet subtract(Wallet other) throws NegativeCoinAmountException { // TODO needs test
        Wallet result = new Wallet();
        for (BigDecimal key : other.keySet()) {
            int amount = this.get(key) - other.get(key);
            if (amount < 0)
                throw new NegativeCoinAmountException();
            result.put(key, amount);
        }

        return result;
    }

    @Override
    public String toString() {
        return wallet.toString();
    }

    // TODO Implement a working equals
    // @Override 
    // public boolean equals(Object other) {
    //     if (other == this) return true;
    //     if (other == null) return false;
    //     if (getClass() != other.getClass()) return false;
    //     wallet that = (wallet) other;

    //     Collection<Integer> thisValues = this.wallet.values();
    //     Collection<Integer> thatValues = that.wallet.values();

    //     return this.wallet.equals(that.wallet) && thisValues.equals(thatValues); // doesn't work, find way to see if 2 collections are equal
    // }

}