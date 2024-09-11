package com.pitercapistrano.calculadoradeimc;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pitercapistrano.calculadoradeimc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String peso = binding.editPeso.getText().toString();
                String altura = binding.editAltura.getText().toString();

                if (peso.isEmpty()){
                    binding.editPeso.setError("Informe o seu peso!");
                } else if (altura.isEmpty()) {
                    binding.editAltura.setError("Informe sua altura!");
                }else {
                    calcularImc();
                }
            }
        });

    }
    private void calcularImc(){

       Float peso = Float.parseFloat(binding.editPeso.getText().toString());
       Float altura = Float.parseFloat(binding.editAltura.getText().toString());
       Float imc = peso / (altura * altura);

       if (imc < 18.5 ){
           binding.txtResultado.setText("Seu IMC é: " + imc + "\nSeu peso está abaixo do ideal!");
           binding.txtResultado.setTextColor(getColor(R.color.yellow));
       } else if (imc <= 24.9) {
           binding.txtResultado.setText("Seu IMC é: " + imc + "\nSeu peso está nroma!");
           binding.txtResultado.setTextColor(getColor(R.color.green));
       } else if (imc <=29.9) {
           binding.txtResultado.setText("Seu IMC é: " + imc + "\nVocê está com sobrepeso!");
           binding.txtResultado.setTextColor(getColor(R.color.gold));
       } else if (imc <= 34.9) {
           binding.txtResultado.setText("Seu IMC é: " + imc + "\nVocê está com Obesidade (Grau I)");
           binding.txtResultado.setTextColor(getColor(R.color.orange));
       } else if (imc <= 39.9) {
           binding.txtResultado.setText("Seu IMC é: " + imc + "\nVocê está com Obesidade Severa (Grau II)");
           binding.txtResultado.setTextColor(getColor(R.color.salmao));
       }else {
           binding.txtResultado.setText("Seu IMC é: " + imc + "\nVocê está com Obesidade Mórbida (Grau III)");
           binding.txtResultado.setTextColor(getColor(R.color.red));
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemID = item.getItemId();

        if (itemID == R.id.ic_limpar){
            binding.editPeso.setText("");
            binding.editAltura.setText("");
            binding.txtResultado.setText("");
        }

        return super.onOptionsItemSelected(item);
    }
}