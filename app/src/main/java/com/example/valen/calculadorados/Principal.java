package com.example.valen.calculadorados;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends AppCompatActivity {
    TextView pantallaGrande;
    TextView pantallaChica;
    double op1,op2;
    String operacion;
    boolean realizada,cargada;
    double memoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        pantallaGrande = (TextView)findViewById(R.id.datosGrande);
        pantallaChica = (TextView)findViewById(R.id.datosChico);
        op1=op2=0;
        operacion=null;
        realizada=false;
        cargada=false;
        memoria=-1;

    }

    protected void pulsarNumero(View view){
        if(realizada) {
            pantallaChica.setText("");
            pantallaGrande.setText("");
            realizada=false;
        }
        pantallaGrande.append(((Button)view).getText());
        pantallaChica.append(((Button)view).getText());
    }

    protected void pulsarOp(View view){
        if(operacion==null && !pantallaGrande.getText().toString().isEmpty()) {
            op1=Double.parseDouble(pantallaGrande.getText().toString());
            operacion = ((Button) view).getText().toString();
            pantallaGrande.setText("");
            pantallaChica.setText(op1+" "+operacion+" ");
            realizada=false;
            cargada=false;
        }else{
            operacion = ((Button) view).getText().toString();
            pantallaChica.setText(op1+" "+operacion+" "+pantallaGrande.getText().toString());
        }
    }

    protected void pulsarIgual(View view){
        if(operacion!=null) {
            String s = pantallaGrande.getText().toString();
            if(!s.isEmpty())op2 = Double.parseDouble(s);

            if (operacion.equals("+")) {
                pantallaGrande.setText((op1 + op2) + "");
                op1 = op1 + op2;
            } else if (operacion.equals("-")) {
                pantallaGrande.setText((op1 - op2) + "");
                op1 = op1 - op2;
            } else if (operacion.equals("/")) {
                if (op2 != 0) {
                    pantallaGrande.setText((op1 / op2) + "");
                    op1 = op1 / op2;
                } else {
                    pantallaGrande.setText("0");
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } else if (operacion.equals("x")) {
                pantallaGrande.setText((op1 * op2) + "");
                op1 = op1 * op2;
            }
            realizada = true;
            operacion = null;
        }

    }

    protected void pulsarBorrado(View view){
        operacion=null;
        pantallaChica.setText("");
        pantallaGrande.setText("");
        op1=op2=0;
        realizada=false;
    }

    protected void pulsarBorrarUltimo(View view){
        String s = pantallaGrande.getText().toString();
        if(s.length()>0 && !realizada) {
            pantallaGrande.setText(s.substring(0, s.length()-1));
            pantallaChica.setText(pantallaChica.getText().toString().substring(0,pantallaChica.getText().length()-1));
        }else if(s.length()>0){
            pantallaGrande.setText(s.substring(0, s.length()-1));
        }

    }

    protected void pulsarComa(View view){
        if(!pantallaGrande.getText().toString().isEmpty() && !pantallaGrande.getText().toString().contains(".")){
            pantallaGrande.append(".");
            pantallaChica.append(".");
        }
    }

    protected void pulsarGuardarMemoria(View view){
        if(!pantallaGrande.getText().toString().isEmpty()) {
            memoria = Double.parseDouble(pantallaGrande.getText().toString());
            Toast.makeText(getApplicationContext(), memoria + " guardado", Toast.LENGTH_LONG).show();
            cargada=false;
        }
    }

    protected void pulsarLimpiarMemoria(View view){
        memoria=-1;
        Toast.makeText(getApplicationContext(), "Memoria borrada", Toast.LENGTH_LONG).show();
        cargada=false;
    }

    protected void pulsarLlamarMemoria(View view){
        if(memoria>=0){
            if(realizada) {
                pantallaChica.setText("");
                pantallaGrande.setText("");
                realizada=false;
            }
            if(!cargada) {
                pantallaGrande.append(memoria + "");
                pantallaChica.append(memoria + "");
                cargada=true;
            }
        }
    }
}
