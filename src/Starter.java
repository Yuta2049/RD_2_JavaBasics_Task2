public class Starter {

    public static void main(String[] args) {
        Service service = new Service();
        String expression = "12-5";
        double result = service.calc(expression);
        System.out.println("Выражение: "+ expression);
        System.out.println("Результат: "+result);
        
    }
}
