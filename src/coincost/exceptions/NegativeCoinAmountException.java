package coincost.exceptions;

/**
 * <p>RuntimeException used when there is an attempt to input or 
 * decrease a coin/bill's amount in the wallet down to negative values.</p>
 * @author Leandro Quintans
 */
public class NegativeCoinAmountException extends RuntimeException {

    private static final long serialVersionUID = 4278817640583465485L;

    public NegativeCoinAmountException() {
        super();
    }

    public NegativeCoinAmountException(String message) {
        super(message);
    }

}
