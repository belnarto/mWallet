package com.vironit.mWallet.servlets;

import com.vironit.mWallet.models.Currency;
import com.vironit.mWallet.services.CurrencyService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("pages/currencies.jsp");
        List<Currency> currencies = currencyService.findAll();
        req.setAttribute("currencies", currencies);
        requestDispatcher.forward(req, resp);
    }

}
