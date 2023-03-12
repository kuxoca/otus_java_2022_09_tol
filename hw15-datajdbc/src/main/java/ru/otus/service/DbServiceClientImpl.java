package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbServiceClientImpl implements DbServiceClient {

    private final ClientRepository clientRepository;

    public DbServiceClientImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        List<Client> list = new ArrayList<>();
        clientRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public void saveClient(Client client) {
         clientRepository.save(client);
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
}
