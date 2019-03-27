package com.example.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView _Tela;
    private String display = "";
    private String operadorAtual = "";
    private String resultado = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _Tela = findViewById(R.id.tvResult);
        _Tela.setText(display);
    }

    private void atualizarTela(){
        _Tela.setText(display);
    }

    private void limpar(){
        display = "";
        operadorAtual = "";
        resultado = "";
    }

    private boolean isOperador(char op){
        switch (op){
            case '+':
            case '-':
            case 'x':
            case '÷':return true;
            default: return false;
        }
    }

    private double operador(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "x": return Double.valueOf(a) * Double.valueOf(b);
            case "/": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            case "%": try{
                return (Double.valueOf(b) / 100) * Double.valueOf(a);
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            default: return -1;
        }
    }

    private boolean getresultado(){
        if(operadorAtual == "") return false;
        String[] operadores = display.split(Pattern.quote(operadorAtual));
        if(operadores.length < 2) return false;
        resultado = String.valueOf(operador(operadores[0], operadores[1], operadorAtual));
        return true;
    }

    public void onClickNumero(View v){
        if(resultado != ""){
            limpar();
            atualizarTela();
        }
        Button b = (Button) v;
        display += b.getText();
        atualizarTela();
    }

    public void onClickOperador(View v){
        if(display == "") return;

        Button b = (Button)v;

        if(resultado != ""){
            String _display = resultado;
            limpar();
            display = _display;
        }

        if(operadorAtual != ""){
            Log.d("CalcX","" + display.charAt(display.length()-1));
            if(isOperador(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                atualizarTela();
                return;
            }else{
                getresultado();
                display = resultado;
                resultado = "";
            }
            operadorAtual = b.getText().toString();
        }
        display += b.getText();
        operadorAtual = b.getText().toString();
        atualizarTela();
    }

    public void onClickIgual(View v){
        getresultado();
        display = resultado;
        atualizarTela();
    }

    public void onClickLimpar(View v){
        limpar();
        atualizarTela();
    }

    public void onClickBackSpace(View v){
        display = display.substring(0, display.length()-1);
        atualizarTela();
    }

    public void onClickMaisMenos(View v){
        String b = "+";
        String c = "-";

        if(isOperador(display.charAt(display.length()-1)) && operadorAtual == c){
            display = display.replace(display.charAt(display.length()-1), b.charAt(0));
            atualizarTela();
            return;
        }else{
            display = display.replace(display.charAt(display.length()-1), c.charAt(0));
            atualizarTela();
        }
    }
}
