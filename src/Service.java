import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    private class PositionWithResult {
        private int position;
        private double result;

        public int getPosition() {
            return position;
        }

        public double getResult() {
            return result;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void setResult(double result) {
            this.result = result;
        }
    }

    private PositionWithResult getLeftNumber(String expression, int indexOfSign) {

        String number = "";
        int position = 0;
        for (int i = indexOfSign - 1; i >= 0; i--) {
            char currentChar = expression.charAt(i);
            if ((Character.isDigit(currentChar) || currentChar == '.' || currentChar == ',') || (currentChar == '-' && i == 0)) {
                number = expression.charAt(i) + number;
                position = i;
            } else {
                break;
            }
        }

        //System.out.println("Левое число: "+number);

        double numberDouble = 0.0;
        try {
            numberDouble = Double.parseDouble(number);
        } catch (Exception e) {
            System.out.println("Не получилось преобразовать текст " + number + " в число");
            throw new NumberFormatException();
        }


        PositionWithResult positionWithResult = new PositionWithResult();
        positionWithResult.setPosition(position);
        positionWithResult.setResult(numberDouble);

        return positionWithResult;
    }

    private PositionWithResult getRightNumber(String expression, int indexOfSign) {

        String number = "";
        int position = 0;
        for (int i = indexOfSign + 1; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            if (Character.isDigit(currentChar) || currentChar == '.' || currentChar == ',') {
                number = number + expression.charAt(i);
                position = i;
            } else {
                break;
            }
        }

        //System.out.println("Правое число: "+number);

        double numberDouble = 0.0;
        try {
            numberDouble = Double.parseDouble(number);
        } catch (Exception e) {
            System.out.println("Не удалось преобразовать текст " + number + " в число");
            throw new NumberFormatException();
        }

        PositionWithResult positionWithResult = new PositionWithResult();
        positionWithResult.setPosition(position);
        positionWithResult.setResult(numberDouble);

        return positionWithResult;
    }


    private double calcExpressionWithoutParenthesis(String expression) {

        double result = 0;

        while (Math.max(expression.indexOf("*", 1), expression.indexOf("/", 1)) > 0) {

            int indexMinus = expression.indexOf("/", 1);
            int indexPlus = expression.indexOf("*", 1);

            int indexOfSign = 0;
            String sign = "Plus";

            if ((indexMinus > 0 && indexPlus > 0 && indexMinus < indexPlus) || (indexMinus > 0 && indexPlus < 0)) {
                indexOfSign = indexMinus;
                sign = "Minus";
            } else if ((indexMinus > 0 && indexPlus > 0 && indexMinus > indexPlus) || (indexPlus > 0 && indexMinus < 0)) {
                indexOfSign = indexPlus;
                sign = "Plus";
            }

            PositionWithResult leftResult = getLeftNumber(expression, indexOfSign);
            PositionWithResult rightResult = getRightNumber(expression, indexOfSign);

            if (sign == "Minus") {
                result = leftResult.getResult() / rightResult.getResult();
            } else {
                result = leftResult.getResult() * rightResult.getResult();
            }

            // System.out.println("Левый символ: " + leftResult.getPosition());
            //System.out.println("Правый символ: " + rightResult.getPosition());

            expression = expression.substring(0, leftResult.getPosition()) + Double.valueOf(result) + expression.substring(rightResult.getPosition() + 1);

            System.out.println("Выражение после замены: " + expression);

        }


        // тут цикл пока не останется знаков действий
        //while (Math.max(expression.indexOf("+"), expression.indexOf("-"))+ Math.max(expression.indexOf("*"), expression.indexOf("/")) > -2)
        while (Math.max(expression.indexOf("+", 1), expression.indexOf("-", 1)) > 0) {

            int indexMinus = expression.indexOf("-", 1);
            int indexPlus = expression.indexOf("+", 1);

            int indexOfSign = 0;
            String sign = "Plus";

            if ((indexMinus > 0 && indexPlus > 0 && indexMinus < indexPlus) || (indexMinus > 0 && indexPlus < 0)) {
                indexOfSign = indexMinus;
                sign = "Minus";
            } else if ((indexMinus > 0 && indexPlus > 0 && indexMinus > indexPlus) || (indexPlus > 0 && indexMinus < 0)) {
                indexOfSign = indexPlus;
                sign = "Plus";
            }

            PositionWithResult leftResult = getLeftNumber(expression, indexOfSign);
            PositionWithResult rightResult = getRightNumber(expression, indexOfSign);

            if (sign == "Minus") {
                result = leftResult.getResult() - rightResult.getResult();
            } else {
                result = leftResult.getResult() + rightResult.getResult();
            }

            //System.out.println("Левый символ: " + leftResult.getPosition());
            //System.out.println("Правый символ: " + rightResult.getPosition());

            expression = expression.substring(0, leftResult.getPosition()) + Double.valueOf(result) + expression.substring(rightResult.getPosition() + 1);

            //System.out.println("Выражение после замены: " + expression);

        }

        return result;

    }

    public double calc(String expression) {

        double result = 0.0;

        // тут цикл пока не останется скобок
        while (Math.max(expression.indexOf("("), expression.indexOf(")")) > 0) {
            //String regex = "\\(.[^\\(\\)]*\\)";
            String regex = "[\\(][^\\(\\)]*+[\\)]";
            Pattern pattern = Pattern.compile(regex);

            Matcher match = pattern.matcher(expression);

            while (match.find()) {

                expression = match.replaceFirst(String.valueOf(calcExpressionWithoutParenthesis(expression.substring(match.start(), match.end()))));

                System.out.println("Выражение после замены: " + expression);
            }
        }

//        if ((Math.max(expression.indexOf("*", 1), expression.indexOf("/", 1)) > 0) ||
//           (Math.max(expression.indexOf("+", 1), expression.indexOf("-", 1)) > 0)) {

        try {
            result = Double.valueOf(expression);

        } catch (NumberFormatException e) {
            result = calcExpressionWithoutParenthesis(expression);
        }

        return result;
    }

}
