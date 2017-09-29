package commandPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @kash on 9/29/2017.
 */
public class test {
    private static CashRegister cashRegister = new CashRegister(0);
    private static PurchaseInvoker purchaseInvoker = new PurchaseInvoker();

    public static void main(String[] args)  {
        Command purchase1 = new Purchase(cashRegister,50);
        Command purchase2 = new Purchase(cashRegister, 100);
        purchaseInvoker.executePurchase(purchase1);
        purchaseInvoker.executePurchase(purchase2);
        System.out.println("After purchases cash = " + cashRegister.getTotal());

        System.out.println("Setting cash to 0.");
        cashRegister.reset();
        List<Command> purchases = purchaseInvoker.getPurchaseHistory();
        for(Command purchase: purchases)  {
            purchase.execute();
        }
        System.out.println("After replaying, cash = " + cashRegister.getTotal());
    }
}

interface Command   {
    public void execute();
}

class Purchase implements Command {
    CashRegister cashRegister;
    Integer amount;

    public Purchase(CashRegister cashRegister, Integer amount)  {
        this.cashRegister = cashRegister;
        this.amount = amount;
    }

    @Override
    public void execute() {
        cashRegister.addCash(amount);
    }
}

class CashRegister  {
    private Integer total;

    public CashRegister(int initialCash)    {
        total = initialCash;
    }

    public void addCash(Integer cash)  {
        total += cash;
    }

    public Integer getTotal()   {
        return total;
    }

    public void reset() {
        total = 0;
    }
}

class PurchaseInvoker   {
    private List<Command> purchases = new ArrayList<>();

    public void executePurchase(Command purchase)   {
        purchases.add(purchase);
        purchase.execute();
    }

    public List<Command>  getPurchaseHistory()  {
        return purchases;
    }
}
