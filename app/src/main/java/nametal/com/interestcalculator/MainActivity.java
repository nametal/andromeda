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

        double amount = Double.parseDouble(amountStr);
        double installment = Double.parseDouble(installmentStr);
        double tenor = Double.parseDouble(tenorStr);

        double interest = ((installment * tenor / amount) - 1) * 100;
        interest = round(interest, 2);
        interestText.setText(String.valueOf(interest) + " %");
    }

    /**
     * Rounding a double value with a number of places behind decimal point
     * @param value value to round
     * @param places number of places
     * @return rounded value
     */
    private double round(double value, int places) {
        double factor = Math.pow(10, places);
        value *= factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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
