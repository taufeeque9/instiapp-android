package app.insti.adapter;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.insti.R;
import app.insti.api.model.TimetableCourse;


public class TimetableCourseAdapter extends RecyclerView.Adapter<TimetableCourseAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private static ArrayList<TimetableCourse> courseList;
    private Dialog dialog;



    public TimetableCourseAdapter(Dialog d, LayoutInflater infl, ArrayList<TimetableCourse> courseList){

        inflater = infl;
        TimetableCourseAdapter.courseList = courseList;
        dialog = d;
    }

    @Override
    public TimetableCourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.course_card, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimetableCourseAdapter.MyViewHolder holder, final int position) {


        holder.editText.setText(courseList.get(position).getCourse_id());
        Log.d("print","yes");

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        protected EditText editText;
        protected Spinner spinner;

        public MyViewHolder(View itemView) {
            super(itemView);

            editText = (EditText) itemView.findViewById(R.id.course_edit_text);
            spinner = (Spinner) itemView.findViewById(R.id.course_spinner);



            ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(dialog.getContext(), R.array.dept_array, android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String dept = adapterView.getItemAtPosition(position).toString();
                    courseList.get(getAdapterPosition()).setCourse_dept(dept);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    courseList.get(getAdapterPosition()).setCourse_dept("");
                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    courseList.get(getAdapterPosition()).setCourse_id(editText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean focused)
                {
                    if (focused)
                    {
                        Log.d("tf", "running");
                        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        editText.setFocusable(true);
                        editText.requestFocus();
                    }
                }
            });

            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("tf", "clicked");
                }
            });

        }

    }
}