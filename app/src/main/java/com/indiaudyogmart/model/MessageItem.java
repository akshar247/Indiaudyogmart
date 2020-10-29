package com.indiaudyogmart.model;

public class MessageItem {
    private int CompanynName,Name,Manufacture,Message,Date,MessageCount;


    public MessageItem(int companyname,int name,int manufacture,int message,int date,int messageCount) {

        CompanynName= companyname;
        Name=name;
        Manufacture=manufacture;
        Message=message;
        Date=date;
        MessageCount=messageCount;

    }

    public int getCompanynName() {
        return CompanynName;
    }

    public void setCompanynName(int companynName) {
        CompanynName = companynName;
    }

    public int getName() {
        return Name;
    }

    public void setName(int name) {
        Name = name;
    }

    public int getManufacture() {
        return Manufacture;
    }

    public void setManufacture(int manufacture) {
        Manufacture = manufacture;
    }

    public int getMessage() {
        return Message;
    }

    public void setMessage(int message) {
        Message = message;
    }

    public int getDate() {
        return Date;
    }

    public void setDate(int date) {
        Date = date;
    }

    public int getMessageCount() {
        return MessageCount;
    }

    public void setMessageCount(int messageCount) {
        MessageCount = messageCount;
    }
}







