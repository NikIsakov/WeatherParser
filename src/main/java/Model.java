import lombok.Data;

@Data
public class Model {

    private String name;
    private String dt;
    private Double temp;
    private int pressure;
    private String main;
    //private String city;

    @Override
    public String toString() {
        return  "город ='" + name + '\'' +
                ", дата ='" + dt + '\'' +
                ", температура =" + temp +
                ", давление =" + pressure;
    }
}
