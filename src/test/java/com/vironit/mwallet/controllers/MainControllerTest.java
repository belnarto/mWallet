package com.vironit.mwallet.controllers;

import com.vironit.mwallet.config.WebConfig;
import com.vironit.mwallet.model.attribute.RoleEnum;
import com.vironit.mwallet.model.entity.Currency;
import com.vironit.mwallet.model.entity.Role;
import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.service.CurrencyService;
import com.vironit.mwallet.service.RoleService;
import com.vironit.mwallet.service.UserService;
import com.vironit.mwallet.service.exception.LoginAlreadyDefinedException;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("FieldCanBeLocal")
@Log4j2
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class MainControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CurrencyService currencyService;

    private User user;
    private Currency currency;
    private Role role;

    @Before
    public void setUp() throws LoginAlreadyDefinedException, InterruptedException {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx)
                .addFilters(springSecurityFilterChain)
                .build();

        Optional<User> userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test"))
                .findAny();
        userOpt.ifPresent(userService::delete);

        userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test2"))
                .findAny();
        userOpt.ifPresent(userService::delete);

        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        roleOpt.ifPresent(roleService::delete);

        role = new Role(0, RoleEnum.TST);
        roleService.save(role);

        user = new User(0, "Test", "Test", "Test", role, LocalDateTime.now(), null);
        userService.save(user);

        currency = new Currency(0, "TST", 0.01);
        Optional<Currency> currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));
        currencyService.save(currency);

        Thread.sleep(1);
    }

    @After
    public void tearDown() {
        Optional<User> userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test"))
                .findAny();
        userOpt.ifPresent(userService::delete);

        userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test2"))
                .findAny();
        userOpt.ifPresent(userService::delete);

        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        roleOpt.ifPresent(roleService::delete);

        Optional<Currency> currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));

        currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST2"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));
    }

    @Test
    public void mainPage() throws Exception {

        // main page without authorized user
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(forwardedUrl("/WEB-INF/pages/main.jsp"))
                .andExpect(model().attributeDoesNotExist("userId"))
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        // main page with authorized user
        result = mockMvc.perform(post("/login")
                .param("username", "Test")
                .param("password", "Test")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(get("/")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(forwardedUrl("/WEB-INF/pages/main.jsp"))
                .andExpect(model().attributeExists("userId"))
                .andReturn();
    }

    @Test
    public void login1() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("/WEB-INF/pages/login.jsp"));
    }

    @Test
    public void login2() throws Exception {

        mockMvc.perform(formLogin("/login")
                .user("Test")
                .password("Test"))
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated())
                .andExpect(authenticated().withAuthorities(
                        userDetailsService.loadUserByUsername("Test").getAuthorities()))
                .andExpect(authenticated().withUsername("Test"));
    }

    @Test
    public void login3() throws Exception {

        mockMvc.perform(formLogin("/login")
                .user("Test")
                .password("invalid"))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login?error=true"));
    }

    @Test
    public void login4() throws Exception {

        MvcResult result = mockMvc
                .perform(get("/users/" + user.getId()))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(post("/login")
                .param("username", "Test")
                .param("password", "Test")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + user.getId()))
                .andExpect(authenticated())
                .andReturn();

        mockMvc.perform(post("/logout")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        mockMvc.perform(get("/login?logout=true")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("/WEB-INF/pages/login.jsp"))
                .andExpect(model().attribute("logout", "You have been logged out successfully."))
                .andReturn();
    }

    @Test
    public void login5() throws Exception {

        MvcResult result = mockMvc
                .perform(get("/users/" + user.getId()))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(post("/login")
                .param("username", "Test")
                .param("password", "invalid")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/login?error=true"))
                .andExpect(unauthenticated())
                .andReturn();

        mockMvc.perform(get("/login?error=true")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("/WEB-INF/pages/login.jsp"))
                .andExpect(model().attribute("error", "Your username and password are invalid."))
                .andReturn();
    }

    @Test
    public void logout() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.logout())
                .andExpect(redirectedUrl("/login?logout=true"));
    }

    @Test
    public void accessDenied() throws Exception {

        MvcResult result = mockMvc
                .perform(get("/currencies/editCurrency?currencyId=1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test")
                .param("password", "Test")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/currencies/editCurrency?currencyId=1"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(get("/currencies/editCurrency?currencyId=1")
                .session(session)
                .with(csrf()))
                .andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/403"))
                .andReturn();

        mockMvc.perform(get("/403")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(forwardedUrl("/WEB-INF/pages/errorPage.jsp"))
                .andExpect(model().attribute("errorMsg", "Hi Test, you do not have permission to access this page!"))
                .andReturn();

        mockMvc.perform(post("/logout")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        mockMvc.perform(get("/403")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(forwardedUrl("/WEB-INF/pages/errorPage.jsp"))
                .andExpect(model().attribute("errorMsg", "You do not have permission to access this page!"))
                .andReturn();
    }
}