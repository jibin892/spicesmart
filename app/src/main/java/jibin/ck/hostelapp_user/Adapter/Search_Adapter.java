package jibin.ck.hostelapp_user.Adapter;

public class Search_Adapter {

    private String productimg,producname;

    public Search_Adapter(String productimg,String producname) {
        this.productimg = productimg;
        this.producname = producname;


    }
    public Search_Adapter(){

    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }

    public String getProducname() {
        return producname;
    }

    public void setProducname(String producname) {
        this.producname = producname;
    }

    @Override
    public String toString() {
        return "Populars{" +
                "productimg='" + productimg + '\'' +
                "producname='" + producname + '\'' +

                '}';
    }
}