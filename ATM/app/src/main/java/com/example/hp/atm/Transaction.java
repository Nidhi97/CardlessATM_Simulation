package com.example.hp.atm;

/**
 * Created by LENOVO on 10-05-2018.
 */

public class Transaction {
    float amount;
    int acc_no;
    String acc_type;
    String atm_no;
    String code;
    String date_of_Transaction;
    boolean isComplete;
    String method_used;
    public Transaction(){

    }

    public Transaction(float amount, int acc_no, String acc_type, String atm_no, String code, String date_of_Transaction, boolean isComplete, String method_used) {
        this.amount = amount;
        this.acc_no = acc_no;
        this.acc_type = acc_type;
        this.atm_no = atm_no;
        this.code = code;
        this.date_of_Transaction = date_of_Transaction;
        this.isComplete = isComplete;
        this.method_used = method_used;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(int acc_no) {
        this.acc_no = acc_no;
    }

    public String getAcc_type() {
        return acc_type;
    }

    public void setAcc_type(String acc_type) {
        this.acc_type = acc_type;
    }

    public String getAtm_no() {
        return atm_no;
    }

    public void setAtm_no(String atm_no) {
        this.atm_no = atm_no;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate_of_Transaction() {
        return date_of_Transaction;
    }

    public void setDate_of_Transaction(String date_of_Transaction) {
        this.date_of_Transaction = date_of_Transaction;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getMethod_used() {
        return method_used;
    }

    public void setMethod_used(String method_used) {
        this.method_used = method_used;
    }
}
