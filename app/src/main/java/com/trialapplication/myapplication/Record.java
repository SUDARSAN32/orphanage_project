package com.trialapplication.myapplication;

public class Record {
    private String name;
    private String item_name;
    private String phone;
    private String address;

    public Record(String name, String item_name, String phone, String address) {
        this.name = name;
        this.item_name = item_name;
        this.phone = phone;
        this.address = address;
    }

    // Getter methods for each field

    public String getName() {
        return name;
    }

    public String getItemName() {
        return item_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
