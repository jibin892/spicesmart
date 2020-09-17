package jibin.ck.hostelapp_user.Adapter;

public class Nearest_Adapter {

    private String productimg;

    public Nearest_Adapter(String productimg) {
        this.productimg = productimg;


    }
    public Nearest_Adapter(){

    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }


    @Override
    public String toString() {
        return "Populars{" +
                "productimg='" + productimg + '\'' +

                '}';
    }
}