package com.example.playspot;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

        // Shared Preferences
        SharedPreferences pref;

        // Editor for Shared preferences
        SharedPreferences.Editor editor;

        // Context
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Sharedpref file name
        private static final String PREF_NAME = "Login";

        // All Shared Preferences Keys
        private static final String IS_LOGIN = "IsLoggedIn";

        // User name (make variable public to access from outside)
//    public static final String KEY_NAME = "name";

        // Email address (make variable public to access from outside)
//    public static final String KEY_PASSWORD = "password";

        // Constructor
        public SessionManager(Context context){
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        /**
         * Create login session
         *
         * @param id
         * @param type
         * @param email
         * @param place
         * @param district
         * @param state
         * @param phone
         * @param email
         * @param image
         * @param pass*/
        public void createLoginSession(String id, String email, String pass, String name, String place, String district, String phone, String image, String type, String state){
            // Storing login value as TRUE
            editor.putBoolean(IS_LOGIN, true);

            // Storing name in pref
            editor.putString("id", id);

            // Storing email in pref
            editor.putString("email", email);
            editor.putString("password", pass);
            editor.putString("name", name);
            editor.putString("place", place);
            editor.putString("district", district);
            editor.putString("phone", phone);
            editor.putString("image", image);
            editor.putString("state", state);
            editor.putString("type", type);


            // commit changes
            editor.commit();
        }

        /**
         * Check login method wil check user login status
         * If false it will redirect user to login page
         * Else won't do anything
         * */
        public boolean checkLogin(){
            // Check login status
            if(this.isLoggedIn()) {
                return true;
            } else {
                return false;
                // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, LoginActivity.class);
                // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
//            _context.startActivity(i);
            }

        }



        /**
         * Get stored session data
         * */
        public HashMap<String, String> getUserDetails(){
            HashMap<String, String> user = new HashMap<String, String>();
            // user name
            user.put("id", pref.getString("id", null));

            // user email id
            user.put("email", pref.getString("email", null));
            user.put("password", pref.getString("password", null));
            user.put("place", pref.getString("place", null));
            user.put("district", pref.getString("district", null));
            user.put("phone", pref.getString("phone", null));
            user.put("name", pref.getString("name", null));
            user.put("image", pref.getString("image", null));
            user.put("state", pref.getString("state", null));
            user.put("type", pref.getString("type", null));



            // return user
            return user;
        }

        /**
         * Clear session details
         * */
        public void logoutUser(){
            // Clearing all data from Shared Preferences
            editor.clear();
            editor.commit();

            // After logout redirect user to Login Activity
//        Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
//        _context.startActivity(i);
        }

        /**
         * Quick check for login
         * **/
        // Get Login State
        public boolean isLoggedIn(){
            return pref.getBoolean(IS_LOGIN, false);
        }
    }
