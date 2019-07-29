package com.vironit.mwallet.servlet;

import com.vironit.mwallet.model.dto.CurrencyDto;
import com.vironit.mwallet.service.CurrencyService;
import com.vironit.mwallet.service.mapper.CurrencyMapper;
import lombok.extern.log4j.Log4j2;
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
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
@Log4j2
@WebServlet("/currencies/addCurrency")
public class AddCurrencyServlet extends HttpServlet {

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
        RequestDispatcher requestDispatcher =
                req.getRequestDispatcher("/WEB-INF/pages/currencyPages/addCurrency.jsp");
        List<CurrencyDto> currencies = currencyService.findAll().stream()
                .map(currency -> currencyMapper.toDto(currency))
                .collect(Collectors.toList());
        req.setAttribute("currencies", currencies);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String rate = req.getParameter("rate");
        CurrencyDto currencyDto = new CurrencyDto(0, name, Double.valueOf(rate));
        currencyService.save(currencyMapper.toEntity(currencyDto));
        req.setAttribute("currency", currencyDto);
        doGet(req, resp);
    }

}
