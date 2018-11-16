import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    private double calculateResult(String expression) {

        double result = 0;
        String regex = "[\\+\\-\\*\\/]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {

            int end = matcher.end();

            if (end == 1) {
                continue;
            }

            double leftNumber;
            double rightNumber;

            try {
                String stringLeftNumber = expression.substring(0, end-1);
                if (stringLeftNumber.indexOf(".", 0) < 0) {
                    stringLeftNumber = stringLeftNumber+".0";
                }
                leftNumber = Double.parseDouble(stringLeftNumber);


                 String stringRightNumber = expression.substring(end);
                if (stringRightNumber.indexOf(".", 0) < 0) {
                    stringRightNumber = stringRightNumber+".0";
                }
                rightNumber = Double.parseDouble(stringRightNumber);
            }
            catch (Exception e) {
                System.out.println("Не удалось вычислить выражение: " + expression + ". Ошибка при переводе в число");
                throw new NumberFormatException();
            }

            switch (expression.charAt(matcher.end()-1)) {
                case '*' : {result = leftNumber * rightNumber; break;}
                case '/' : {result = leftNumber / rightNumber; break;}
                case '+' : {result = leftNumber + rightNumber; break;}
                case '-' : {result = leftNumber - rightNumber; break;}
            }

    }
    return result;
}


    private double calcExpressionWithoutParenthesis(String expression) {

        double result = 0;
        String regex="";

        char symbol1;
        char symbol2;

        for (int i=0; i<2; i++) {

            if (i == 0) {
                regex = "[0-9\\.]+[\\*\\/][0-9\\.]+";
                symbol1 = '*';
                symbol2 = '/';
            } else {
                regex = "[0-9\\.]+[\\+\\-][0-9\\.]+";
                symbol1 = '+';
                symbol2 = '-';
            }

            while (Math.max(expression.indexOf(symbol1, 1), expression.indexOf(symbol2, 1)) > 0) {

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(expression);

                if (matcher.find()) {

                    int start = matcher.start();
                    int end = matcher.end();

                    if (start == 1 && expression.charAt(0) == '-') {
                        start = 0;
                    }

                    result = calculateResult(expression.substring(start, end));

                    expression = expression.substring(0, start) + Double.valueOf(result) + expression.substring(end);

                }
            }
        }

        return result;

    }

    public double calc(String expression) {

        double result = 0.0;

        // тут цикл пока не останется скобок
        while (Math.max(expression.indexOf("("), expression.indexOf(")")) > 0) {

            String regex = "[\\(][^\\(\\)]*+[\\)]";
            Pattern pattern = Pattern.compile(regex);

            Matcher match = pattern.matcher(expression);

            while (match.find()) {

                expression = match.replaceFirst(String.valueOf(calcExpressionWithoutParenthesis(expression.substring(match.start()+1, match.end()-1))));

                System.out.println("Выражение после замены: " + expression);
            }
        }

        try {
            result = Double.valueOf(expression);

        } catch (NumberFormatException e) {
            result = calcExpressionWithoutParenthesis(expression);
        }

        return result;
    }

}
