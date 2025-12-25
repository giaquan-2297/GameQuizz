package com.example.gamequizz.model;

public class Question {
    private String questionText;
    private String[] options;
    private int correctOptionIndex; // Index từ 0 đến 3

    public Question(String questionText, String a, String b, String c, String d, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = new String[]{a, b, c, d};
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }
}