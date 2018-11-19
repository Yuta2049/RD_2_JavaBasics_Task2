public class Starter {

    public static void main(String[] args) {
        Service service = new Service();

        String expression = "12-5*((1.5+5) + 8*40)-4*20,5";

        expression = expression.replaceAll("\\s","");
        expression = expression.replaceAll(",",".");

        System.out.println("Исходное выражение: " + expression);
        double result = service.calc(expression);
        System.out.println("Результат: " + result);

    }
}
