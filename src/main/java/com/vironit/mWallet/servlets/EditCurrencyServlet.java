package com.vironit.mWallet.servlets;

import com.vironit.mWallet.models.Currency;
import com.vironit.mWallet.services.CurrencyService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCurrencyServlet extends HttpServlet {

    CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyId = req.getParameter("currencyId");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pages/editCurrency.jsp");
        Currency currency = currencyService.findById(Integer.valueOf(currencyId));
        req.setAttribute("currency", currency);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyId = req.getParameter("currencyId");
        String newName = req.getParameter("name");
        String newRate = req.getParameter("rate");
        Currency currency = currencyService.findById(Integer.valueOf(currencyId));
        currency.setName(newName);
        currency.setRate(Double.valueOf(newRate));
        currencyService.update(currency);
        req.setAttribute("updated", "true");
        doGet(req, resp);
    }

}
