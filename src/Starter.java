public class Starter {

    public static void main(String[] args) {
        Service service = new Service();
        String expression = "12-5+1*5-4*20";
        System.out.println("Исходное выражение: "+ expression);
        double result = service.calc(expression);
        System.out.println("Результат: "+result);

    }
}
