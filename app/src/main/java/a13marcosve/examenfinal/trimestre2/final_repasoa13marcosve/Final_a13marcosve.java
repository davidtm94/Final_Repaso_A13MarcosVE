package a13marcosve.examenfinal.trimestre2.final_repasoa13marcosve;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Final_a13marcosve extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_a13marcosve);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final_a13marcosve, menu);
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

    //CALL
    public void called(View view) {
        EditText number = (EditText) findViewById(R.id.phone);
        if (!number.getText().toString().equalsIgnoreCase("")) {
            Intent chamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)"+number.getText().toString()));
            startActivity(chamada);
        }else
            Toast.makeText(getBaseContext(),getString(R.string.toastPhone),Toast.LENGTH_SHORT).show();
    }
    //ACTIVITY VIDEO
    public void video(View view){
        Intent video = new Intent(this,Video.class);
        startActivity(video);
    }
    //SALARY
    public void salario(View view){
        Intent salary = new Intent(this,Salary.class);
        startActivity(salary);
    }
}
