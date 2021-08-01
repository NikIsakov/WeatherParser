import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Model model = new Model();

        Scanner scanner = new Scanner(System.in);
        //String message = scanner.nextLine(); // дл ввода через консоль любого другого города
        String message = "Санкт-петербург"; // фикс. значение города

        System.out.println(Weather.getWeather(message,model));
    }
}
