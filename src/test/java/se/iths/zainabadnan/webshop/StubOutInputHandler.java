package se.iths.zainabadnan.webshop;

public class StubOutInputHandler implements OutInputHandler {

    @Override
    public String prompt(String message) {
        return "";
    }

    @Override
    public void info(String message) {
    }

    @Override
    public String menu() {
        return "4";
    }
}
