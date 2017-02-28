package klop.propagate.com.au.klop.Fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import klop.propagate.com.au.klop.Adapter.PlayersAdapter;
import klop.propagate.com.au.klop.AddPlayerActivity;
import klop.propagate.com.au.klop.Database.DatabasePlayers;
import klop.propagate.com.au.klop.Model.NewPlayers;
import klop.propagate.com.au.klop.R;

public class PlayersFragment extends Fragment {

    private List<NewPlayers> listdata = new ArrayList<>();
    private PlayersAdapter playersAdapter;
    private Button btnAdd;
    private ListView listViewPlayers;

    public static PlayersFragment newInstance() {
        PlayersFragment fragment = new PlayersFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_players, container, false);

        btnAdd = (Button)rootView.findViewById(R.id.btnAdd);
        listViewPlayers = (ListView)rootView.findViewById(R.id.lvPlayers);
        DatabasePlayers db = new DatabasePlayers(getActivity());
        playersAdapter = new PlayersAdapter(listdata,getActivity());
        listViewPlayers.setAdapter(playersAdapter);
        listViewPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewPlayers newPlayers = listdata.get(i);
                Intent intent1 = new Intent(getActivity(), AddPlayerActivity.class);
                String namePlayer = listdata.get(i).getFirstName();
                String fName = "";
                String lName = "";
                if (namePlayer.indexOf("_") > 0) {
                    fName = namePlayer.substring(0, namePlayer.indexOf("_"));
                    lName = namePlayer.substring(namePlayer.indexOf("_") + 1, namePlayer.length());
                }

                intent1.putExtra("fName", fName);
                intent1.putExtra("lName", lName);
                intent1.putExtra("image", listdata.get(i).getImg());
                intent1.putExtra("id", listdata.get(i).getId());
                intent1.putExtra("update", true);
                startActivity(intent1);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(getActivity(),AddPlayerActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabasePlayers db = new DatabasePlayers(getActivity());
        listdata.clear();
        listdata.addAll(db.getallPlayers());
        listViewPlayers.setAdapter(playersAdapter);
        playersAdapter.notifyDataSetChanged();
    }
}
