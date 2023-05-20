package com.naumen.blogeng.dto;

import jakarta.validation.constraints.NotEmpty;

public class DtoJoke {
    @NotEmpty
    private String text;

    public DtoJoke(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String toString() {
        return text;
    }

    public String[] getParagraphs() {
        return text.split(System.lineSeparator());
    }
}
