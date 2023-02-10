package com.wisedrive.customerapp.pojos;

public class Pojo_Q_And_A {
    String tv_question,tv_select_option;

    public Pojo_Q_And_A(String tv_question, String tv_select_option) {
        this.tv_question = tv_question;
        this.tv_select_option = tv_select_option;
    }

    public String getTv_question() {
        return tv_question;
    }

    public void setTv_question(String tv_question) {
        this.tv_question = tv_question;
    }

    public String getTv_select_option() {
        return tv_select_option;
    }

    public void setTv_select_option(String tv_select_option) {
        this.tv_select_option = tv_select_option;
    }
}
