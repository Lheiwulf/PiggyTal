package mark.nobleza.uhack4;

import com.google.gson.annotations.SerializedName;

/**
 * Created by markjon on 12/11/2016.
 */
public class myDateValidation {

    @SerializedName("UbAccID")
    public int UbAccID;
    @SerializedName("diffDate")
    public int diffDate;

    public myDateValidation(int UbAccID,int diffDate){
        this.UbAccID = UbAccID;
        this.diffDate = diffDate;
    }

    public int getUbAccID() {
        return UbAccID;
    }

    public int getDiffDate() {
        return diffDate;
    }
}
