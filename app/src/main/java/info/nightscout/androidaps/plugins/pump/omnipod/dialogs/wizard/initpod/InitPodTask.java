package info.nightscout.androidaps.plugins.pump.omnipod.dialogs.wizard.initpod;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;

import info.nightscout.androidaps.plugins.configBuilder.ProfileFunctions;
import info.nightscout.androidaps.plugins.pump.omnipod.defs.PodInitActionType;
import info.nightscout.androidaps.plugins.pump.omnipod.driver.comm.AapsOmnipodManager;

/**
 * Created by andy on 11/12/2019
 */
public class InitPodTask extends AsyncTask<Void, Void, String> {

    private InitActionFragment initActionFragment;

    public InitPodTask(InitActionFragment initActionFragment) {

        this.initActionFragment = initActionFragment;
    }

    protected void onPreExecute() {
        initActionFragment.progressBar.setVisibility(View.VISIBLE);
        initActionFragment.errorView.setVisibility(View.GONE);
        initActionFragment.retryButton.setVisibility(View.GONE);
    }

    @Override
    protected String doInBackground(Void... params) {

        if (initActionFragment.podInitActionType == PodInitActionType.PairAndPrimeWizardStep) {
            initActionFragment.callResult = AapsOmnipodManager.getInstance().initPod(
                    initActionFragment.podInitActionType,
                    initActionFragment.instance,
                    null
            );
        } else if (initActionFragment.podInitActionType == PodInitActionType.FillCannulaSetBasalProfileWizardStep) {
            initActionFragment.callResult = AapsOmnipodManager.getInstance().initPod(
                    initActionFragment.podInitActionType,
                    initActionFragment.instance,
                    ProfileFunctions.getInstance().getProfile()
            );
        } else if (initActionFragment.podInitActionType == PodInitActionType.DeactivatePodWizardStep) {
            initActionFragment.callResult = AapsOmnipodManager.getInstance().deactivatePod(initActionFragment.instance);
        }

        return "OK";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        initActionFragment.actionOnReceiveResponse(result);
    }


}
