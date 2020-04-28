package scraping;

public class Cloth {
    private int ordinalNo;
    private int articleNo;
    private String name;
    private String size;
    private String dateOfAssign;
    private long barCode;
    private String releaseDate;
    private String washingDate;

    public Cloth(){}

    public Cloth(
            int ordinalNo, int articleNo, String name, String size, String dateOfAssign,
            long barCode, String releaseDate, String washingDate) {
        this.ordinalNo = ordinalNo;
        this.articleNo = articleNo;
        this.name = name;
        this.size = size;
        this.dateOfAssign = dateOfAssign;
        this.barCode = barCode;
        this.releaseDate = releaseDate;
        this.washingDate = washingDate;
    }

    public int getArticleNo() {
        return articleNo;
    }

    public void setArticleNo(int articleNo) {
        this.articleNo = articleNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDateOfAssign() {
        return dateOfAssign;
    }

    public void setDateOfAssign(String dateOfAssign) {
        this.dateOfAssign = dateOfAssign;
    }

    public long getBarCode() {
        return barCode;
    }

    @Override
    public String toString() {
        return "Cloth{" +
                "ordinalNo=" + ordinalNo +
                ", articleNo=" + articleNo +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", dateOfAssign='" + dateOfAssign + '\'' +
                ", barCode=" + barCode +
                ", releaseDate='" + releaseDate + '\'' +
                ", washingDate='" + washingDate + '\'' +
                '}';
    }

    public void setBarCode(long barCode) {
        this.barCode = barCode;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getWashingDate() {
        return washingDate;
    }

    public void setWashingDate(String washingDate) {
        this.washingDate = washingDate;
    }

    public int getOrdinalNo() {
        return ordinalNo;
    }

    public void setOrdinalNo(int ordinalNo) {
        this.ordinalNo = ordinalNo;
    }
}
