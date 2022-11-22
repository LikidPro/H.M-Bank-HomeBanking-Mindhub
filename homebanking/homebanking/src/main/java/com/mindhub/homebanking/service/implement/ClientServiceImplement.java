package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepositort;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    ClientRepositort clientRepositort;
    @Override
    public List<ClientDTO> getClientsDTO(){
        return clientRepositort.findAll().stream().filter(client -> client.isClientState() == true).map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }
    @Override
    public ClientDTO getClientDTO(long id){
        return clientRepositort.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }
    @Override
    public Client getClientByEmail(String email) {
        return clientRepositort.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {clientRepositort.save(client);}

    @Override
    public List<ClientDTO> getClientsDTODisabled() {
        return clientRepositort.findAll().stream().filter(client -> client.isClientState() == false).map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public void disabledClient(long id) {
    Client client=clientRepositort.findById(id).orElse(null);
    client.setClientState(Boolean.FALSE);
    clientRepositort.save(client);

    }

    @Override
    public void enableClient(long id)  {Client client=clientRepositort.findById(id).orElse(null);
        client.setClientState(Boolean.TRUE);
        clientRepositort.save(client);}

    @Override
    public Client getClientById(long id) {
        return clientRepositort.findById(id).orElse(null);
    }


}
