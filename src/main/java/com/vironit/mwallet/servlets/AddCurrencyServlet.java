package com.vironit.mwallet.servlets;

import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@WebServlet("/currencies/addCurrency")
public class AddCurrencyServlet extends HttpServlet {

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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/pages/currencyPages/addCurrency.jsp");
        List<CurrencyDto> currencies = currencyService.findAll();
        req.setAttribute("currencies", currencies);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String rate = req.getParameter("rate");
        CurrencyDto currencyDto = new CurrencyDto(0, name, Double.valueOf(rate));
        currencyService.save(currencyDto);
        req.setAttribute("currency", currencyDto);
        doGet(req, resp);
    }

}
