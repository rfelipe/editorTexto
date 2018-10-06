package com.example.azoi.editortexto;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    //*preferencia
    public void gravaPreferencia(String sChave, String sValor) {
        SharedPreferences sharedPreferences = getSharedPreferences("azoi", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sChave, sValor);
        editor.commit();
    }

    public String lePreferencia(String sChave, String sDefault) {
        String sValor = "";
        SharedPreferences prefs = getSharedPreferences("azoi", MODE_PRIVATE);
        sValor = prefs.getString(sChave, sDefault);
        return sValor;
    }

    private boolean gravarArquivoMemoriaInterna(String sNomeArquivo, String texto){
    try{
        File file = new File (getFilesDir().getPath() + "\\" + sNomeArquivo);
        FileWriter fileWriter= new FileWriter(file,true);
        fileWriter.append(texto);
        fileWriter.flush();
        return true;
    }catch(Exception e){
        e.printStackTrace();
        return false;
    }
    }

    //ler interno
    private String leArquivoMemoriaInterna(String sNomeArquivo) {
        String sLinha;
        String sConteudo = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir().getPath() + "\\" + sNomeArquivo));
            while ((sLinha = br.readLine()) != null) {
                sConteudo += sLinha;
                sConteudo += "\n";
            }
            br.close();
    }catch (FileNotFoundException e){
        Log.e("ftec","Arquivo não existe", e);
    }catch (Exception e){
        Log.e("ftec","erro ler arquivo na memoria interna", e);
    }
    return sConteudo;
}

    // salvar arquivo externo
    private boolean gravarArquivoExterna(String sNomeArquivo, String sTexto, boolean sobrescrever){
        try{
            File f= new File(getExternalFilesDir(null),sNomeArquivo);
            BufferedWriter out = new BufferedWriter(new FileWriter(f,sobrescrever));
            out.write(sTexto);
            out.flush();
            out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }
    //ler EXterna
    private String leArquivoMemoriaExterna(String sNomeArquivo){
        String sLinha;
        String sConteudo="";
        try{
            File f= new File(getExternalFilesDir(null),sNomeArquivo);
            BufferedReader br = new BufferedReader(new FileReader(f));
            while ((sLinha= br.readLine()) !=null){
                sConteudo += sLinha;
                sConteudo += "\n";
            }
        }catch (FileNotFoundException e){
            Log.e("ftec","arquivo não existe");
        }catch (Exception e){
            Log.e("Ftec", "erro ao ler arquivo na memoria externa", e);
        }
        return sConteudo;
    }


    EditText namefile,Cxtexto;
    Button  bsalvar,babrir,blimpar;
    RadioButton memoria,celular;
    String snamefile,sCxtexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namefile = (EditText) findViewById(R.id.namefile);
        Cxtexto = (EditText) findViewById(R.id.Cxtexto);
        bsalvar = (Button) findViewById(R.id.bsalvar);
        blimpar = (Button) findViewById(R.id.blimpar);
        babrir = (Button) findViewById(R.id.babrir);
        memoria = (RadioButton) findViewById(R.id.memoria);
        celular = (RadioButton) findViewById(R.id.celular);

        snamefile = namefile.getText().toString();
        sCxtexto = Cxtexto.getText().toString();
       // lePreferencia(snamefile,snamefile);
       // gravaPreferencia(snamefile, snamefile);

        bsalvar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (celular.isChecked()) {
                            //salvar interno;
                            gravarArquivoMemoriaInterna(snamefile, sCxtexto);
                        }
                        if (memoria.isChecked()) {
                            gravarArquivoExterna(snamefile, sCxtexto,memoria.isChecked());
                        }
                    }
                });
        babrir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (celular.isChecked()) {
                    //ler celular
                        leArquivoMemoriaInterna(snamefile);
                    sCxtexto=leArquivoMemoriaInterna(snamefile);
                    Cxtexto.setText(sCxtexto);
                    if (memoria.isChecked()) {
                        leArquivoMemoriaExterna(snamefile);
                        sCxtexto=leArquivoMemoriaExterna(snamefile);
                        Cxtexto.setText(sCxtexto);
                    }
                }
            }
        });
        blimpar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                snamefile="";
                sCxtexto="";
                namefile.setText(snamefile);
                Cxtexto.setText(sCxtexto);
            }
        });
    }
}
