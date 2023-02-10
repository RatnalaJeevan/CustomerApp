package com.wisedrive.customerapp.pojos;

public class Pojo_Class_Plan_2 {
    String text1,text2,text3,Pacakge_name;

    public Pojo_Class_Plan_2(String text1,String text2,String text3,String Package_name){
        this.text1=text1;
        this.text2=text2;
        this.text3=text3;
        this.Pacakge_name=Package_name;

    }
    public String getText1(){
        return text1;
    }
    public void setText1(){
        this.text1=text1;
    }
    public String gettext2(){
        return text2;
    }
    public void settext2(){
        this.text2=text2;
    }
    public String getText3(){return text3;}
    public void setText3(){this.text3=text3;}
    public String getPacakge_name(){return Pacakge_name;}
    public void setPacakge_name(){this.Pacakge_name=Pacakge_name;}
}
