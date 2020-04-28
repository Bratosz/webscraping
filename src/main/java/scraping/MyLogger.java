
package scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.jsoup.Connection.*;

public class MyLogger {
    private final String startURL = "http://klsonline24.pl";
    private final String loginURL = startURL + "/default.aspx";
    private final String baseURL = startURL + "/baza.aspx";
    private String referrer = "https://google.com/";
    private final String userAgentChrome = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.113 Safari/537.36";
    private Map<String, String> cookies;
    private Map<String, String> formParameters;
    private Response mainPage;
    private Document actualPage;
    List<Cloth> clothes;

    public MyLogger(String login, String password) throws IOException {
        Response response = grabLoginPage(startURL, referrer);
        Document loginPage = response.parse();
        getFormParameters(loginPage);
        cookies = response.cookies();
        referrer = startURL;
        mainPage = logIn(login, password);
        clothes = new LinkedList<Cloth>();
    }

    private Response logIn(String login, String password) {
        putLoginDataToForm(login, password);
        try {
            Response resp = Jsoup.connect(loginURL)
                    .userAgent(userAgentChrome)
                    .referrer(referrer)
                    .timeout(10 * 1000)
                    .cookies(cookies)
                    .data(formParameters)
                    .followRedirects(true)
                    .execute();
            cookies.putAll(resp.cookies());
            referrer = baseURL;
            return resp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void findByLockerAndBox(Integer lockerNo, Integer boxNo) throws IOException {
        getFormParameters(mainPage.parse());
        putLockerNoAndBoxNoToForm(lockerNo.toString(), boxNo.toString());
        Document doc = standardPost().parse();
        getFormParameters(doc);
        mainPage = clickViewButton();
        actualPage = mainPage.parse();
    }

    private Response clickViewButton() {
        updateFormWithClick();
        try {
            return standardPost();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateFormWithClick() {
        formParameters.put("__EVENTTARGET", "ctl00$MainContent$GridView1");
        formParameters.put("__EVENTARGUMENT", "Select$0");
    }

    private Response standardPost() throws IOException {
        return Jsoup.connect(baseURL)
                .method(Method.POST)
                .userAgent(userAgentChrome)
                .referrer(referrer)
                .timeout(10 * 1000)
                .cookies(cookies)
                .data(formParameters)
                .followRedirects(true)
                .execute();
    }

    public Response getMainPage(){
        return  mainPage;
    }

    private void putLoginDataToForm(String login, String password) {
        formParameters.put("txtName", login);
        formParameters.put("txtPW", password);
    }

    private void putLockerNoAndBoxNoToForm(String lockerNo, String boxNo) {
        formParameters.put("ctl00$MainContent$szafa", lockerNo);
        formParameters.put("ctl00$MainContent$box", boxNo);
        formParameters.put("__EVENTTARGET", "ctl00$MainContent$szafa");
    }

    private Response grabLoginPage(String startURL, String referrer) {
        try {
            return Jsoup.connect(startURL)
                    .userAgent(userAgentChrome)
                    .referrer(referrer)
                    .timeout(10 * 1000)
                    .followRedirects(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getFormParameters(Document loginPage) {
        formParameters = new HashMap<String, String>();
        Elements elements = loginPage.select("input");
        for(Element e : elements) {
            String id = e.attr("name");
            String value = e.val();
            formParameters.put(id, value);
        }
        formParameters.remove("");
    }

    public Document login(String login, String password) {
        return null;
    }

    public Map<String, String> getFormParameters() {
        return formParameters;
    }

    public String getEmployeeLastName() {
        return actualPage.select("#ctl00_MainContent_GridView1 > tbody > tr:nth-child(2) > td:nth-child(3)").text();
    }

    public List<Cloth> getClothes() {
            Elements elements = actualPage.select("#ctl00_MainContent_GridView2 > tbody > tr");
            for(int i = 1; i < elements.size(); i++) {
                Elements td = elements.get(i).select("td");
                Cloth cloth = new Cloth();
                cloth.setOrdinalNo(Integer.valueOf(td.get(0).text()));
                cloth.setArticleNo(Integer.valueOf(td.get(1).text()));
                cloth.setName(td.get(2).text());
                cloth.setSize(td.get(3).text());
                cloth.setDateOfAssign(td.get(4).text());
                cloth.setBarCode(Long.parseLong(td.get(5).text()));
                cloth.setReleaseDate(td.get(6).text());
                cloth.setWashingDate(td.get(7).text());
                clothes.add(cloth);
            }
            return clothes;
    }

    public boolean isEmployeeHaveCloth(long l) {
        for (Cloth c : clothes) {
            if(c.getBarCode() == l) {
                return true;
            }
        }
        return false;
    }
}
