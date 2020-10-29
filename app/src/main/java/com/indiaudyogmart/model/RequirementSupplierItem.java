package com.indiaudyogmart.model;

public class RequirementSupplierItem {
    private int CompanynName,Address,Email,Website,Name,Department;


    public RequirementSupplierItem(int companyname, int address, int email, int website, int name, int department) {

        CompanynName= companyname;
        Address=address;
        Email=email;
        Website=website;
        Name=name;
        Department=department;

    }

    public int getCompanynName() {
        return CompanynName;
    }

    public void setCompanynName(int companynName) {
        CompanynName = companynName;
    }

    public int getAddress() {
        return Address;
    }

    public void setAddress(int address) {
        Address = address;
    }

    public int getEmail() {
        return Email;
    }

    public void setEmail(int email) {
        Email = email;
    }

    public int getWebsite() {
        return Website;
    }

    public void setWebsite(int website) {
        Website = website;
    }

    public int getName() {
        return Name;
    }

    public void setName(int name) {
        Name = name;
    }

    public int getDepartment() {
        return Department;
    }

    public void setDepartment(int department) {
        Department = department;
    }
}







