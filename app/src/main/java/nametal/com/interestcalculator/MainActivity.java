package nametal.com.interestcalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.container) == null) {
            return;
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /**
     * Calculate annual interest based on amount, monthly installment, and tenor months
     */
    public void calculate(View view) {
        EditText amountTxt = (EditText) findViewById(R.id.edtAmount);
        EditText installmentTxt = (EditText) findViewById(R.id.edtInstallment);
        EditText tenorTxt = (EditText) findViewById(R.id.edtTenor);
        EditText interestText = (EditText) findViewById(R.id.edtInterest);
        interestText.setKeyListener(null); // not editable

        String amountStr = amountTxt.getText().toString();
        String installmentStr = installmentTxt.getText().toString();
        String tenorStr = tenorTxt.getText().toString();

        List<EditText> errorList = new ArrayList<>();
        if (amountStr.isEmpty()) { errorList.add(amountTxt); }
        if (installmentStr.isEmpty()) { errorList.add(installmentTxt); }
        if (tenorStr.isEmpty()) { errorList.add(tenorTxt); }

        if (!errorList.isEmpty()) {
            for (int i = 0; i < errorList.size(); i++) {
                EditText editText =  errorList.get(i);
                editText.setError("This is required");
            }
            return;
        }

        BigDecimal amount = new BigDecimal(amountStr);
        BigDecimal installment = new BigDecimal(installmentStr);
        BigDecimal tenor = new BigDecimal(tenorStr);

        BigDecimal hundred = new BigDecimal(100);

        // interest = ((installment * tenor / amount) - 1) * 100;
        BigDecimal interest = installment.multiply(tenor).divide(amount).subtract(BigDecimal.ONE).multiply(hundred);

        // round to 2 decimal places
        interest = interest.setScale(2, RoundingMode.HALF_UP);

        interestText.setText(String.valueOf(interest) + " %");
    }

    public void showCarDb(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new CarFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }
}
