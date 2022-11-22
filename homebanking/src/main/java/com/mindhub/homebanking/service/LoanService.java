package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoansRepositort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public interface LoanService {
    public Set<LoanDTO> getLoansDTO();

    public Loan getLoanById(long id);

    public void  saveLoan(Loan loan);

    public void  deleteLoan(long id);
}
