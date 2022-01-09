package com.example.play.stadium.StadiumProfile;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentTransaction;

import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.play.Config;
import com.example.play.Login;
import com.example.play.R;
import com.example.play.SessionManager;
import com.example.play.VolleyMultipartRequest;
import com.example.play.databinding.FragmentStadiumBookingBinding;
import com.example.play.databinding.FragmentStadiumProfileBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StadiumProfileFragment extends Fragment {

    private FragmentStadiumProfileBinding binding;
    ImageView dp;
    ImageButton logout;
    TextView sname, stype, splace, sdistrict, sstate, sphone, semail;
    AppCompatButton update, upi;
    String id, image, pass, name, type, place, district, state, phone, email, status, message, url=Config.baseurl+"stadium_dp_update.php";

    Uri uri;
    private RequestQueue rQueue;
    private static ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStadiumProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dp = binding.stadiumDp;
        logout = binding.slogout;
        sname = binding.sName;
        stype = binding.sType;
        splace = binding.sLocation;
        sdistrict = binding.sDistrict;
        sstate = binding.sState;
        sphone = binding.sPhone;
        semail = binding.sEmail;
        update = binding.sUpdate;
        upi = binding.sUpi;

        id = new SessionManager(getActivity()).getUserDetails().get("id");
        image = new SessionManager(getActivity()).getUserDetails().get("image");
        name = new SessionManager(getActivity()).getUserDetails().get("name");
        type = new SessionManager(getActivity()).getUserDetails().get("type");
        place = new SessionManager(getActivity()).getUserDetails().get("place");
        district = new SessionManager(getActivity()).getUserDetails().get("district");
        state = new SessionManager(getActivity()).getUserDetails().get("state");
        phone = new SessionManager(getActivity()).getUserDetails().get("phone");
        email = new SessionManager(getActivity()).getUserDetails().get("email");
        pass = new SessionManager(getActivity()).getUserDetails().get("password");
        sname.setText(name);
        stype.setText(type);
        splace.setText(place);
        sdistrict.setText(district);
        sstate.setText(state);
        sphone.setText(phone);
        semail.setText(email);
        Picasso.get().load(Config.imageUrl+image).into(dp);

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDp();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StadiumProfileUpdate.class);
                startActivity(intent);
            }
        });

        upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StadiumUpiUpdate.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SessionManager(getActivity()).logoutUser();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Logout Successful", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });


        return root;
    }

    private void changeDp() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,1);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
//            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        displayName = cursor.getString(index);
                        Log.d("nameeeee>>>>  ",displayName);

                        uploadPDF(displayName,uri);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPDF(final String pdfname, Uri pdffile){

        InputStream iStream = null;
        try {

            iStream = getActivity().getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            showSimpleProgressDialog(getActivity(), null, "Uploading", false);

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
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    new SessionManager(getActivity()).createLoginSession(id,email,pass,type,name,place,district,phone,image,state);
//                                    Fragment currentfragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
//                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                                    fragmentTransaction.detach(currentfragment);
//                                    fragmentTransaction.attach(currentfragment);
//                                    fragmentTransaction.commit();

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
                    params.put("id", id);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}