package com.calendar.anthony.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragNewEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragNewEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragNewEvent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button submit;
    private EditText title;
    private EditText time;
    private EditText description;

    private int day;
    private int month;
    private int year;


    private OnFragmentInteractionListener mListener;

    public FragNewEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragNewEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragNewEvent newInstance(int param1, int param2, int param3) {
        FragNewEvent fragment = new FragNewEvent();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
            year = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_new_event, container, false);

        submit = (Button)view.findViewById(R.id.submitNew);
        title = (EditText)view.findViewById(R.id.eTitle);
        time = (EditText)view.findViewById(R.id.eTime);
        description = (EditText)view.findViewById(R.id.eInfo);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventT = title.getText().toString().trim();
                String eventTi = time.getText().toString().trim();
                String eventI = description.getText().toString().trim();
                int num;

                try{
                    num = Integer.parseInt(eventTi);
                }catch(NumberFormatException e){
                    num = 0;
                }


                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(year, month, day);
                calendar1.setTimeZone(TimeZone.getTimeZone("CST"));
                calendar1.set(Calendar.HOUR_OF_DAY, num);
                calendar1.set(Calendar.MINUTE, 0);
                calendar1.set(Calendar.MILLISECOND, 0);

                long start = calendar1.getTimeInMillis();
                ContentValues values = new ContentValues();

                values.put(CalendarContract.Events.EVENT_LOCATION, "madison");
                values.put(CalendarContract.Events.DTSTART,eventTi);
                values.put(CalendarContract.Events.DTEND, start);
                values.put(CalendarContract.Events.TITLE, eventT);
                values.put(CalendarContract.Events.CALENDAR_ID, (long)1);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "CST");
                values.put(CalendarContract.Events.DESCRIPTION, eventI);

                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_CALENDAR);
                Uri uri = getActivity().getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);



                long eventId = new Long(uri.getLastPathSegment());
                Toast.makeText(getActivity().getBaseContext(),""+eventId,Toast.LENGTH_LONG).show();
                getActivity().finish();
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
