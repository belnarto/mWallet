package com.vironit.mwallet.servlets;

import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;

@Component
@WebServlet("/currencies/editCurrency")
public class EditCurrencyServlet extends HttpServlet {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private CurrencyService currencyService;

    @Override
    public void init(ServletConfig config) {
        try {
            super.init(config);
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                    config.getServletContext());
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyId = req.getParameter("currencyId");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/pages/currencyPages/editCurrency.jsp");
        CurrencyDto currencyDto = currencyService.findById(Integer.valueOf(currencyId));
        req.setAttribute("currency", currencyDto);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currencyId = req.getParameter("currencyId");
        String newName = req.getParameter("name");
        String newRate = req.getParameter("rate");
        CurrencyDto currencyDto = currencyService.findById(Integer.valueOf(currencyId));
        currencyDto.setName(newName);
        currencyDto.setRate(Double.valueOf(newRate));
        currencyService.update(currencyDto);
        req.setAttribute("updated", "true");
        doGet(req, resp);
    }

}
