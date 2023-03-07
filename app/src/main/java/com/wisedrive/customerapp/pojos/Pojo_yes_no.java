package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_yes_no {
   @SerializedName("answer")
    String answer;
    @SerializedName("answer_id")
    String answer_id;

    String isSelected="n";
    public String getAnswer() {
        return answer;
    }

    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
}
