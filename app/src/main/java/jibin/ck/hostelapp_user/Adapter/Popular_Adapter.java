package jibin.ck.hostelapp_user.Adapter;

public class Popular_Adapter {

    private String productimg;

    public Popular_Adapter(String productimg) {
        this.productimg = productimg;


    }
    public Popular_Adapter(){

    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }


    @Override
    public String toString() {
        return "Popular{" +
                "productimg='" + productimg + '\'' +

                '}';
    }
}