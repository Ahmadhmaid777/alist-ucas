package com.app.AlistApp.utils;

import android.content.Context;

import com.app.AlistApp.R;
import com.app.AlistApp.model.Service;
import com.app.AlistApp.model.SubService;

import java.util.ArrayList;

public class ServicesData {
    private static ArrayList<Service> services = new ArrayList<>();

    public static ArrayList<Service> getServices(Context context) {
        ArrayList<Service> services = new ArrayList<>();
        ArrayList<SubService> subServices = new ArrayList<>();


        services.add(new Service(1, context.getResources().getString(R.string.services_act_how_request_tran), R.drawable.ic_watching_tv, subServices));

        subServices.add(new SubService(1, context.getResources().getString(R.string.newSub_mechanicalMeter)));
        subServices.add(new SubService(2, context.getResources().getString(R.string.newSub_mechanicalMeter)));
        subServices.add(new SubService(3, context.getResources().getString(R.string.newSub_smartMeter)));
        services.add(new Service(2, context.getResources().getString(R.string.services_act_newsub), R.drawable.ic_profile, subServices));

        subServices=null;
        subServices=new ArrayList<>();
        subServices.add(new SubService(1, context.getResources().getString(R.string.edit_sub_changeBeneficiary)));
        subServices.add(new SubService(2, context.getResources().getString(R.string.edit_sub_editConnectionType)));
        subServices.add(new SubService(3, context.getResources().getString(R.string.edit_sub_editName)));
        subServices.add(new SubService(4, context.getResources().getString(R.string.edit_sub_editSubscriberRating)));
        services.add(new Service(3, context.getResources().getString(R.string.services_act_edit_sub), R.drawable.ic_invite, subServices));

        subServices=null;
        subServices=new ArrayList<>();
        subServices.add(new SubService(1, context.getResources().getString(R.string.active_sub_stopSubscription)));
        subServices.add(new SubService(2, context.getResources().getString(R.string.active_sub_stopDamagedSubscription)));
        subServices.add(new SubService(3, context.getResources().getString(R.string.active_sub_ultimatesStopSubscription)));
        subServices.add(new SubService(4, context.getResources().getString(R.string.active_sub_ultimatesStopDamagedSubscription)));
        subServices.add(new SubService(5, context.getResources().getString(R.string.active_sub_activeDamagedSubscription)));
        subServices.add(new SubService(6, context.getResources().getString(R.string.active_sub_activeSubscription)));
        services.add(new Service(4, context.getResources().getString(R.string.services_act_active_sub), R.drawable.ic_switch, subServices));

        subServices=null;
        subServices=new ArrayList<>();
        subServices.add(new SubService(1, context.getResources().getString(R.string.moveOrEnlarge_sub_moveSub)));
        subServices.add(new SubService(2, context.getResources().getString(R.string.moveOrEnlarge_sub_enLargeSub)));
        subServices.add(new SubService(3, context.getResources().getString(R.string.moveOrEnlarge_sub_moveCounterInSame)));
        subServices.add(new SubService(4, context.getResources().getString(R.string.moveOrEnlarge_sub_moveSubToAntherPlace)));
        services.add(new Service(5, context.getResources().getString(R.string.services_act_movieOrEnlarge_sub), R.drawable.ic_destination, subServices));


        subServices=null;
        subServices=new ArrayList<>();
        subServices.add(new SubService(1, context.getResources().getString(R.string.edit_counter_prepaidToPrepaidCounter)));
        subServices.add(new SubService(2, context.getResources().getString(R.string.edit_counter_prepaidToSmartCounter)));
        subServices.add(new SubService(3, context.getResources().getString(R.string.edit_counter_mechanicalToMechanicalCounter)));
        subServices.add(new SubService(4, context.getResources().getString(R.string.edit_counter_mechanicalToPrepaidCounter)));
        subServices.add(new SubService(4, context.getResources().getString(R.string.edit_counter_smartToSmartCounter)));
        subServices.add(new SubService(4, context.getResources().getString(R.string.edit_counter_mechanicalToSmartCounter)));
        subServices.add(new SubService(4, context.getResources().getString(R.string.edit_counter_smartToPrepaidCounter)));
        services.add(new Service(5, context.getResources().getString(R.string.services_act_edit_counter), R.drawable.ic_destination, subServices));

        subServices=null;
        subServices=new ArrayList<>();
        subServices.add(new SubService(1, context.getResources().getString(R.string.inspection_tran_counterStamp)));
        subServices.add(new SubService(2, context.getResources().getString(R.string.inspection_tran_counterCheck)));
        services.add(new Service(6, context.getResources().getString(R.string.services_act_inspection_tran), R.drawable.ic_settings, subServices));


        subServices=null;
        subServices=new ArrayList<>();
        subServices.add(new SubService(1, context.getResources().getString(R.string.financial_tran_fillOutSurvey)));
        subServices.add(new SubService(2, context.getResources().getString(R.string.financial_tran_stopDeduction)));
        subServices.add(new SubService(3, context.getResources().getString(R.string.financial_tran_pauseDeduction)));
        subServices.add(new SubService(4, context.getResources().getString(R.string.financial_tran_updateSurvey)));
        subServices.add(new SubService(5, context.getResources().getString(R.string.financial_tran_addAutomaticPayment)));
        subServices.add(new SubService(6, context.getResources().getString(R.string.financial_tran_stopAutomaticPayment)));
        subServices.add(new SubService(7, context.getResources().getString(R.string.financial_tran_discountReply)));
        subServices.add(new SubService(8, context.getResources().getString(R.string.financial_tran_addDeduction)));
        subServices.add(new SubService(9, context.getResources().getString(R.string.financial_tran_transferDues)));
        subServices.add(new SubService(10, context.getResources().getString(R.string.financial_tran_requestDistributeServices)));
        subServices.add(new SubService(11, context.getResources().getString(R.string.financial_tran_editAutomaticPayment)));
        subServices.add(new SubService(12, context.getResources().getString(R.string.financial_tran_transformationPrepaidBoost)));
        subServices.add(new SubService(13, context.getResources().getString(R.string.financial_tran_returnPaymentToMinistryOfFinance)));
        subServices.add(new SubService(14, context.getResources().getString(R.string.financial_tran_removePause)));
        subServices.add(new SubService(15, context.getResources().getString(R.string.financial_tran_transferDuesToRam)));
        subServices.add(new SubService(16, context.getResources().getString(R.string.financial_tran_returnPrepaidTOGaza)));
        services.add(new Service(7, context.getResources().getString(R.string.services_act_Financial_tran), R.drawable.ic_settings, subServices));
        return services;

    }

}
