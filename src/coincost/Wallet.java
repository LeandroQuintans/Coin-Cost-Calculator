package coincost;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

import coincost.exceptions.NegativeCoinAmountException;

/** 
 * <p>This class exposes several useful TreeMap methods 
 * and implements others to handle a wallet like
 * entity.</p>
 * <p>Wallet is composed by a TreeMap with BigDecimal as keys 
 * and Integer as values.</p>
 * <p>Wallet only stores positive valued entries. Zero or negative values are not permitted.</p>
 * @author Leandro Quintans
 */
public class Wallet {
    private TreeMap<BigDecimal, Integer> wallet;

    /**
     * <p>Constructor with no arguments, creates an empty TreeMap for its field.</p>
     */
    public Wallet() {
        wallet = new TreeMap<BigDecimal, Integer>();
    }

    /**
     * <p>Constructor using an existing Map as a starting point.</p>
     * <p>All negative or zero valued entries are removed from the walle.t</p>
     * <p>For the wallet field a new TreeMap is created using the Map, 
     * meaning any changes made to the argument Map will not 
     * affect the wallet field.</p>
     * @param m Map with BigDecimal keys and Integer values, used as a starting point for the wallet field
     */
    public Wallet(Map<? extends BigDecimal, ? extends Integer> m) {
        wallet = new TreeMap<BigDecimal, Integer>(m);
        m.entrySet().stream().filter(pair -> pair.getValue() <= 0).forEach(pair -> wallet.remove(pair.getKey()));
    }

    /**
     * <p>Constructor using a Wallet as a starting point</p>
     * <p>For the wallet field a new TreeMap is created using the Wallet, 
     * meaning any changes made to the argument Wallet will not 
     * affect the wallet field</p>
     * @param cp
     */
    public Wallet(Wallet cp) {
        wallet = new TreeMap<BigDecimal, Integer>(cp.wallet);
    }

    /**
     * <p>Returns the value stored in the wallet that corresponds to the key inputted</p>
     * <p>Implementation on top of the normal TreeMap.get() method</p>
     * @param key Value of a coin (or bill)
     * @return If the key exists in the wallet, returns the value of key in the wallet, if not, returns 0
     * @see TreeMap#get(Object)
     * @see TreeMap#containsKey(Object)
     */
    public Integer get(BigDecimal key) {
        return wallet.containsKey(key) ? wallet.get(key) : 0;
    }

    /**
     * <p>Puts the key, value pair in the wallet</p>
     * <p>If the value is negative, NegativeCoinAmountException is thrown, 
     * if the value is zero, the key is removed from the wallet using {@link TreeMap#remove(Object)}</p>
     * @param key Value of a coin (or bill)
     * @param value Amount of coins (or bills)
     * @return param value
     * @see TreeMap#put(Object, Object)
     * @throws NegativeCoinAmountException
     */
    public Integer put(BigDecimal key, Integer value) {
        if (value < 0)
            throw new NegativeCoinAmountException();
        if (value == 0)
            return wallet.remove(key);
        return wallet.put(key, value);
    }

    /**
     * @see TreeMap#descendingKeySet()
     */
    public NavigableSet<BigDecimal> descendingKeySet() {
        return wallet.descendingKeySet();
    }

    /**
     * @see TreeMap#keySet()
     */
    public Set<BigDecimal> keySet() {
        return wallet.keySet();
    }

    /**
     * @see TreeMap#navigableKeySet()
     */
    public NavigableSet<BigDecimal> navigableKeySet() {
        return wallet.navigableKeySet();
    }

    // public Collection<Integer> values() {
    //     return wallet.values();
    // }

    /**
     * @see TreeMap#size()
     */
    public int size() {
        return wallet.size();
    }

    /**
     * @see TreeMap#firstKey(Object)
     */
    public BigDecimal firstKey() {
        return wallet.firstKey();
    }

    /**
     * @see TreeMap#lowerKey(Object)
     */
    public BigDecimal lowerKey(BigDecimal key) {
        return wallet.lowerKey(key);
    }

    /**
     * @see TreeMap#higherKey(Object)
     */
    public BigDecimal higherKey(BigDecimal key) {
        return wallet.higherKey(key);
    }

    /**
     * @see TreeMap#lastKey()
     */
    public BigDecimal lastKey() {
        return wallet.lastKey();
    }

    /**
     * @see TreeMap#headMap(Object, boolean)
     */
    public Wallet headWallet(BigDecimal toKey, boolean inclusive) {
        return new Wallet(wallet.headMap(toKey, inclusive));
    }

    /**
     * @see TreeMap#headMap(Object)
     */
    public Wallet headWallet(BigDecimal toKey) {
        return new Wallet(wallet.headMap(toKey));
    }

    /**
     * @see TreeMap#subMap(Object, boolean, Object, boolean)
     */
    // returns a part of the wallet, not to be confused with subtract
    public Wallet subWallet(BigDecimal fromKey, boolean fromInclusive, BigDecimal toKey, boolean toInclusive) {
        return new Wallet(wallet.subMap(fromKey, fromInclusive, toKey, toInclusive));
    }

    /**
     * @see TreeMap#subMap(Object, Object)
     */
    // returns a part of the wallet, not to be confused with subtract
    public Wallet subWallet(BigDecimal fromKey, BigDecimal toKey) {
        return new Wallet(wallet.subMap(fromKey, toKey));
    }

    /**
     * @see TreeMap#tailMap(Object, boolean)
     */
    public Wallet tailWallet(BigDecimal fromKey, boolean inclusive) {
        return new Wallet(wallet.tailMap(fromKey, inclusive));
    }

    /**
     * @see TreeMap#tailMap(Object)
     */
    public Wallet tailWallet(BigDecimal fromKey) {
        return new Wallet(wallet.tailMap(fromKey));
    }

    /**
     * <p>Calculates the total value of a key, that is:</p>
     * <p>key (coin value) * value (amount)</p>
     * @param key Value of a coin (or bill)
     * @return Key total
     */
    public BigDecimal getKeyTotal(BigDecimal key) {
        return key.multiply(new BigDecimal(wallet.get(key)));
    }

    /**
     * <p>Calculates the total value of a keySet, that is:</p>
     * <p>The sum of all getKeyTotal(key) where key belongs to keySet</p>
     * @param keySet Set of values of coins (or bills)
     * @return Total value of a keySet
     */
    public BigDecimal getKeySetTotal(Set<BigDecimal> keySet) {
        BigDecimal result = new BigDecimal(0);
        for (BigDecimal key : keySet)
            result = result.add(getKeyTotal(key));
        return result;
    }

    /**
     * <p>Calculates the full value of the wallet</p>
     * @return Full value of wallet
     */
    public BigDecimal getFullTotal() {
        return getKeySetTotal(wallet.keySet());
    }
    
    /**
     * <p>Adds the value of key by the inputted amount</p>
     * @param key Value of a coin (or bill)
     * @param amount Amount of coins (or bills)
     * @return Value that will be stored, i.e. the sum of the current value and the inputted amount
     */
    public Integer addAmount(BigDecimal key, Integer amount) {
        Integer currentAmount = wallet.containsKey(key) ? wallet.get(key) : 0;
        return put(key, currentAmount + amount);
    }
    
    /**
     * <p>Subtracts the value of key by the inputted amount</p>
     * <p>Throws NegativeCoinAmountException if the difference between 
     * the value of the key and the inputted amount is negative</p>
     * @param key Value of a coin (or bill)
     * @param amount Amount of coins (or bills)
     * @return Value that will be stored, i.e. the difference between the current value and the inputted amount
     * @throws NegativeCoinAmountException
     */
    public Integer subAmount(BigDecimal key, Integer amount) throws NegativeCoinAmountException {
        Integer currentAmount = wallet.containsKey(key) ? wallet.get(key) : 0;
        return put(key, currentAmount - amount);
    }

    /**
     * <p>Checks if a pile of coins exists in the wallet, i.e., if the key exists</p>
     * @param key Value of a coin (or bill)
     * @return true if pile of coins doesn't exist, false if not
     */
    public boolean isPileEmpty(BigDecimal key) {
        return !wallet.containsKey(key) || wallet.get(key) == 0;
    }

    /**
     * <p>Empties a coin "pile", i.e. removes key from wallet wallet</p>
     * @param key Value of a coin (or bill)
     * @return Value of key removed
     * @see TreeMap#remove(Object)
     */
    public Integer emptyPile(BigDecimal key) {
        return wallet.remove(key);
    }

    /**
     * <p>Empties wallet, i.e. removes all keys from wallets</p>
     * @see Wallet#emptyPile(BigDecimal)
     */
    public void empty() {
        for (BigDecimal key : keySet())
            emptyPile(key);
    }

    /**
     * <p>Adds two wallets together, returning a new wallet.</p>
     * @param other 
     * @return New Wallet that is the sum of this and other Wallets
     */
    public Wallet add(Wallet other) {
        Wallet result = new Wallet();
        Set<BigDecimal> allKeySet = new HashSet<>();
        allKeySet.addAll(this.keySet());
        allKeySet.addAll(other.keySet());

        for (BigDecimal key : allKeySet) {
            int amount = this.get(key) + other.get(key);
            result.put(key, amount);
        }

        return result;
    }


    /**
     * <p>Subtracts two wallets together, returning a new wallet.</p>
     * <p>Throws NegativeCoinAmountException if any value in the returned Wallet is negative</p>
     * @param other
     * @return New Wallet that is the difference of this and other Wallets
     * @throws NegativeCoinAmountException
     */
    // this Wallet must have greater or equal number of coins in each coin value than other Wallet,
    // else it will throw an exception
    public Wallet subtract(Wallet other) throws NegativeCoinAmountException {
        Wallet result = new Wallet();
        for (BigDecimal key : this.keySet()) {
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

    @Override
    public int hashCode() {
        return wallet.hashCode();
    }

    @Override 
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        Wallet that = (Wallet) other;

        return this.wallet.equals(that.wallet);
    }

}
