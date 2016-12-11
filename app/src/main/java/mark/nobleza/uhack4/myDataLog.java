package mark.nobleza.uhack4;

import com.google.gson.annotations.SerializedName;

/**
 * Created by markjon on 12/11/2016.
 */
public class myDataLog {

    @SerializedName("TransactionID")
    public int transID;
    @SerializedName("UbAccID")
    public int accID;
    @SerializedName("UbAccAmount")
    public double accAmount;
    @SerializedName("DateSaved")
    public String Date;

    public myDataLog(int transID, int accID, double accAmount, String Date){
        this.transID = transID;
        this.accID = accID;
        this.accAmount = accAmount;
        this.Date = Date;
    }

    public int getAccID() {
        return accID;
    }

    public double getAccAmount() {
        return accAmount;
    }

    public int getTransID() {
        return transID;
    }

    public String getDate() {
        return Date;
    }
}
