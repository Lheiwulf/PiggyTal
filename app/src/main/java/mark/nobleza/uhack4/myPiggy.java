package mark.nobleza.uhack4;

import com.google.gson.annotations.SerializedName;


/**
 * Created by markjon on 12/10/2016.
 */
public class myPiggy {

    @SerializedName("UbAccID")
    public int piggyID;
    @SerializedName("AccID")
    public int AccID;
    @SerializedName("UbAccAmount")
    public double piggyAmount;
    @SerializedName("UbDateToOpen")
    public String piggyDate;
    @SerializedName("description")
    public String piggyDesc;
    @SerializedName("isActive")
    public int isActive;

    public myPiggy(int piggyID, int AccID, double piggyAmount, String piggyDate, String piggyDesc, int isActive){
        this.piggyID = piggyID;
        this. AccID = AccID;
        this. piggyAmount = piggyAmount;
        this. piggyDate = piggyDate;
        this. piggyDesc = piggyDesc;
        this. isActive = isActive;
    }

    public int getPiggyID(){
        return piggyID;
    }

    public int getAccID() {
        return AccID;
    }

    public double getPiggyAmount(){
        return piggyAmount;
    }

    public String getPiggyDate(){
        return piggyDate;
    }

    public String getPiggyDesc(){
        return piggyDesc;
    }

    public int getIsActive(){
        return isActive;
    }
}
