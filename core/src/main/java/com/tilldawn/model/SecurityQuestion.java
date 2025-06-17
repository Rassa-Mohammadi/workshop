package com.tilldawn.model;

import com.tilldawn.model.enums.Output;

public class SecurityQuestion {
    private Output question;
    private String answer;

    public SecurityQuestion(Output question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Output getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
