package com.clintonmedbery.calculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public TextView displayText;

    CalculatorEngine calc = new CalculatorEngine(12);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayText = (TextView) findViewById(R.id.textView);
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

    public void onSaveInstanceState(Bundle outState){
        //Bundle bundle = new Bundle();
        outState.putSerializable("calculator", calc);
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedBundle){
        super.onRestoreInstanceState(savedBundle);
        calc = (CalculatorEngine) savedBundle.getSerializable("calculator");
        displayText.setText(calc.getDisplay());
    }

    public void clearButton(View view) {
        calc.clear();
        displayText.setText(calc.getDisplay());
    }

    public void clearEverythingButton(View view) {
        calc.clearEntry();
        displayText.setText(calc.getDisplay());
    }

    public void posNegButton(View view){
        calc.toggleSign();
        displayText.setText(calc.getDisplay());
    }

    public void divideButton(View view){
        calc.perform(Operation.DIVIDE);
        displayText.setText(calc.getDisplay());
    }

    public void sevenButton(View view){
        calc.insert('7');
        displayText.setText(calc.getDisplay());
    }

    public void eightButton(View view){
        calc.insert('8');
        displayText.setText(calc.getDisplay());
    }

    public void nineButton(View view){
        calc.insert('9');
        displayText.setText(calc.getDisplay());
    }

    public void plusButton(View view){
        calc.perform(Operation.ADD);
        displayText.setText(calc.getDisplay());

    }

    public void fourButton(View view){
        calc.insert('4');
        displayText.setText(calc.getDisplay());
    }

    public void fiveButton(View view){
        calc.insert('5');
        displayText.setText(calc.getDisplay());
    }

    public void sixButton(View view){
        calc.insert('6');
        displayText.setText(calc.getDisplay());
    }

    public void minusButton(View view){
        calc.perform(Operation.SUBTRACT);
        displayText.setText(calc.getDisplay());
    }

    public void oneButton(View view){
        calc.insert('1');
        displayText.setText(calc.getDisplay());
    }

    public void twoButton(View view){
        calc.insert('2');
        displayText.setText(calc.getDisplay());
    }

    public void threeButton(View view){
        calc.insert('3');
        displayText.setText(calc.getDisplay());
    }

    public void multiplyButton(View view){
        calc.perform(Operation.MULTIPLY);
        displayText.setText(calc.getDisplay());
    }

    public void zeroButton(View view){
        calc.insert('0');
        displayText.setText(calc.getDisplay());
    }

    public void decimalButton(View view){
        calc.insert('.');
        displayText.setText(calc.getDisplay());
    }

    public void equalsButton(View view){
        calc.perform(Operation.EQUALS);
        displayText.setText(calc.getDisplay());
    }




}
