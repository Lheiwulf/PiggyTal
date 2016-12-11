package mark.nobleza.uhack4;

import com.google.gson.annotations.SerializedName;

/**
 * Created by markjon on 12/10/2016.
 */
public class myUser {

    @SerializedName("AccID")
    public int AccID;
    @SerializedName("AccUserName")
    public String AccUser;
    @SerializedName("AccPassword")
    public String AccPass;
    @SerializedName("UbAccAmount")
    public double UbAccAmount;
    @SerializedName("AccAtmCardNum")
    public int AccNum;
    @SerializedName("UbAccID")
    public int UbAccID;


    public myUser(int AccID, String AccUser, String AccPassword, double UbAccAmount,  int AccNum, int UbAccID){
        this.AccID = AccID;
        this.AccUser = AccUser;
        this.AccPass = AccPassword;
        this.UbAccAmount = UbAccAmount;
        this.AccNum = AccNum;
        this.UbAccID = UbAccID;
    }

    public int getAccID() {
        return AccID;
    }

    public int getAccNum() {
        return AccNum;
    }

    public String getAccPass() {
        return AccPass;
    }

    public double getUbAccAmount() {
        return UbAccAmount;
    }

    public String getAccUser() {
        return AccUser;
    }

    public int getUbAccID() {
        return UbAccID;
    }
}
