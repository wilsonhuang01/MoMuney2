package com.example.momuney2.controllers;

import com.example.momuney2.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class FilterService {
    public FilterService() {

    }

    public List<Transaction> filterByName(List<Transaction> transactions, String[] keywords) {
        if (transactions == null) return null;
        if (keywords.length == 0) return transactions;

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

        return result;
    }

    public void filterByDate() {

    }

    public void filterByCategory() {

    }

    public void sortBy() {

    }
}
