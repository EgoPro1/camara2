package com.appseducativos.camara;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {

    Button boton;
    ImageView imagen;
    Snackbar s1,s2;
    RelativeLayout LayoutPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new Handler().postDelayed()
        boton=findViewById(R.id.button);
        imagen=findViewById(R.id.imageView);
        LayoutPrincipal=findViewById(R.id.layoutprincipal);
        s1=Snackbar.make(LayoutPrincipal,"Los Permisos fueron otorgados correctamente.",Snackbar.LENGTH_LONG);
        s2=Snackbar.make(LayoutPrincipal,"Es necesario que otorgue los permisos de la camara.",Snackbar.LENGTH_INDEFINITE);

        s2.setAction("solicitar", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hacer_peticion();
            }
        });
        /*boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hacer_peticion();
            }
        });*/
        if(verificarPermisos())
        {
            iniciarCamara();
        }
        else
        {
            justificarPermisos();
        }
    }






    private boolean verificarPermisos() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;

        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this,CAMERA)== PackageManager.PERMISSION_GRANTED)//revisa si se ha otorgado un permiso  GRANTED(CONCEDIDO, DENIED(DENEGADO))
        {
            return true;

        }

       return false;
    }
    private void justificarPermisos() {

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,CAMERA))
        {
            //la justificacion de por que no debe rechazar el permiso
            s2.show();

        }
        else{
            hacer_peticion();


        }
    }

    private void iniciarCamara() {

        s1.show();
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),200);

    }


    public void hacer_peticion(){

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{CAMERA},100);

        //ActivityCompat.requestPermissions(MainActivity.this,new String[]{CAMERA,ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},100);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case 100:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                    s1.show();

                }
                else{

                    s2.show();

                }




        }
    }
}
