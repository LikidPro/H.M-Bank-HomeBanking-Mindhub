package com.mindhub.homebanking.service.implement;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepositort;
import com.mindhub.homebanking.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {
    @Autowired
    private ClientLoanRepositort clientLoanRepositort;

    @Override
    public void clientLoanSave(ClientLoan clientLoan) {
        clientLoanRepositort.save(clientLoan);
    }
}
