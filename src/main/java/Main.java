import models.Wallet;
import services.CurrencyService;
import services.WalletService;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) {
        System.out.println(CurrencyService.findByName("USD"));
    }
}