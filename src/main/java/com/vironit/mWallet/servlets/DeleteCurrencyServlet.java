package com.vironit.mWallet.servlets;

import com.vironit.mWallet.services.CurrencyService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/currencies/deleteCurrency")
public class DeleteCurrencyServlet extends HttpServlet {

    private CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyId = req.getParameter("currencyId");
        currencyService.delete(currencyService.findById(Integer.valueOf(currencyId)));
        resp.sendRedirect(req.getContextPath() + "/currencies");
    }

}
