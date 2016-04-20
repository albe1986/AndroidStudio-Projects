package it.app.apolverari.parking;

/**
 * Created by a.polverari on 20/04/2016.
 */
public class Parking {

    private String date;
    private String address;
    private String pic;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Parking(String date, String address, String pic){
        this.date = date;
        this.address = address;
        this.pic = pic;
    }
}
