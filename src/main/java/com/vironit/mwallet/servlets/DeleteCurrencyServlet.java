package com.vironit.mwallet.servlets;

import com.vironit.mwallet.services.CurrencyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
@WebServlet("/currencies/deleteCurrency")
public class DeleteCurrencyServlet extends HttpServlet {

    @Autowired
    private CurrencyService currencyService;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyId = req.getParameter("currencyId");
        currencyService.delete(currencyService.findById(Integer.valueOf(currencyId)));
        resp.sendRedirect(req.getContextPath() + "/currencies");
    }

}
