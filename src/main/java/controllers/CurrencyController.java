package controllers;

import dao.CurrencyDao;
import dao.CurrencyDaoHibernate;
import models.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.CurrencyService;

@Controller
public class CurrencyController {

    CurrencyService currencyService = new CurrencyService();

    @RequestMapping(value = "/currencies", method = RequestMethod.GET)
    public ModelAndView allCurrencies() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("currencyPages/currencies");
        modelAndView.addObject("currencies", currencyService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/currencies/addCurrency", method = RequestMethod.GET)
    public ModelAndView addCurrencyPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("currencyPages/addCurrency");
        return modelAndView;
    }

    @RequestMapping(value = "/currencies/addCurrency", method = RequestMethod.POST)
    public ModelAndView addCurrency(@ModelAttribute("currency") Currency currency) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/currencies");
        currencyService.save(currency);
        return modelAndView;
    }

    @RequestMapping(value = "/currencies/editCurrency/{id}", method = RequestMethod.GET)
    public ModelAndView editCurrencyPage(@PathVariable("id") int id) {
        Currency currency = currencyService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("currencyPages/editCurrency");
        modelAndView.addObject("currency", currency);
        return modelAndView;
    }

    @RequestMapping(value = "/currencies/editCurrency", method = RequestMethod.POST)
    public ModelAndView editCurrency(@ModelAttribute("currency") Currency currency) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/currencies");
        currencyService.update(currency);
        return modelAndView;
    }

    @RequestMapping(value = "/currencies/deleteCurrency/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") int id) {
        currencyService.delete(currencyService.findById(id));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/currencies");
        return modelAndView;
    }

}
