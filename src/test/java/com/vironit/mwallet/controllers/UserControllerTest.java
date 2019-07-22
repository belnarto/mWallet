package com.vironit.mwallet.controllers;

import com.vironit.mwallet.config.WebConfig;
import com.vironit.mwallet.models.attributes.RoleEnum;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.models.entity.Role;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
import com.vironit.mwallet.services.mapper.RoleMapper;
import com.vironit.mwallet.services.mapper.UserMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

@SuppressWarnings("FieldCanBeLocal")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class UserControllerTest {

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

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
    public void allUsersPage() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "ADMIN")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        assert session != null;

        result = mockMvc.perform(get("/users")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(get("/users")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", hasItem(userMapper.toDto(user))))
                .andExpect(model().attribute("users", hasItem(userMapper.toDto(userService.findByLogin("Test2")))))
                .andReturn();

    }

    @Test
    public void allUsersGetWithDefaultRole() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        assert session != null;

        result = mockMvc.perform(get("/users")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(get("/users")
                .session(session))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void addUserGet() throws Exception {
        mockMvc.perform(get("/users/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPages/addUser"))
                .andExpect(forwardedUrl("/WEB-INF/pages/userPages/addUser.jsp"))
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))));
    }

    @Test
    public void addUserPostValidWithoutCsrf() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "DEFAULT"))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertNull(userService.findByLogin("Test2"));
    }

    @Test
    public void addUserPostValidWithCsrf() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPages/addUser"))
                .andExpect(forwardedUrl("/WEB-INF/pages/userPages/addUser.jsp"))
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        assertNull(userService.findByLogin("Test2"));

        mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(forwardedUrl("/WEB-INF/pages/main.jsp"))
                .andReturn();

        assertNotNull(userService.findByLogin("Test2"));
    }

    @Test
    public void addUserPostNotValidNameTooShort() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPages/addUser"))
                .andExpect(forwardedUrl("/WEB-INF/pages/userPages/addUser.jsp"))
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        assertNull(userService.findByLogin("Test2"));

        String[] codes = {"Size.user.name", "Size.name", "Size.java.lang.String", "Size"};
        String[] codes2 = {"user.name", "name"};
        DefaultMessageSourceResolvable defaultMessageSourceResolvable =
                new DefaultMessageSourceResolvable(codes2, null, "name");
        Object[] arguments = {defaultMessageSourceResolvable, 60, 4};
        FieldError fieldError = new FieldError("user", "name", "123",
                false, codes, arguments,
                "Should be bigger than 3 and less than 61");

        mockMvc.perform(post("/users/addUser")
                .param("name", "123")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andExpect(model().attribute("fieldErrors", hasSize(1)))
                .andExpect(model().attribute("fieldErrors", hasItem(fieldError)))
                .andReturn();

        assertNull(userService.findByLogin("Test2"));

    }

    @Test
    public void addUserPostNotValidNameTooLong() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPages/addUser"))
                .andExpect(forwardedUrl("/WEB-INF/pages/userPages/addUser.jsp"))
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        assertNull(userService.findByLogin("Test2"));

        String[] codes = {"Size.user.name", "Size.name", "Size.java.lang.String", "Size"};
        String[] codes2 = {"user.name", "name"};
        DefaultMessageSourceResolvable defaultMessageSourceResolvable =
                new DefaultMessageSourceResolvable(codes2, null, "name");
        Object[] arguments = {defaultMessageSourceResolvable, 60, 4};
        FieldError fieldError = new FieldError("user", "name",
                "0123456789012345678901234567890123456789012345678901234567891",
                false, codes, arguments,
                "Should be bigger than 3 and less than 61");

        mockMvc.perform(post("/users/addUser")
                .param("name", "0123456789012345678901234567890123456789012345678901234567891")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andExpect(model().attribute("fieldErrors", hasSize(1)))
                .andExpect(model().attribute("fieldErrors", hasItem(fieldError)))
                .andReturn();

        assertNull(userService.findByLogin("Test2"));

    }

    @Test
    public void addUserPostNotValidLoginTooShort() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPages/addUser"))
                .andExpect(forwardedUrl("/WEB-INF/pages/userPages/addUser.jsp"))
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        assertNull(userService.findByLogin("Test2"));

        String[] codes = {"Size.user.login", "Size.login", "Size.java.lang.String", "Size"};
        String[] codes2 = {"user.login", "login"};
        DefaultMessageSourceResolvable defaultMessageSourceResolvable =
                new DefaultMessageSourceResolvable(codes2, null, "login");
        Object[] arguments = {defaultMessageSourceResolvable, 60, 4};
        FieldError fieldError = new FieldError("user", "login", "123",
                false, codes, arguments,
                "Should be bigger than 3 and less than 61");

        mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "123")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andExpect(model().attribute("fieldErrors", hasSize(1)))
                .andExpect(model().attribute("fieldErrors", hasItem(fieldError)))
                .andReturn();

        assertNull(userService.findByLogin("Test2"));
        assertNull(userService.findByLogin("123"));

    }

    @Test
    public void addUserPostNotValidLoginTooLong() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPages/addUser"))
                .andExpect(forwardedUrl("/WEB-INF/pages/userPages/addUser.jsp"))
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        assertNull(userService.findByLogin("Test2"));

        String[] codes = {"Size.user.login", "Size.login", "Size.java.lang.String", "Size"};
        String[] codes2 = {"user.login", "login"};
        DefaultMessageSourceResolvable defaultMessageSourceResolvable =
                new DefaultMessageSourceResolvable(codes2, null, "login");
        Object[] arguments = {defaultMessageSourceResolvable, 60, 4};
        FieldError fieldError = new FieldError("user", "login",
                "0123456789012345678901234567890123456789012345678901234567891",
                false, codes, arguments,
                "Should be bigger than 3 and less than 61");

        mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "0123456789012345678901234567890123456789012345678901234567891")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andExpect(model().attribute("fieldErrors", hasSize(1)))
                .andExpect(model().attribute("fieldErrors", hasItem(fieldError)))
                .andReturn();

        assertNull(userService.findByLogin("Test2"));
        assertNull(userService.findByLogin("0123456789012345678901234567890123456789012345678901234567891"));
    }

    @Test
    public void addUserPostNotValidPasswordTooShort() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPages/addUser"))
                .andExpect(forwardedUrl("/WEB-INF/pages/userPages/addUser.jsp"))
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        assertNull(userService.findByLogin("Test2"));

        String[] codes = {"Size.user.password", "Size.password", "Size.java.lang.String", "Size"};
        String[] codes2 = {"user.password", "password"};
        DefaultMessageSourceResolvable defaultMessageSourceResolvable =
                new DefaultMessageSourceResolvable(codes2, null, "password");
        Object[] arguments = {defaultMessageSourceResolvable, 60, 4};
        FieldError fieldError = new FieldError("user", "password", "123",
                false, codes, arguments,
                "Should be bigger than 3 and less than 61");

        mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "123")
                .param("role", "DEFAULT")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andExpect(model().attribute("fieldErrors", hasSize(1)))
                .andExpect(model().attribute("fieldErrors", hasItem(fieldError)))
                .andReturn();

        assertNull(userService.findByLogin("Test2"));

    }

    @Test
    public void addUserPostNotValidPasswordTooLong() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPages/addUser"))
                .andExpect(forwardedUrl("/WEB-INF/pages/userPages/addUser.jsp"))
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        assertNull(userService.findByLogin("Test2"));

        String[] codes = {"Size.user.password", "Size.password", "Size.java.lang.String", "Size"};
        String[] codes2 = {"user.password", "password"};
        DefaultMessageSourceResolvable defaultMessageSourceResolvable =
                new DefaultMessageSourceResolvable(codes2, null, "password");
        Object[] arguments = {defaultMessageSourceResolvable, 60, 4};
        FieldError fieldError = new FieldError("user", "password",
                "0123456789012345678901234567890123456789012345678901234567891",
                false, codes, arguments,
                "Should be bigger than 3 and less than 61");

        mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "0123456789012345678901234567890123456789012345678901234567891")
                .param("role", "DEFAULT")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andExpect(model().attribute("fieldErrors", hasSize(1)))
                .andExpect(model().attribute("fieldErrors", hasItem(fieldError)))
                .andReturn();

        assertNull(userService.findByLogin("Test2"));
    }

    @Test
    public void addUserPostNotValidRoleNull() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPages/addUser"))
                .andExpect(forwardedUrl("/WEB-INF/pages/userPages/addUser.jsp"))
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andReturn();

        MockHttpSession session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        assertNull(userService.findByLogin("Test2"));

        mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("roles", hasItem(roleMapper.toDto(role))))
                .andExpect(model().attribute("fieldErrors", hasSize(1)))
                .andReturn();

        assertNull(userService.findByLogin("Test2"));
    }

    @Test
    public void updateUserGetWithAdmin() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int userId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "ADMIN")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        userId = userService.findByLogin("Test2").getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + userId + "/update")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + userId + "/update"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(get("/users/" + userId + "/updateUser")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(userService.findByLogin("Test2"))))
                .andReturn();

    }

    @Test
    public void updateUserGetWithDefaultSelfAccount() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int userId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        userId = userService.findByLogin("Test2").getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + userId + "/update")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + userId + "/update"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(get("/users/" + userId + "/updateUser")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(userService.findByLogin("Test2"))))
                .andReturn();

    }

    @Test
    public void updateUserGetWithDefaultForeignAccount() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int foreignUserId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        foreignUserId = user.getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + foreignUserId + "/update")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + foreignUserId + "/update"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(get("/users/" + foreignUserId + "/updateUser")
                .session(session))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void updateUserPostForeignWithAdminWithoutChanges() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int foreignUserId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "ADMIN")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        foreignUserId = user.getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + foreignUserId + "/update")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + foreignUserId + "/update"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(get("/users/" + foreignUserId + "/updateUser")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(user)))
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(post("/users/" + foreignUserId + "/updateUser")
                .param("id", String.valueOf(user.getId()))
                .param("name", "Test")
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("role", user.getRole().toString())
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(user)))
                .andReturn();
    }

    @Test
    public void updateUserPostSelfWithAdminWithoutChanges() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int userId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "ADMIN")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        userId = userService.findByLogin("Test2").getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + userId + "/update")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + userId + "/update"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(get("/users/" + userId + "/updateUser")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(userService.findByLogin("Test2"))))
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(post("/users/" + userId + "/updateUser")
                .param("id", String.valueOf(userService.findByLogin("Test2").getId()))
                .param("name", "Test")
                .param("login", userService.findByLogin("Test2").getLogin())
                .param("password", userService.findByLogin("Test2").getPassword())
                .param("role", userService.findByLogin("Test2").getRole().toString())
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(userService.findByLogin("Test2"))))
                .andReturn();
    }

    @Test
    public void updateUserPostForeignWitDefaultWithoutChanges() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int foreignUserId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        foreignUserId = user.getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + foreignUserId + "/update")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + foreignUserId + "/update"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        List<Role> roles = roleService.findAll();
        roles.remove(user.getRole());

        mockMvc.perform(get("/users/" + foreignUserId + "/updateUser")
                .session(session))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void updateUserPostSelfWithDefaultWithoutChanges() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int userId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "DEFAULT")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        userId = userService.findByLogin("Test2").getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + userId + "/update")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + userId + "/update"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(get("/users/" + userId + "/updateUser")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(userService.findByLogin("Test2"))))
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        mockMvc.perform(post("/users/" + userId + "/updateUser")
                .param("id", String.valueOf(userService.findByLogin("Test2").getId()))
                .param("name", "Test")
                .param("login", userService.findByLogin("Test2").getLogin())
                .param("password", userService.findByLogin("Test2").getPassword())
                .param("role", userService.findByLogin("Test2").getRole().toString())
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(userService.findByLogin("Test2"))))
                .andReturn();
    }

    @Test
    public void updateUserPostForeignWithAdminValidNewName() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int foreignUserId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "ADMIN")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        foreignUserId = user.getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + foreignUserId + "/update")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + foreignUserId + "/update"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(get("/users/" + foreignUserId + "/updateUser")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(user)))
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        user.setName("NewTest");

        mockMvc.perform(post("/users/" + foreignUserId + "/updateUser")
                .param("id", String.valueOf(user.getId()))
                .param("name", "NewTest")
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("role", user.getRole().toString())
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(user)))
                .andReturn();

    }

    @Test
    public void updateUserPostForeignWithAdminNotValidNewName() throws Exception {

        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int foreignUserId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "ADMIN")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        foreignUserId = user.getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + foreignUserId + "/update")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + foreignUserId + "/update"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(get("/users/" + foreignUserId + "/updateUser")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userMapper.toDto(user)))
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        String[] codes = {"Size.user.name", "Size.name", "Size.java.lang.String", "Size"};
        String[] codes2 = {"user.name", "name"};
        DefaultMessageSourceResolvable defaultMessageSourceResolvable =
                new DefaultMessageSourceResolvable(codes2, null, "name");
        Object[] arguments = {defaultMessageSourceResolvable, 60, 4};
        FieldError fieldError = new FieldError("user", "name", "123",
                false, codes, arguments,
                "Should be bigger than 3 and less than 61");


        mockMvc.perform(post("/users/" + foreignUserId + "/updateUser")
                .param("id", String.valueOf(user.getId()))
                .param("name", "123")
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("role", user.getRole().toString())
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("fieldErrors", hasSize(1)))
                .andExpect(model().attribute("fieldErrors", hasItem(fieldError)))
                .andReturn();

    }

    @Test
    public void deleteUserPostSelfWithAdmin() throws Exception {
        assertNull(userService.findByLogin("Test2"));

        MvcResult result;
        MockHttpSession session;

        int userId;

        result = mockMvc.perform(post("/users/addUser")
                .param("name", "Test2")
                .param("login", "Test2")
                .param("password", "Test2")
                .param("role", "ADMIN")
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assertNotNull(userService.findByLogin("Test2"));

        userId = userService.findByLogin("Test2").getId();

        assert session != null;

        result = mockMvc.perform(get("/users/" + userId + "/deleteUser")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        result = mockMvc.perform(post("/login")
                .param("username", "Test2")
                .param("password", "Test2")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/" + userId + "/deleteUser"))
                .andExpect(authenticated())
                .andReturn();

        session = (MockHttpSession) result
                .getRequest()
                .getSession();

        assert session != null;

        List<Role> roles = roleService.findAll();
        roles.remove(userService.findByLogin("Test2").getRole());

        mockMvc.perform(post("/users/" + userId + "/deleteUser")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }
}