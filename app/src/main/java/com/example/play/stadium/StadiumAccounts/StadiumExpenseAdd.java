package com.example.play.stadium.StadiumAccounts;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.play.Config;
import com.example.play.R;
import com.example.play.SessionManager;
import com.example.play.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StadiumExpenseAdd extends AppCompatActivity {

    EditText expensetype, expenseamount, expensedescription;
    TextView  expensedate,expensebill;
    AppCompatButton add,date,bill;
    int cYear,cMonth,cDay,sYear,sMonth,sDay;
    String name,id,expenseType,expenseDate,expenseAmount,expenseBill,expenseDescription,status,message,
            url= Config.baseurl+"stadium_add_expense.php";;

    Uri uri;
    private RequestQueue rQueue;
    private static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_expense_add);

        expensetype = findViewById(R.id.sAddExpenseType);
        expensedate = findViewById(R.id.sAddExpenseDate);
        expenseamount = findViewById(R.id.sAddExpenseAmount);
        expensebill = findViewById(R.id.sAddExpensebill);
        expensedescription = findViewById(R.id.sAddExpenseDescription);
        add = findViewById(R.id.sExpenseAdd);
        date = findViewById(R.id.sAddExpenseDatebtn);
        bill = findViewById(R.id.sAddExpensebillbtn);

        Calendar calendar = Calendar.getInstance();
        cYear = calendar.get(Calendar.YEAR);
        cMonth = calendar.get(Calendar.MONTH);
        cDay = calendar.get(Calendar.DAY_OF_MONTH);

//        Current Date and Time
//        String dateFormat = new SimpleDateFormat("dd-MM-yyy"
//                , Locale.getDefault()).format(new Date());
//        String timeFormat = new SimpleDateFormat("hh:mm aa"
//                , Locale.getDefault()).format(new Date());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        //      Button click to select the image and show the name of the image
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense(name,uri);
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && requestCode == 1 && data != null){
            uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getApplication().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        name = cursor.getString(index);
                        expensebill.setText(name);
                        Log.d("nameeeee>>>>  ",name);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                name = myFile.getName();
                expensebill.setText(name);
                Log.d("nameeeee>>>>  ",name);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addExpense(final String pdfname, Uri pdffile) {

        id = new SessionManager(getApplicationContext()).getUserDetails().get("id");
        expenseType = expensetype.getText().toString();
        expenseDate = expensedate.getText().toString();
        expenseAmount = expenseamount.getText().toString();
        expenseBill = expensebill.getText().toString();
        expenseDescription = expensedescription.getText().toString();

        if (TextUtils.isEmpty(expenseType)){
            expensetype.setError("Required field");
            expensetype.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(expenseDate)){
            expensedate.setError("Required field");
            expensedate.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(expenseAmount)){
            expenseamount.setError("Required field");
            expenseamount.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(expenseBill)){
            expensebill.setError("Required field");
            expensebill.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(expenseDescription)){
            expensedescription.setError("Required field");
            expensedescription.requestFocus();
            return;
        }

        //        For executing the query and image upload using the VolleyMultipartRequest
        InputStream iStream = null;
        try {
            iStream = getApplication().getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            showSimpleProgressDialog(getApplication(), null, "Loading", false);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            removeSimpleProgressDialog();
                            Log.d("ressssssoo",new String(response.data));
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));

                                jsonObject.toString().replace("\\\\","");

                                status = jsonObject.getString("status");
                                message = jsonObject.getString("message");

                                if (status.equals("1")) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),SExpenseFragment.class));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            removeSimpleProgressDialog();
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //add string parameters
                    params.put("id", id);
                    params.put("expencetype", expenseType);
                    params.put("expensedate", expenseDate);
                    params.put("expenseamount", expenseAmount);
                    params.put("expensebill", expenseBill);
                    params.put("expensedescription", expenseDescription);
                    return params;
                }
                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("filename", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(getApplicationContext());
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            Log.e("Log", "inside catch IllegalArgumentException");
            ie.printStackTrace();
        } catch (RuntimeException re) {
            Log.e("Log", "inside catch RuntimeException");
            re.printStackTrace();
        } catch (Exception e) {
            Log.e("Log", "Inside catch Exception");
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                StadiumExpenseAdd.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                sYear= year;
                sMonth = month;
                sDay = dayOfMonth;
                String sDate = sDay + "/" + (sMonth+1) + "/" + sYear;
                expensedate.setText(sDate);
            }
        },cYear,cMonth,cDay
        );
        datePickerDialog.updateDate(sYear,sMonth,sDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}
