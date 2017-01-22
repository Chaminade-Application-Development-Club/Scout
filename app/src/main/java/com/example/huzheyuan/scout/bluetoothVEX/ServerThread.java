package com.example.huzheyuan.scout.bluetoothVEX;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class ServerThread extends Thread{
    private BluetoothServerSocket bluetoothServerSocket;
    private BluetoothAdapter bluetoothAdapter;
    private static final String NAME = "bTServer";
    private static final UUID MY_UUID = UUID.fromString("0x1106");
    public ServerThread(){
        BluetoothServerSocket tmp = null;
        try{
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME,MY_UUID);
        }catch (IOException e){

        }
        bluetoothServerSocket = tmp;
    }

    public void run(){
        BluetoothSocket socket = null;
        // 在后台一直监听客户端的请求
        while(true){
            try{
                socket = bluetoothServerSocket.accept();
            }catch (IOException e){
                break;
            }
            if(socket != null){
                try{
                    bluetoothServerSocket.close();
                    break;
                }catch (IOException e){

                }
            }
        }
    }

    public void cancel(){
        try{
            bluetoothServerSocket.close();
        }catch (IOException e){

        }
    }
}
