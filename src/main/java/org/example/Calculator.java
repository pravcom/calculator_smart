package org.example;

import java.util.Stack;

public class Calculator {
    private Stack<Double> stackOfNums = new Stack<Double>();
    private Stack<Character> stackOfOperations = new Stack<>();
    private TokensSets token;

    /**
     * Вычесления согласно оператору
     *
     * @param operation
     * @param firstNum
     * @param secondNum
     * @return
     */
    private double Execute(char operation, double firstNum, double secondNum) throws UnknownOperation, DivideNull {
        double result = 0;
        switch (operation) {
            case '+':
                result = firstNum + secondNum;
                break;
            case '-':
                result = firstNum - secondNum;
                break;
            case '*':
                result = firstNum * secondNum;
                break;
            case '/':
                if (secondNum == 0) {
                    throw new DivideNull();
                }
                result = firstNum / secondNum;
                break;
            case '^':
                result = Math.pow(firstNum,secondNum);
                break;
            default:
                throw new UnknownOperation();
        }
        return result;
    }

    /**
     * Производим вычесления по токену
     *
     * @return
     */
    public double calc() {
        String postFix = null;
        postFix = token.getPostFixExp();
        int counter = 0;//Cчетчик операции
        for (int i = 0; i < postFix.length(); ) {

            char currentSymbol = postFix.charAt(i);

            //Если число то помещаем его в стэк чисел
            if (Character.isDigit(currentSymbol)) {
                String strNum = token.GetStringNumber(postFix, i);
                stackOfNums.push(Double.parseDouble(strNum));
                i = i + strNum.length();
            }
            // Если операция
            else if (token.getOperationPriority().containsKey(currentSymbol)) {
                //Если операция унарная
                if (currentSymbol == '~') {
                    //	Проверяем, пуст ли стек: если да - задаём нулевое значение,
                    //	еси нет - выталкиваем из стека значение
                    double last = stackOfNums.size() > 0 ? stackOfNums.pop() : 0;
                    //Получаем отрицательное число и заносим его в стэк
                    try {
                        stackOfNums.push(Execute('-', 0, last));
                    } catch (UnknownOperation e) {
                        System.out.println(e.getMessage());
                        e.getStackTrace();
                    } catch (DivideNull e) {
                        System.out.println(e.getMessage());
                        e.getStackTrace();
                    }
                    //	Указываем, что нужно перейти к следующей итерации цикла
                    i++;
                    continue;
                }
                //Получаем значения из стека. Сначала secondNum затем firstNum
                double secondNum = stackOfNums.size() > 0 ? stackOfNums.pop() : 0;
                double firstNum = stackOfNums.size() > 0 ? stackOfNums.pop() : 0;
                // Получаем результат операции и заносим в стэк
                try {
                    stackOfNums.push(Execute(currentSymbol, firstNum, secondNum));
                } catch (UnknownOperation e) {
                    System.out.println(e.getMessage());
                    e.getStackTrace();
                } catch (DivideNull e) {
                    System.out.println(e.getMessage());
                    e.getStackTrace();
                }
                //	Отчитываемся пользователю о проделанной работе
                i++;
                counter++;
                System.out.println(counter + ": " + firstNum + currentSymbol + secondNum + " = " + stackOfNums.peek());
            } else//Если попался другой символ в строке, то пропускаем его и идем дальше
            {
                i++;
            }
        }
        return stackOfNums.pop();
    }

    public TokensSets getToken() {
        return token;
    }

    /**
     * При создании инстанции класса передаем ему токен с выражением в постфиксной форме
     *
     * @param token Токен
     */
    public Calculator(TokensSets token) {
        this.token = token;
    }
}
