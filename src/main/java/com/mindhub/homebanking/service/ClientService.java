package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {
    public List<ClientDTO> getClientsDTO();

    public ClientDTO getClientDTO(long id);

    public Client getClientByEmail(String email);

    public void saveClient(Client client);

    public List<ClientDTO> getClientsDTODisabled();

    public void disabledClient(long id);

    public void enableClient(long id);

    public Client getClientById(long id);
}
