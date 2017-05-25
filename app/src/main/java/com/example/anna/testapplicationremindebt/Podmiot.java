package com.example.anna.testapplicationremindebt;

import java.io.Serializable;

public class Podmiot implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nazwa = "";


    public Podmiot(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public String toString() {
        return nazwa;
    }
}
