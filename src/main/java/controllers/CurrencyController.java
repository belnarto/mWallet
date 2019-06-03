package controllers;

import models.Currency;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.CurrencyService;

@Controller
public class CurrencyController {

    @RequestMapping(value = "/currencies", method = RequestMethod.GET)
    public ModelAndView allCurrencies() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("currencyPages/currencies");
        modelAndView.addObject("currencies", CurrencyService.findAll());
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
        CurrencyService.save(currency);
        return modelAndView;
    }

    @RequestMapping(value = "/currencies/editCurrency/{id}", method = RequestMethod.GET)
    public ModelAndView editCurrencyPage(@PathVariable("id") int id) {
        Currency currency = CurrencyService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("currencyPages/editCurrency");
        modelAndView.addObject("currency", currency);
        return modelAndView;
    }

    @RequestMapping(value = "/currencies/editCurrency", method = RequestMethod.POST)
    public ModelAndView editCurrency(@ModelAttribute("currency") Currency currency) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/currencies");
        CurrencyService.update(currency);
        return modelAndView;
    }

    @RequestMapping(value = "/currencies/deleteCurrency/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") int id) {
        CurrencyService.delete(CurrencyService.findById(id));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/currencies");
        return modelAndView;
    }

}
