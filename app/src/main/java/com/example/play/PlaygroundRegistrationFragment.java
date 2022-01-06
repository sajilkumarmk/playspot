package com.example.play;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.play.databinding.FragmentPlaygroundRegistrationBinding;
import com.example.play.databinding.FragmentUserRegistrationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaygroundRegistrationFragment extends Fragment {

    private FragmentPlaygroundRegistrationBinding binding;
    EditText playname,playplace,playstate,playphone,playemail,playpassword;
    TextView playimagename;
    Spinner playdistrict,playtype;
    AppCompatButton playregistration,playupload;
    LinearLayout playregistrationback;
    Dialog dialog;

    String status,message,name,Name,Type,Place,District,Phone,Email,Image,Password,State,url=Config.baseurl+"play_registration.php";

    Uri uri;
    private RequestQueue rQueue;
    private static ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlaygroundRegistrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        playname = binding.playgroundregname;
        playtype = binding.playgroundregtype;
        playplace = binding.playgroundregplace;
        playdistrict = binding.playgroundregdistrict;
        playstate = binding.playgroundregstate;
        playphone = binding.playgroundregphone;
        playemail = binding.playgroundregemail;
        playimagename = binding.playgroundregimagename;
        playupload = binding.playgroundregupload;
        playpassword = binding.playgroundregpassword;
        playregistration = binding.playgroundregbutton;
        playregistrationback = binding.playgroundregback;

//      Setting the spinner values
        String[] typ = {"Select Type","Turf","Stadium","Club"};
        ArrayAdapter adaptertype = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, typ);
        playtype.setAdapter(adaptertype);

        String[] dis = {"Select District","Kasaragod","Kannur","Wayanad","Kozhikode","Malappuram","Palakkad",
                "Thrissur","Ernakulam","Alappuzha","Idukki","Kollam","Kottayam","Pathanamthitta","Thiruvananthapuram"};
        ArrayAdapter adapterdis = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, dis);
        playdistrict.setAdapter(adapterdis);

//      Button click to select the image and show the name of the image
        playupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

//        Button click to upload the image and register the details
        playregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playgroundreg(name,uri);
            }
        });

        playregistrationback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
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
                    cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        name = cursor.getString(index);
                        playimagename.setText(name);
                        Log.d("nameeeee>>>>  ",name);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                name = myFile.getName();
                playimagename.setText(name);
                Log.d("nameeeee>>>>  ",name);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void playgroundreg(final String pdfname, Uri pdffile) {

        //        getting the values from the fields
        Name = playname.getText().toString();
        Type = playtype.getSelectedItem().toString();
        Place = playplace.getText().toString();
        District = playdistrict.getSelectedItem().toString();
        State = playstate.getText().toString();
        Phone = playphone.getText().toString();
        Email = playemail.getText().toString();
        Image = playimagename.getText().toString();
        Password = playpassword.getText().toString();

//        Validating the values and highlight errors is any
        if (TextUtils.isEmpty(Name)){
            playname.setError("Required field");
            playname.requestFocus();
            return;
        }
        else if (Type.equals("Select Type")){
            Toast.makeText(getActivity(), "Please select Type", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(Place)){
            playplace.setError("Required field");
            playplace.requestFocus();
            return;
        }
        else if (District.equals("Select District")){
            Toast.makeText(getActivity(), "Please select the district", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(State)){
            playstate.setError("Required field");
            playstate.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Phone)){
            playphone.setError("Required field");
            playphone.requestFocus();
            return;
        }
        else if (!isPhoneValid(Phone)){
            playphone.setError("Invalid phone number");
            playphone.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Email)){
            playemail.setError("Required field");
            playemail.requestFocus();
            return;
        }
        else if (!isEmailValid(Email)){
            playemail.setError("Invalid email");
            playemail.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Image)){
            playimagename.setError("Required field");
            playimagename.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Password)){
            playpassword.setError("Required field");
            playpassword.requestFocus();
            return;
        }

//        For executing the query and image upload using the VolleyMultipartRequest
        InputStream iStream = null;
        try {
            iStream = getActivity().getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            showSimpleProgressDialog(getActivity(), null, "Loading", false);

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
//                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(getActivity(),Login.class));
                                    showDialog();
                                }
                                else if (status.equals("2")){
//                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    playemail.setError(message);
                                    playemail.requestFocus();
                                    return;
                                }
                                else {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
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
                    params.put("name", Name);
                    params.put("type", Type);
                    params.put("place", Place);
                    params.put("district", District);
                    params.put("state", State);
                    params.put("phone", Phone);
                    params.put("email", Email);
                    params.put("password", Password);

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
            rQueue = Volley.newRequestQueue(getActivity());
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showDialog(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.alert_dialogbox);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        AppCompatButton alertButton = dialog.findViewById(R.id.alertButton);

        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(),Login.class));
                getActivity().finish();
            }
        });
        dialog.show();
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

//    Phone validation method for pattern matching
    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

//    Email validation method for pattern matching
    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

}