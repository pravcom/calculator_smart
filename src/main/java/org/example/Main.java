package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Input value: ");
        String value = in.nextLine();
        //Создаем токен
        TokensSets token = new TokensSets();
        try {
            token.setInfixExp(value);
        } catch (UnknownOperation e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println(token.getInfixExp());
        System.out.println(token.getPostFixExp());
        //Высчитываем данные из токена
        Calculator calculator = new Calculator(token);
        System.out.println("Result: "+calculator.calc());

    }
}