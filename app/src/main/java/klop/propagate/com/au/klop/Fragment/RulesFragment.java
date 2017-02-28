package klop.propagate.com.au.klop.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import klop.propagate.com.au.klop.R;

public class RulesFragment extends Fragment {
    private TextView tvEx;
    private Button btnWatch;
    private boolean isYoutubeInstalled = false;

    public static RulesFragment newInstance() {
        RulesFragment fragment = new RulesFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rules, container, false);

        StringBuilder text = new StringBuilder();
        try {
            InputStream is = getResources().openRawResource(getResources().getIdentifier("example","raw",getActivity().getPackageName()));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        tvEx = (TextView) rootView.findViewById(R.id.tvExample);
        tvEx.setText(Html.fromHtml(text.toString()));

        String packageName = "com.google.android.youtube";
        isYoutubeInstalled = isAppInstalled(packageName);

        btnWatch = (Button) rootView.findViewById(R.id.btnWatch);
        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isYoutubeInstalled == true){
                    String id = "watch?v=lIdWY9gPqhE&feature=share";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + id));
                    startActivity(intent);
                }else {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=lIdWY9gPqhE&feature"));
                    startActivity(myIntent);
                }

            }
        });
        return rootView;
    }
    private boolean isAppInstalled(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }
}
