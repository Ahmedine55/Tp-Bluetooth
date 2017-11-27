package com.example.ahmed.tp_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bltAdapter;
    public Button activerblt;
    public Button desactiverblt;
    public Button activervisib;
    public Button desactivervisib;
    public Button boutonListsVisib;
    public Button boutonListsAsso;
    public ListView listes;
    private ArrayList list;
    public String toastText="";
    private BroadcastReceiver receiver;

    //create a BroadcastReceiver to receive states changes
    BroadcastReceiver bluetoothState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String stateExtra = BluetoothAdapter.EXTRA_STATE;
            int state = intent.getIntExtra(stateExtra,-1);
            switch(state){
                case(BluetoothAdapter.STATE_TURNING_ON):
                {
                    toastText="Bluetooth turning on";
                    Toast.makeText(MainActivity.this,toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
                case(BluetoothAdapter.STATE_ON):
                {
                    toastText="Bluetooth on";
                    Toast.makeText(MainActivity.this,toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
                case(BluetoothAdapter.STATE_TURNING_OFF):
                {
                    toastText="Bluetooth turning off";
                    Toast.makeText(MainActivity.this,toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
                case(BluetoothAdapter.STATE_OFF):
                {
                    toastText="Bluetooth off";
                    Toast.makeText(MainActivity.this,toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } //fin de onReceive
    }; //fin de bluetoothState



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialisation des boutons
        activerblt = (Button) findViewById(R.id.activerblt);
        desactiverblt =(Button)findViewById(R.id.desactiverblt);
        activervisib =(Button)findViewById(R.id.activerVisiblité);
        desactivervisib =(Button)findViewById(R.id.desactiverVisiblité);
        boutonListsVisib =(Button)findViewById(R.id.listVisible);
        boutonListsAsso =(Button)findViewById(R.id.listAssocié);
        listes = (ListView)findViewById(R.id.listView);
        list = new ArrayList();
        //Initialisation de BluetoothAdapter
      /*  bltAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bltAdapter == null) {
            Toast.makeText(this, "Bluetooth n'est pas disponible !", Toast.LENGTH_SHORT).show();
            finish(); // fermeture automatiquue de l'application si le service bluetooth n'es pas dispo !
        }*/

        //Activation de Bluetooth
        activerblt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bltAdapter.isEnabled()) {
                   /* String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
                    String actionRequestEnable = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                    IntentFilter filter = new IntentFilter(actionStateChanged);
                    registerReceiver(bluetoothState, filter);
                    startActivityForResult(new Intent(actionRequestEnable), 0);
                    Toast.makeText(MainActivity.this, "Bluetooth on" ,Toast.LENGTH_LONG).show();*/

                } // fin de if
            } // fin de onClick
        }); // fin de activerblt.setOnClickListener

        //Desactivation de bluetooth
        desactiverblt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   bltAdapter.disable();
                Toast.makeText(MainActivity.this, "Bluetooth off" ,Toast.LENGTH_LONG).show();*/
            } // fin de onClick
        }); // fin de desactiverblt.setOnClickListener

        //Activation de Visiblité de l'Appareil
        activervisib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Register for discovery events
               /* String scanModeChanged = BluetoothAdapter.ACTION_SCAN_MODE_CHANGED;
                String beDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;

                IntentFilter filter = new IntentFilter(scanModeChanged);
                registerReceiver(bluetoothState, filter);
                startActivityForResult(new Intent(beDiscoverable),1);*/
            } // fin de onClick
        }); // fin de activervisib.setOnClickListener

        // desactivation de visiblité
        desactivervisib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent discoverableIntent = new
                        Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,1);
                startActivity(discoverableIntent);
                Toast.makeText(MainActivity.this, "Visiblité off" ,Toast.LENGTH_LONG).show();*/
            }
        });

        //Affichage de la liste des appareils visibles
        boutonListsVisib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initier le processus de découverte
              /*  bltAdapter.startDiscovery();
                list.clear();
                // Inscrire le BroadcastReceiver
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(receiver, filter);*/
                // On crée un BroadcastReceiver pour ACTION_FOUND
                receiver = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        Toast.makeText(MainActivity.this,"onReceive",Toast.LENGTH_SHORT).show();
                        String action = intent.getAction();
                        // Quand la recherche trouve un terminal
                        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                            Toast.makeText(MainActivity.this,"Affichage des appareils Visibles ",Toast.LENGTH_SHORT).show();
                            // On récupère l'object BluetoothDevice depuis l'Intent
                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            // On ajoute le nom et l'adresse du périphérique dans une liste
                            list.add(device.getName() + "\n" + device.getAddress());
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Aucun appareil visible",Toast.LENGTH_SHORT).show();
                        }
                        // On ajoute la liste dans une ArrayAdapter
                        final ArrayAdapter adapter = new  ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, list);
                        listes.setAdapter(adapter);
                    } //fin onReceive
                }; //fin de discoveryResult
            } // // fin de onClick
        }); // boutonListsVisib.setOnClickListener

        //Affichage de la liste des appareils deja associé
        boutonListsAsso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "getBondedDevices()" renvoie une liste de périphérique connu .
              /*  Set<BluetoothDevice> devices = bltAdapter.getBondedDevices();
                ArrayList list = new ArrayList();
                for(BluetoothDevice blt : devices){
                    list.add(blt.getName());
                } // fin for
                Toast.makeText(MainActivity.this, "Affichage des appareils associés" ,Toast.LENGTH_SHORT).show();
                //// On ajoute le nom du périphérique dans une ArrayAdapter
                final ArrayAdapter adapter = new  ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, list);
                listes.setAdapter(adapter);*/
            } // // fin de onClick
        }); // boutonListsAsso.setOnClickListener



    } //fin onCreate
}
