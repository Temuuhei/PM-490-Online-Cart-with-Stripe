package com.pm490.PM490.model;

public class TransactionReport {
    private long id;
    private String concept;
    private double amount;
    private String dateShipped;

    public TransactionReport(long id, String concept, double amount, String dateShipped) {
        this.id = id;
        this.concept = concept;
        this.amount = amount;
        this.dateShipped = dateShipped;
    }

    @Override
    public String toString() {
        return "TransactionReport{" +
                "id=" + id +
                ", concept='" + concept + '\'' +
                ", amount=" + amount +
                ", dateShipped=" + dateShipped +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(String dateShipped) {
        this.dateShipped = dateShipped;
    }
}
