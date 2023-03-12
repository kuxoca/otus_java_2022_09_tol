package ru.otus.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.service.DbServiceClient;

import java.util.List;
import java.util.Set;

@Controller
public class HwController {
    private final DbServiceClient serviceClient;

    public HwController(DbServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/clients")
    public String clients(Model model) {

        List<Client> clients = serviceClient.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping(value = "/api/v1/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Client> allClients() {
        List<Client> clients;
        clients = serviceClient.findAll();
        return clients;
    }

    @PostMapping("/clients")
    public String saveClient(@RequestParam
                             String name,
                             String address,
                             String phone1,
                             String phone2
    ) {
        Client client = Client.builder()
                .name(name)
                .address(Address.builder().street(address).build())
                .phones(Set.of(
                        Phone.builder().number(phone1).build(),
                        Phone.builder().number(phone2).build()))
                .build();
        serviceClient.saveClient(client);
        return "redirect:clients";
    }

}
