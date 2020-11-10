package jibin.ck.hostelapp_user.Adapter;

public class Music_Adapter {

    private String img,music,name,singer;

    public Music_Adapter(String img, String music,String name,String singer) {
        this.img = img;
        this.music = music;
        this.name = name;
        this.singer = singer;


    }
    public Music_Adapter(){

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
    @Override
    public String toString() {
        return "music{" +
                "img='" + img + '\'' +
                "music='" + music + '\'' +
                "name='" + name + '\'' +
                "singer='" + singer + '\'' +

                '}';
    }
}