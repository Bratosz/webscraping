
import scraping.Cloth;
import scraping.MyLogger;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        String login = "";
        String password = "";
        int lockerNo = 10;
        int boxNo = 10;
        MyLogger log = new MyLogger(login, password);
        log.findByLockerAndBox(lockerNo, boxNo);
        List<Cloth> clothes = log.getClothes();
        boolean isOwned = log.isEmployeeHaveCloth(4818131242526l);

        String name = log.getEmployeeLastName();
        System.out.println(name);
        for(Cloth c : clothes) {
            System.out.println(c);
        }
        System.out.println(isOwned);


    }
}
