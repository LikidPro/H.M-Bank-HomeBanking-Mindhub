package com.mindhub.homebanking.service;

import com.lowagie.text.DocumentException;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public interface PdfService {
    public void export(Account account, Set<Transaction> transactions, HttpServletResponse response) throws IOException, DocumentException;
}
