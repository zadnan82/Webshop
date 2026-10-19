package se.iths.zainabadnan.webshop;

public class ConsoleOutInputHandler implements OutInputHandler {

    @Override
    public String prompt(String message) {
        return IO.readln(message);
    }

    @Override
    public void info(String message) {
        IO.println(message);
    }

    @Override
    public String menu() {
        info("");
        info("1. Lägg till produkt");
        info("2. Lista alla produkter");
        info("3. Visa information om en produkt");
        info("4. Avsluta");
        return prompt("Ditt val: ");
    }
}
