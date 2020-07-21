package app.insti.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

import app.insti.R;
import app.insti.Utils;
import app.insti.adapter.TimetableCourseAdapter;
import app.insti.api.RetrofitInterface;
import app.insti.api.model.TimetableCourse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimetableDialog extends AppCompatDialogFragment {
    private ArrayList<TimetableCourse> timetableCourses;
    private RecyclerView recyclerView;
    private TimetableCourseAdapter timetableCourseAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LayoutInflater inflater;
    private Dialog dialog;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        timetableCourses = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            TimetableCourse course = new TimetableCourse("", i+1);
            timetableCourses.add(course);
        }
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.timetable_box, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.courses_rv);
        Button add_course_button = view.findViewById(R.id.add_course_button);
        add_course_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                TimetableCourse course = new TimetableCourse("", timetableCourses.size()+1);
                timetableCourses.add(course);
                timetableCourseAdapter.notifyItemInserted(timetableCourses.size() - 1);
            }
        });


        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("tf", timetableCourses.toString());
                        for(TimetableCourse course : timetableCourses){
                            if(course.isEmpty()){
                                Toast.makeText(getActivity(), "Did not get timetable.\nSome of the text fields were empty", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        if(! hasStoragePermission()){
                            Toast.makeText(dialog.getOwnerActivity(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                        else{
//                            getTimetableFile();


                        }


                    }});




//        TextView t = view.findViewById(R.id.timetable_text);

        dialog =  builder.create();
        return dialog;

    }


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    public void onStart() {
        super.onStart();
        timetableCourseAdapter = new TimetableCourseAdapter(dialog, inflater, timetableCourses);
        recyclerView.setAdapter(timetableCourseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if(! hasStoragePermission()){
            getStoragePermission();
        }

    }

    private boolean hasStoragePermission(){
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_DENIED;
    }

    private void getStoragePermission(){
        // Requesting the permission
        ActivityCompat.requestPermissions(getActivity(),
            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
            TimetableDialog.STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (! (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) ){
                Toast.makeText(getActivity(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getTimetableFile(){
        RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();
        Call<ResponseBody> call = retrofitInterface.getTimetable(Utils.getSessionIDHeader(), timetableCourses);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
//                                    Log.d("tf", response.toString());
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                    Log.d("tf", String.valueOf(writtenToDisk));
                }
                else{
                    Log.d("tf", "code: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("tf", "failed");
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
//            Environment.getExternalStorageDirectory().getPath()
            File file = new File( Environment.DIRECTORY_DOWNLOADS + File.separator + "timetable.ics");
            Log.d("tf", Environment.DIRECTORY_DOWNLOADS + File.separator + "timetable.ics");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("tf" , fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                Log.d("tf", "ioexcep");
                Log.d("tf", e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.d("tf", e.getMessage());
            return false;
        }
    }
}
