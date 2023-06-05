package com.wisedrive.customerapp.pojos;

import java.util.ArrayList;

public class PojoInspQ {

    String question;
    String question_id;

    ArrayList<PojoInspA> answerList;
    public String getQuestion() {
        return question;
    }

    public ArrayList<PojoInspA> getAnswerList() {
        return answerList;
    }

    public String getQuestion_id() {
        return question_id;
    }


}
