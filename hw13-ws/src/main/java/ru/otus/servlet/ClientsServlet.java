package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";
    private static final String FROM_FORM_CLIENT_NAME = "name";
    private static final String FROM_FORM_CLIENT_ADDRESS = "address";
    private static final String FROM_FORM_CLIENT_PHONE_1 = "phone1";
    private static final String FROM_FORM_CLIENT_PHONE_2 = "phone2";
    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR_CLIENTS, dbServiceClient.findAll());

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var param = req.getParameterMap();

        Client client = new Client();
        client.setName(param.get(FROM_FORM_CLIENT_NAME)[0]);

        Address address = new Address(null, param.get(FROM_FORM_CLIENT_ADDRESS)[0]);
        client.setAddress(address);

        List<Phone> phones = new ArrayList<>();
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        phone1.setNumber(param.get(FROM_FORM_CLIENT_PHONE_1)[0]);
        phones.add(phone1);
        if (param.get(FROM_FORM_CLIENT_PHONE_2)[0].length() != 0) {
            phone2.setNumber(param.get(FROM_FORM_CLIENT_PHONE_2)[0]);
            phones.add(phone2);
        }
        client.setPhones(phones);

        try {
            dbServiceClient.saveClient(client);
        } catch (DataTemplateException e) {
            log("Ошибка сохранения");
        }

        resp.sendRedirect(TEMPLATE_ATTR_CLIENTS);
    }
}
