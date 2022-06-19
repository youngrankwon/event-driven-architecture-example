package com.example.customer.webapi;

public class FindCustomerResponse {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Long creditLimit) {
        this.creditLimit = creditLimit;
    }

    private String name;
    private Long creditLimit;

    public FindCustomerResponse(Long id, String name, Long creditLimit) {
        this.id = id;
        this.name = name;
        this.creditLimit = creditLimit;
    }
}
