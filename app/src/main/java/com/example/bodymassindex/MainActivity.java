package com.example.bodymassindex;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText weightInput;
    private EditText heightInput;
    private TextView bmiResult;
    private TextView categoryResult;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        // เชื่อม View จาก activity_main.xml
        weightInput = findViewById(R.id.weight_input);
        heightInput = findViewById(R.id.height_input);
        bmiResult = findViewById(R.id.bmi_result);
        categoryResult = findViewById(R.id.category_result);
        calculateButton = findViewById(R.id.btn_calculate);

        // เมื่อกดปุ่มคำนวณ
        calculateButton.setOnClickListener(v -> calculateBMI());
    }

    private void calculateBMI() {

        String weightText = weightInput.getText().toString().trim();
        String heightText = heightInput.getText().toString().trim();

        // ตรวจสอบว่ากรอกข้อมูลครบหรือไม่
        if (weightText.isEmpty() || heightText.isEmpty()) {
            bmiResult.setText("-");
            categoryResult.setText("กรุณากรอกข้อมูล");
            return;
        }

        try {
            double weight = Double.parseDouble(weightText);
            double heightCm = Double.parseDouble(heightText);

            // ตรวจสอบค่าที่ไม่ถูกต้อง
            if (weight <= 0 || heightCm <= 0) {
                bmiResult.setText("-");
                categoryResult.setText("กรุณากรอกค่าที่ถูกต้อง");
                return;
            }

            // แปลงส่วนสูงจาก cm เป็น m
            double heightM = heightCm / 100.0;

            // สูตร BMI = น้ำหนัก / ส่วนสูง²
            double bmi = weight / (heightM * heightM);
            categoryResult.setVisibility(TextView.VISIBLE);

            // แสดง BMI ทศนิยม 2 ตำแหน่ง
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            bmiResult.setText(decimalFormat.format(bmi));

            // แบ่งเกณฑ์ BMI
            if (bmi < 18.5) {

                categoryResult.setText("น้ำหนักน้อย");
                categoryResult.setBackgroundResource(R.drawable.bg_badge_blue);

            } else if (bmi < 25.0) {

                categoryResult.setText("ปกติ");
                categoryResult.setBackgroundResource(R.drawable.bg_badge_green);

            } else if (bmi < 30.0) {

                categoryResult.setText("น้ำหนักเกิน");
                categoryResult.setBackgroundResource(R.drawable.bg_badge_yellow);

            } else {

                categoryResult.setText("อ้วน");
                categoryResult.setBackgroundResource(R.drawable.bg_badge_red);
            }

        } catch (NumberFormatException e) {

            bmiResult.setText("-");
            categoryResult.setText("กรุณากรอกตัวเลข");
        }
    }
}