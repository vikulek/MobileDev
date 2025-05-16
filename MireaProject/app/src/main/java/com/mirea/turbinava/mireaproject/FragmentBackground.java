package com.mirea.turbinava.mireaproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.UUID;

public class FragmentBackground extends Fragment {

    private TextView statusTextView;
    private UUID workId;

    public FragmentBackground() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background, container, false);

        statusTextView = view.findViewById(R.id.statusTextView);
        Button startButton = view.findViewById(R.id.startWorkButton);

        startButton.setOnClickListener(v -> startBackgroundWork());

        return view;
    }

    private void startBackgroundWork() {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        workId = workRequest.getId();

        WorkManager.getInstance(requireContext()).enqueue(workRequest);

        WorkManager.getInstance(requireContext())
                .getWorkInfoByIdLiveData(workId)
                .observe(getViewLifecycleOwner(), workInfo -> {
                    if (workInfo != null) {
                        WorkInfo.State state = workInfo.getState();
                        statusTextView.setText("Статус: " + state.name());
                        Log.d("WorkerFragment", "Work status: " + state.name());
                    }
                });
    }
}