package coincost;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CoinCost {
    private Wallet wallet;
    private BigDecimal cost;

    public CoinCost(Wallet wallet, BigDecimal cost) {
        this.wallet = new Wallet(wallet);
        this.cost = new BigDecimal(cost.toString());
    }

    public Wallet getWallet() {
        return wallet;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Set<Wallet> payments() {
        return new AllPaymentCalc(this).payments();
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(wallet.toString());

        sBuilder.append(cost);

        return sBuilder.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] coinPileStrings = sc.nextLine().split(" ");
        BigDecimal cost = new BigDecimal(sc.next());
        sc.close();
        
        Pattern pattern = Pattern.compile("\\((\\d+.\\d+|\\d+),(\\d+)\\)");
        
        Wallet coinPiles = new Wallet();
        for (String coinPileString : coinPileStrings) {
            Matcher matcher = pattern.matcher(coinPileString);
            matcher.matches();
            BigDecimal value = new BigDecimal(matcher.group(1));
            int amount = Integer.parseInt(matcher.group(2));

            coinPiles.put(value, amount);
        }

        CoinCost coinCost = new CoinCost(coinPiles, cost);
        Set<Wallet> payments = coinCost.payments();
        for (Wallet payment : payments)
            System.out.println(payment);
    }
}
