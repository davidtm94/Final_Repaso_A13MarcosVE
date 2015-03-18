package a13marcosve.examenfinal.trimestre2.final_repasoa13marcosve;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Salary extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_salary, menu);
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

    //CLASE SALARIO
    class Salario {
        public double amount;
        public String month;

        public Salario() {
            this.amount = 0;
        }

        public Salario(int amount, String month) {
            this.amount = amount;
            this.month = month;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return month + " " + amount;
        }
    }

    File rutaArquivo;

    private void descargarArquivo(String direccion) {
        URL url = null;
        try {
            url = new URL(direccion);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }

        HttpURLConnection conn = null;
        String nomeArquivo = Uri.parse(direccion).getLastPathSegment();
        rutaArquivo = new File (getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)+File.separator+nomeArquivo);
        try {

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000); /* milliseconds */
            conn.setConnectTimeout(15000); /* milliseconds */
            conn.setRequestMethod("POST");
            conn.setDoInput(true);

            conn.connect();

            int response = conn.getResponseCode();
            if (response != HttpURLConnection.HTTP_OK) {
                return;
            }
            OutputStream os = new FileOutputStream(rutaArquivo);
            InputStream in = conn.getInputStream();
            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data)) != -1) {
                os.write(data, 0, count);
            }
            os.flush();
            os.close();
            in.close();
            conn.disconnect();
            Log.i("COMUNICACION", "ACABO");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e("COMUNICACION", e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("COMUNICACION", e.getMessage());
        }

    }

    public void descarga(View v) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                descargarArquivo("https://manuais.iessanclemente.net/images/5/53/Salaries.xml");
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
