package org.example;

import java.lang.ref.Reference;
import java.util.*;

public class TokensSets {
    private String infixExp;

    //	Список и приоритет операторов
    private Map<Character,Integer> operationPriority = new HashMap<>();
    public TokensSets(){
        operationPriority.put('(', 0);
        operationPriority.put('+', 1);
        operationPriority.put('-', 1);
        operationPriority.put('*', 2);
        operationPriority.put('/', 2);
        operationPriority.put('^', 3);
        operationPriority.put('~', 4); //	Унарный минус
    }
    public void setInfixExp(String str) throws UnknownOperation{
        this.infixExp = str.replace(" ", "");
        int count = 0;
        char currentSymbol;
        for(int i=0;i<infixExp.length();i++){
            currentSymbol = infixExp.charAt(i);
            if ((currentSymbol != '(' && currentSymbol != ')' ) && operationPriority.containsKey(currentSymbol)){
                count++;
                if (count > 1){
                    throw new UnknownOperation();
                }
            } else if (Character.isDigit(currentSymbol)) {
                count = 0;
            } else if ((currentSymbol != '(' && currentSymbol != ')' ) && !operationPriority.containsKey(currentSymbol)) {
                throw new UnknownOperation();
            }
        }

    }
    public String getInfixExp() {
        return infixExp;
    }

    /**
     * Получаем PostFixExp
     */
    public String getPostFixExp(){
        String postFixExp = "";
        Stack<Character> stackOfOperations = new Stack<>();

        char separator = ',';

        for(int i = 0; i < infixExp.length(); i++){
            char currentSymbol = infixExp.charAt(i);
            //Если символ Цифра
            if (Character.isDigit(currentSymbol)){
                postFixExp += currentSymbol;
                //Если сепаратор
            } else if (currentSymbol == separator) {
                postFixExp += currentSymbol;
            }
            //Если открывающаяся скобка
            else if (currentSymbol == '(') {
                //Ставим разделитель в выходную строку чтобы обозначить конец числа
                postFixExp += '|';
                stackOfOperations.push(currentSymbol);
            }
            //Если закрвыающаяся скобка
            else if (currentSymbol == ')'){
                //Ставим разделитель в выходную строку чтобы обозначить конец числа
                postFixExp += '|';
                //Выносим все значения из стэка до открывающейся скобки
                while (!(stackOfOperations.empty()) && stackOfOperations.peek() != '(' ){
                    postFixExp += stackOfOperations.pop();
                }
                //Удаляем открывающаюсю скобку
                stackOfOperations.pop();
            }
            //Проверяем содержится ли символ в списке операций
            else if (operationPriority.containsKey(currentSymbol)) {
                char symbol = currentSymbol;
                //Ставим разделитель в выходную строку чтобы обозначить конец числа
                postFixExp += '|';
                //проверяем знак - , что он унарный
                if(symbol == '-' && (i == 0 || ( i > 1 && operationPriority.containsKey(infixExp.charAt(i - 1))))){
                    symbol = '~';
                }
                //	Заносим в выходную строку все операторы из стека, имеющие более высокий приоритет
                while (!(stackOfOperations.empty()) && operationPriority.get(stackOfOperations.peek()) >= operationPriority.get(symbol)){
                    postFixExp += stackOfOperations.pop();
                }
                //	Заносим в стек оператор
                stackOfOperations.push(symbol);
            }
        }
        //	Заносим все оставшиеся операторы из стека в выходную строку
        while (!(stackOfOperations.empty()))
            postFixExp += stackOfOperations.pop();

        //Возвращаем строку
        return postFixExp;
    }

    /**
     * Парсинг целочисленных значений
     * @param expression Строка
     * @param pos Номер позиции указателя в строке
     * @return Число
     */
    public String GetStringNumber(String expression, Integer pos){
        //	Хранит число
        String strNum = "";
        //	Перебираем строку
        for (;pos < expression.length(); pos++){
            //	Разбираемый символ строки
            char currentSymbol = expression.charAt(pos);
            //	Проверяем, является символ числом
            if (Character.isDigit(currentSymbol) ){
                strNum += currentSymbol;
            }
            //	Проверяем, является символ разделитем
            else if (currentSymbol == ',' || currentSymbol == '.') {
                strNum += '.';
            } else {
                // Выходим из цикла предварительно сместив счетчик назад
                pos--;
                break;
            }
        }
        return strNum;
    }

    public Map<Character, Integer> getOperationPriority() {
        return operationPriority;
    }
}
