package com.sugiartha.juniorandroid;

public class HitungBMI {
    public String hitungBMI(double beratBadan, double tinggiBadan){
        String statusBadan;
        double BMI = beratBadan/(tinggiBadan*tinggiBadan);
        if(BMI<18.5){
            statusBadan = "Anda kekurangan berat badan.";
        } else if(BMI>=18.5 && BMI<=24.9){
            statusBadan = "Berat badan anda normal (ideal).";
        } else if(BMI>=25 && BMI<=29.9){
            statusBadan = "Anda kelebihan berat badan.";
        } else {
            statusBadan = "Anda kegemukan (obesitas).";
        }
        return statusBadan;
    }
}
