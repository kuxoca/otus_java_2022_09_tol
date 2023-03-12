package ru.otus.service;

import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public interface DbServiceClient {
    List<Client> findAll();

    void saveClient(Client client);

    Optional<Client> getClientById(Long id);
}
