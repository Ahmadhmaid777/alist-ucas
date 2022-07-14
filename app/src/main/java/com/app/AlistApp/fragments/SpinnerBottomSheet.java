package com.app.AlistApp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.AlistApp.R;
import com.app.AlistApp.adapter.SpinnerAdapter;
import com.app.AlistApp.databinding.BottomSheetSpinnerBinding;
import com.app.AlistApp.databinding.BottomSheetSpinnerBinding;
import com.app.AlistApp.model.SpinnerItem;
import com.app.AlistApp.ui.RequestService.RequestServiceActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class SpinnerBottomSheet extends BottomSheetDialogFragment {
    static String ITEMS_LIST_KEY = "items list";
    public static int APPLICANT_SPINNER_ID = 1;
    public static int APPLICANT_JOB_SPINNER_ID = 2;
    public static String SPINNER_TYPE = "spinner type";

    private SpinnerAdapter recyclerAdapter;
    private BottomSheetSpinnerBinding binding;
    BottomSheetListener listener;
    static String TITLE_KEY = "title";
    int spinner_id;
    ArrayList<SpinnerItem> items;


    public static SpinnerBottomSheet newInstance(int spinnerId) {
        SpinnerBottomSheet sheetDialog = new SpinnerBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putInt(SPINNER_TYPE, spinnerId);
        sheetDialog.setArguments(bundle);
        return sheetDialog;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof RequestServiceActivity) {
            listener = (BottomSheetListener) context;
        } else {
            try {
                throw new Exception("your activity should implement BottomSheetListener");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spinner_id = getArguments().getInt(SPINNER_TYPE, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireActivity()).inflate(R.layout.bottom_sheet_spinner, null, false);
        binding = BottomSheetSpinnerBinding.bind(view);
        if (spinner_id == APPLICANT_JOB_SPINNER_ID) {
            binding.bottomSheetTitleTv.setText(getString(R.string.choose_applicant_job_spn_title));
        } else {
            binding.bottomSheetTitleTv.setText(getString(R.string.choose_applicant_et_hint));
        }


        recyclerAdapter = new SpinnerAdapter(items, new SpinnerAdapter.ItemClickedListener() {
            @Override
            public void onClickItemListener(SpinnerItem item) {
                listener.onSpinnerItemClickedListener(item, spinner_id);
                dismiss();
            }
        });
        initRecyclerView();


        if (spinner_id == APPLICANT_SPINNER_ID) {
            recyclerAdapter.setItems(getApplicantsType());
        } else {
            recyclerAdapter.setItems(getJobs());
        }
        binding.bottomSheetCloseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    public void initRecyclerView() {
        binding.bottomSheetRecycler.setAdapter(recyclerAdapter);
        binding.bottomSheetRecycler.setHasFixedSize(false);
        binding.bottomSheetRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    public ArrayList<SpinnerItem> getApplicantsType() {
        ArrayList<SpinnerItem> spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItem(1, "شخصي"));
        spinnerItems.add(new SpinnerItem(2, "وكيل"));
        spinnerItems.add(new SpinnerItem(3, "مفوض"));
        return spinnerItems;
    }

    public ArrayList<SpinnerItem> getJobs() {
        ArrayList<SpinnerItem> spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItem(1, "مهندس"));
        spinnerItems.add(new SpinnerItem(2, "طبيب"));
        spinnerItems.add(new SpinnerItem(3, "معلم"));
        return spinnerItems;
    }

    public interface BottomSheetListener {
        void onSpinnerItemClickedListener(SpinnerItem item, int spinnerId);
    }
}