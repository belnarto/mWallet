import dao.CurrencyDaoHibernate;
import dao.CurrencyDaoJDBC;
import models.Currency;

import java.sql.SQLException;

public class Main {

    static CurrencyDaoHibernate currencyDaoHibernate = new CurrencyDaoHibernate();

    public static void main(String[] args) {
        CurrencyDaoJDBC cur = new CurrencyDaoJDBC();
            System.out.println(cur.findByName("USD"));
        System.out.println(cur.findByName("USD2"));
        Currency currency = new Currency();
        currency.setName("TST");
        currency.setRate(222);
        cur.save(currency);
        //cur.delete(currencyDaoHibernate.findById(164));

        System.out.println(cur.findAll());
    }
}