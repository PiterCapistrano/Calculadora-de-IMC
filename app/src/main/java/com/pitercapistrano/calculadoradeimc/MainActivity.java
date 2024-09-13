package com.pitercapistrano.calculadoradeimc;

import android.os.Bundle;  // Importa a classe Bundle para salvar e restaurar o estado da atividade
import android.view.Menu;  // Importa a classe Menu para manipular o menu de opções
import android.view.MenuItem;  // Importa a classe MenuItem para manipular itens no menu
import android.view.View;  // Importa a classe View para interações com a interface de usuário

import androidx.activity.EdgeToEdge;  // Permite o uso otimizado da tela, aproveitando as bordas
import androidx.annotation.ColorInt;  // Anotação para trabalhar com cores inteiras
import androidx.annotation.NonNull;  // Anotação que define que o valor não pode ser nulo
import androidx.appcompat.app.AppCompatActivity;  // Classe base para atividades
import androidx.core.graphics.Insets;  // Gerenciamento de margens do sistema
import androidx.core.view.ViewCompat;  // Proporciona compatibilidade com versões antigas de Android
import androidx.core.view.WindowInsetsCompat;  // Manipula as margens das barras do sistema (status e navegação)

import com.pitercapistrano.calculadoradeimc.databinding.ActivityMainBinding;  // Vincula os componentes da interface

import java.text.DecimalFormat;  // Classe usada para formatar números decimais

public class MainActivity extends AppCompatActivity {

    // Vincula o layout da atividade principal com o código, permitindo acesso aos elementos da interface
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilita o uso completo da tela, incluindo áreas de barra de status e navegação
        EdgeToEdge.enable(this);

        // Infla o layout da atividade principal e vincula com o objeto `binding`
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Define o layout da atividade a partir da raiz do objeto `binding`
        setContentView(binding.getRoot());

        // Ajusta as margens da interface para considerar as barras do sistema (status, navegação)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Define a ação ao clicar no botão de calcular o IMC
        binding.btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Captura os valores dos campos de peso e altura como texto
                String peso = binding.editPeso.getText().toString();
                String altura = binding.editAltura.getText().toString();

                // Verifica se os campos estão vazios e exibe erro se necessário
                if (peso.isEmpty()) {
                    binding.editPeso.setError("Informe o seu peso!");  // Exibe erro caso o peso não seja informado
                } else if (altura.isEmpty()) {
                    binding.editAltura.setError("Informe sua altura!");  // Exibe erro caso a altura não seja informada
                } else {
                    // Se ambos os campos estiverem preenchidos, calcula o IMC
                    calcularImc();
                }
            }
        });
    }

    // Função para calcular o IMC baseado nos valores de peso e altura
    private void calcularImc() {
        // Converte o texto de peso e altura para números de ponto flutuante, trocando "," por "."
        Float peso = Float.parseFloat(binding.editPeso.getText().toString().replace(",", "."));
        Float altura = Float.parseFloat(binding.editAltura.getText().toString().replace(",", "."));

        // Calcula o IMC usando a fórmula: IMC = peso / (altura * altura)
        Float imc = peso / (altura * altura);

        // Formata o valor do IMC para exibir com duas casas decimais
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        // Verifica a faixa do IMC e define o texto e cor do resultado
        if (imc < 18.5) {
            binding.txtResultado.setText("Seu IMC é: " + decimalFormat.format(imc).replace(".", ",") + "\nSeu peso está abaixo do ideal!");
            binding.txtResultado.setTextColor(getColor(R.color.yellow));  // Exibe em amarelo
        } else if (imc <= 24.9) {
            binding.txtResultado.setText("Seu IMC é: " + decimalFormat.format(imc).replace(".", ",") + "\nSeu peso está normal!");
            binding.txtResultado.setTextColor(getColor(R.color.green));  // Exibe em verde
        } else if (imc <= 29.9) {
            binding.txtResultado.setText("Seu IMC é: " + decimalFormat.format(imc).replace(".", ",") + "\nVocê está com sobrepeso!");
            binding.txtResultado.setTextColor(getColor(R.color.gold));  // Exibe em dourado
        } else if (imc <= 34.9) {
            binding.txtResultado.setText("Seu IMC é: " + decimalFormat.format(imc).replace(".", ",") + "\nVocê está com Obesidade (Grau I)");
            binding.txtResultado.setTextColor(getColor(R.color.orange));  // Exibe em laranja
        } else if (imc <= 39.9) {
            binding.txtResultado.setText("Seu IMC é: " + decimalFormat.format(imc).replace(".", ",") + "\nVocê está com Obesidade Severa (Grau II)");
            binding.txtResultado.setTextColor(getColor(R.color.salmao));  // Exibe em salmão
        } else {
            binding.txtResultado.setText("Seu IMC é: " + decimalFormat.format(imc).replace(".", ",") + "\nVocê está com Obesidade Mórbida (Grau III)");
            binding.txtResultado.setTextColor(getColor(R.color.red));  // Exibe em vermelho
        }
    }

    // Cria o menu de opções na atividade
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla o menu a partir do arquivo XML (menu_principal)
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Define o comportamento ao selecionar um item do menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();  // Obtém o ID do item selecionado

        // Verifica se o item selecionado é o ícone de limpar
        if (itemID == R.id.ic_limpar) {
            // Limpa os campos de entrada de peso e altura e o texto de resultado
            binding.editPeso.setText("");
            binding.editAltura.setText("");
            binding.txtResultado.setText("");
        }

        return super.onOptionsItemSelected(item);
    }
}
