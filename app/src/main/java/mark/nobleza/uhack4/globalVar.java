package mark.nobleza.uhack4;

import android.app.Application;

/**
 * Created by markjon on 12/10/2016.
 */
public class globalVar extends Application {

    public static int gAccID;
    public static int gUBAccID;
    public static double gTotalCash;
    public static int gUbAccIDMain;
    public static int gAtmNum;
    private static globalVar singleton;


    public static globalVar getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
