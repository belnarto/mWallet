package com.vironit.mwallet.servlet;

import com.vironit.mwallet.model.dto.CurrencyDto;
import com.vironit.mwallet.model.entity.Currency;
import com.vironit.mwallet.service.CurrencyService;
import com.vironit.mwallet.service.mapper.CurrencyMapper;
import lombok.extern.log4j.Log4j2;
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

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
@Log4j2
@WebServlet("/currencies/editCurrency")
public class EditCurrencyServlet extends HttpServlet {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public void init(ServletConfig config) {
        try {
            super.init(config);
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                    config.getServletContext());
        } catch (ServletException e) {
            log.error("error occurred during servlet init", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String currencyId = req.getParameter("currencyId");
        RequestDispatcher requestDispatcher =
                req.getRequestDispatcher("/WEB-INF/pages/currencyPages/editCurrency.jsp");
        CurrencyDto currencyDto = currencyMapper.toDto(
                currencyService.findById(Integer.valueOf(currencyId)));
        req.setAttribute("currency", currencyDto);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
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
