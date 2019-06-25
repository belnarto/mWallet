package com.vironit.mWallet.servlets;

import com.vironit.mWallet.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import javax.servlet.annotation.WebServlet;

@Component
//@WebServlet("/currencies/deleteCurrency")
public class DeleteCurrencyServlet extends HttpServlet {

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyId = req.getParameter("currencyId");
        currencyService.delete(currencyService.findById(Integer.valueOf(currencyId)));
        resp.sendRedirect(req.getContextPath() + "/currencies");
    }

}
