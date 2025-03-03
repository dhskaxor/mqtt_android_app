package com.example.mqttapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.eclipse.paho.client.mqttv3.*;

public class MainActivity extends AppCompatActivity {
    private EditText urlInput, topicInput;
    private Button connectButton;
    private TextView messageView;
    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlInput = findViewById(R.id.urlInput);
        topicInput = findViewById(R.id.topicInput);
        connectButton = findViewById(R.id.connectButton);
        messageView = findViewById(R.id.messageView);

        connectButton.setOnClickListener(v -> {
            String brokerUrl = urlInput.getText().toString();
            String topic = topicInput.getText().toString();
            connectToMqtt(brokerUrl, topic);
        });
    }

    private void connectToMqtt(String brokerUrl, String topic) {
        try {
            mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            mqttClient.connect(options);

            mqttClient.subscribe(topic, (t, message) -> runOnUiThread(() -> {
                messageView.append("\n" + new String(message.getPayload()));
            }));

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
