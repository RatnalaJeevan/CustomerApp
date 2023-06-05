package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pojo_Q_And_A {
    @SerializedName("symptom_question_id")
    String symptom_question_id;
    @SerializedName("symptom_name")
    String symptom_name;
    @SerializedName("is_input_field")
    String is_input_field;

    @SerializedName("Answerlist")
    ArrayList<Pojo_yes_no> Answerlist;

    public Pojo_Q_And_A() {
    }

    public String getSymptom_question_id() {
        return symptom_question_id;
    }

    public String getSymptom_name() {
        return symptom_name;
    }

    public String getIs_input_field() {
        return is_input_field;
    }

    public ArrayList<Pojo_yes_no> getAnswerlist() {
        return Answerlist;
    }
}
