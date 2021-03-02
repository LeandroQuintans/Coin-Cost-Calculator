package coincost;

import java.util.Set;

/**
 * <p>Interface that exposes the payments method</p>
 * <p>This interface should be used for different 
 * algorithms to calculate the payments of a CoinCost entity</p>
 * @author Leandro Quintans
 */
public interface IPaymentCalc {
    /**
     * <p>Calculates all the payments and returns a set of them</p>
     * @return Set of payments
     */
    public Set<Wallet> payments();
}
