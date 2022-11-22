package com.mindhub.homebanking.DTO;

public class LoanApplicationDTO {
    private Long idLoan;
    private Double amount;
    private Integer payments;
    private  String numberAccountsDestiny;


    public LoanApplicationDTO(Long idLoan, Double amount, int payments, String numberAccountsDestiny) {
        this.idLoan = idLoan;
        this.amount = amount;
        this.payments = payments;
        this.numberAccountsDestiny = numberAccountsDestiny;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(long idLoan) {
        this.idLoan = idLoan;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getNumberAccountsDestiny() {
        return numberAccountsDestiny;
    }

    public void setNumberAccountsDestiny(String numberAccountsDestiny) {this.numberAccountsDestiny = numberAccountsDestiny;}
}
