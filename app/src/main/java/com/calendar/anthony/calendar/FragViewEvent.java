package com.calendar.anthony.calendar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragViewEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragViewEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragViewEvent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> eventInfo;
    private ArrayList<Integer>intEventIDs;

    private ListView listView;

    private OnFragmentInteractionListener mListener;

    public FragViewEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment FragViewEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragViewEvent newInstance(ArrayList<String> param1, ArrayList param2) {
        FragViewEvent fragment = new FragViewEvent();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, param1);
        args.putIntegerArrayList(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventInfo = getArguments().getStringArrayList(ARG_PARAM1);
            intEventIDs = getArguments().getIntegerArrayList((ARG_PARAM2));
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_view_event, container, false);

        listView = (ListView)view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, eventInfo);

        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String[] selArgs = new String[]{Integer.toString(intEventIDs.get(position))};
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.WRITE_CALENDAR);
                int deleted = getActivity().getContentResolver().delete(
                        CalendarContract.Events.CONTENT_URI,
                        CalendarContract.Events._ID + " =? ", selArgs);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
