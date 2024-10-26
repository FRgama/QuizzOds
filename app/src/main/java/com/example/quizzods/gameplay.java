package com.example.quizzods;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Arrays;

public class gameplay extends AppCompatActivity {
    private RadioGroup divQuestoes;
    private RadioButton questao1, questao2, questao3, questao4;
    private Button enviarResposta;
    private int idRespostaCorreta = R.id.questao1;
    private TextView questaoNumero;
    private TextView vidasRestantes;



    private List<Questao> questoes = Arrays.asList(
            new Questao("O ODS 6 está relacionado a qual temática?",
                    Arrays.asList("Água potável e saneamento",
                            "Igualdade de gênero",
                            "Energia limpa e acessível",
                            "Combate à fome e desnutrição"),
                    1)
    );

    private int numeroQuestao = 1;
    private int vidas = 3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gameplay);

        divQuestoes = findViewById(R.id.divQuestoes);
        questao1 = findViewById(R.id.questao1);
        questao2 = findViewById(R.id.questao2);
        questao3 = findViewById(R.id.questao3);
        questao4 = findViewById(R.id.questao4);
        vidasRestantes = findViewById(R.id.vidasRestantes);

        questaoNumero = findViewById(R.id.questaoNumero);
        enviarResposta = findViewById(R.id.btnEnviarResposta);




        enviarResposta.setOnClickListener(v -> {
            retornarCores();
            int idRespostaSelecionada = divQuestoes.getCheckedRadioButtonId(); //caso a resposta esteja correta
            if (idRespostaSelecionada == idRespostaCorreta) {
                Toast.makeText(this, "Resposta Correta!",Toast.LENGTH_SHORT).show();
                findViewById(idRespostaSelecionada).setBackgroundColor(Color.GREEN);
                numeroQuestao  = numeroQuestao+1;
                questaoNumero.setText("Questão " + numeroQuestao);
                enviarResposta.setEnabled(false);



            }
            else {
                Toast.makeText(this, "Resposta Incorreta!",Toast.LENGTH_SHORT).show();
                findViewById(idRespostaSelecionada).setBackgroundColor(Color.RED);
                vidas -=1;
                vidasRestantes.setText("Vidas Restantes: " + vidas);
            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void retornarCores(){
        questao1.setBackgroundColor(Color.WHITE);
        questao2.setBackgroundColor(Color.WHITE);
        questao3.setBackgroundColor(Color.WHITE);
        questao4.setBackgroundColor(Color.WHITE);
    }
}