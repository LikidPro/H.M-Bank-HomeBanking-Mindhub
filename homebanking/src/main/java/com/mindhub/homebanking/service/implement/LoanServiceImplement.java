package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoansRepositort;
import com.mindhub.homebanking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    LoansRepositort loansRepositort;

    public Set<LoanDTO> getLoansDTO(){
        return loansRepositort.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toSet());
    }

    @Override
    public Loan getLoanById(long id) {
        return loansRepositort.findById(id).orElse(null);
    }

    @Override
    public void saveLoan(Loan loan) {
        loansRepositort.save(loan);
    }

    @Override
    public void deleteLoan(long id) {
        loansRepositort.deleteById(id);
    }

}
