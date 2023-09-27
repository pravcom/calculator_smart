package org.example;

public class UnknownOperation extends Exception{
    @Override
    public String getMessage() {
        return "UnknownOperation";
    }
}
