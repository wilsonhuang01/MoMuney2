package com.example.momuney2.controllers;

import com.example.momuney2.models.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilterService {
    private List<Transaction> transactions;

    public FilterService(List<Transaction> transactions) {
        reset(transactions);
    }

    public void reset(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean filterByName(String[] keywords) {
        if (transactions == null || keywords.length == 0) return false;

        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            boolean match = true;

            for (String keyword : keywords) {
                if (!transaction.getVendor().getName().toLowerCase().contains(keyword)
                        && !transaction.getVendor().getLocation().toLowerCase().contains(keyword)) {
                    match = false;
                }
            }

            if (match) result.add(transaction);
        }

        transactions = result;
        return transactions.size() != result.size();
    }

    public boolean filterByDate(LocalDate dateFrom, LocalDate dateTo) {
        if (transactions == null || dateFrom == null && dateTo == null) return false;

        List<Transaction> result = new ArrayList<>();
        if (dateFrom != null && dateTo != null) { // dateFrom <= transaction.date <= dateTo
            for (Transaction transaction : transactions) {
                if (transaction.getDate().compareTo(dateFrom) >= 0 && transaction.getDate().compareTo(dateTo) <= 0) {
                    result.add(transaction);
                }
            }
        } else if (dateFrom != null) { // dateFrom <= transaction.date
            for (Transaction transaction : transactions) {
                if (transaction.getDate().compareTo(dateFrom) >= 0) {
                    result.add(transaction);
                }
            }
        } else { // transaction.date <= dateTo
            for (Transaction transaction : transactions) {
                if (transaction.getDate().compareTo(dateTo) <= 0) {
                    result.add(transaction);
                }
            }
        }

        transactions = result;
        return transactions.size() != result.size();
    }

    public boolean filterByCategory(String category) {
        if (transactions == null || category == null || category.equals("All")) return false;

        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equals(category)) {
                result.add(transaction);
            }
        }

        transactions = result;
        return transactions.size() != result.size();
    }

    public void sortBy() {

    }
}
