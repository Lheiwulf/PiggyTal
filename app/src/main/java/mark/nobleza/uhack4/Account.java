package mark.nobleza.uhack4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Account extends AppCompatActivity {

    TextView tvBalance, tvAccountNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        String bal = String.valueOf(globalVar.gTotalCash);

        tvBalance = (TextView)findViewById(R.id.BalanceAmount);
        tvBalance.setText("P " + bal);

        tvAccountNum = (TextView)findViewById(R.id.AccountNum);
        tvAccountNum.setText("Account Number: " + globalVar.gAtmNum);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.onBackPressed();
    }
}
