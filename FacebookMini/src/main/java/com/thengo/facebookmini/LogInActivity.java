package com.thengo.facebookmini;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.util.Log;

import com.facebook.*;
import com.facebook.widget.LoginButton;

public class LogInActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginInputFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LoginInputFragment extends Fragment {

        private static final String TAG = "LoginInputFragment";

        private UiLifecycleHelper uiHelper;

        /**
         * Listens for session changes and calls callback function onSessionStateChange
         */
        private Session.StatusCallback callback = new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChange(session, state, exception);
            }
        };

        public LoginInputFragment() {
        }

        /**
         * Making sure sessions are setup correctly on all events
         * because using UiLifecycHelper from Facebook SDK
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            uiHelper = new UiLifecycleHelper(getActivity(), callback);
            uiHelper.onCreate(savedInstanceState);
        }

        /**
         * Making sure sessions are setup correctly on all events
         * because using UiLifecycHelper from Facebook SDK
         */
        @Override
        public void onResume() {
            super.onResume();

            // For scenarios where the main activity is launched and user
            // session is not null, the session state change notification
            // may not be triggered. Trigger it if it's open/closed.
            Session session = Session.getActiveSession();
            if (session != null &&
                    (session.isOpened() || session.isClosed()) ) {
                onSessionStateChange(session, session.getState(), null);
            }

            uiHelper.onResume();
        }

        /**
         * Making sure sessions are setup correctly on all events
         * because using UiLifecycHelper from Facebook SDK
         */
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            uiHelper.onActivityResult(requestCode, resultCode, data);
        }

        /**
         * Making sure sessions are setup correctly on all events
         * because using UiLifecycHelper from Facebook SDK
         */
        @Override
        public void onPause() {
            super.onPause();
            uiHelper.onPause();
        }

        /**
         * Making sure sessions are setup correctly on all events
         * because using UiLifecycHelper from Facebook SDK
         */
        @Override
        public void onDestroy() {
            super.onDestroy();
            uiHelper.onDestroy();
        }

        /**
         * Making sure sessions are setup correctly on all events
         * because using UiLifecycHelper from Facebook SDK
         */
        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            uiHelper.onSaveInstanceState(outState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_log_in, container, false);

            // Allow fragment to receive onActivityResult instead of Activity
            LoginButton authButton = (LoginButton) rootView.findViewById(R.id.authButton);
            authButton.setFragment(this);

            return rootView;
        }

        private void onSessionStateChange(Session session, SessionState state, Exception exception) {
            if (state.isOpened()) {
                Log.i(TAG, "Logged in...");
            } else if (state.isClosed()) {
                Log.i(TAG, "Logged out...");
            }
        }
    }

}
