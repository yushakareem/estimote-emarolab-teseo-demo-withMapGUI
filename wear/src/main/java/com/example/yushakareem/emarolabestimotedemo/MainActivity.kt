/*
Legend:
l - lab
o - office
m - mocap
q1 - quadrant 1 (just like in the cordinate system) ... q2, q3 and q4
b - beacon
prp - purple
pnk - pink
yel - yellow

also capital letters of the above mean the same
 */

package com.example.yushakareem.emarolabestimotedemo

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.widget.Toast
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory
import com.estimote.proximity_sdk.proximity.*
import com.estimote.proximity_sdk.proximity.ProximityContext
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.Context
import android.os.Handler
import java.util.*


class MainActivity : WearableActivity() {

    //beacon flags
    private var near_b_yel1: Boolean = false
    private var near_b_prp1: Boolean = false
    private var near_b_pnk1: Boolean = false
    private var far_b_yel1: Boolean = false
    private var far_b_prp1: Boolean = false
    private var far_b_pnk1: Boolean = false

    private var near_b_yel2: Boolean = false
    private var near_b_prp2: Boolean = false
    private var near_b_pnk2: Boolean = false
    private var far_b_yel2: Boolean = false
    private var far_b_prp2: Boolean = false
    private var far_b_pnk2: Boolean = false

    private var inZone_l_o: Boolean = false
    private var inZone_l_m: Boolean = false

    // Currently present in location
    private var currentLocation: String? = null

    // To get unique ID of app installation
    private var uniqueID: String? = null
    private val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"

    // Related to proximity methods
    private lateinit var proximityObserver: ProximityObserver
    private var proximityObservationHandler: ProximityObserver.Handler? = null

    private val cloudCredentials = EstimoteCloudCredentials("laboratorium-dibris-gmail--kfg", "90e1b9d8344624e9c2cd42b9f5fd6392")

    private val displayToastAboutMissingRequirements: (List<Requirement>) -> Unit = { Toast.makeText(this, "Unable to start proximity observation. Requirements not fulfilled: ${it.size}", Toast.LENGTH_SHORT).show() }
    private val displayToastAboutError: (Throwable) -> Unit = { Toast.makeText(this, "Error while trying to start proximity observation: ${it.message}", Toast.LENGTH_SHORT).show() }

    // Getting reference to firebase DB
    private val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef : DatabaseReference = database.getReference("/users")
    private var childUpdates = HashMap<String,Any>()

    // Init unique ID for the app installed on a smart-device
    private var UID: String? = null

    private var timeOfEntry: Long = 0

    private fun startProximityObservation() {

        // get the UID
        UID = id(applicationContext)
        Log.d("UID for this smart device is: ",UID)

        proximityObserver = ProximityObserverBuilder(applicationContext,cloudCredentials)
                .withOnErrorAction {
                    Log.e("Beacon", "Beacon scan failed: $it")
                }
                .withBalancedPowerMode()
                .build()

//        val zoneLO = this.proximityObserver.zoneBuilder()
//            .forTag("EmaroLab Office Area")
//            .inCustomRange(3.5)
//            .withOnChangeAction(object : Function1<List<ProximityContext>, Unit> {
//                override fun invoke(contexts : List<ProximityContext>) {
//                    val wall = ArrayList<String?>()
//                    for (context in contexts) {
//                        wall.add(context.getAttachments()["Wall"])
//                    }
//                    //Log.d("app", "In range of desks: $wall")
////                    inZone_l_o = wall.count() >= 1
////                    inZone_l_m = false
////                    thinkAndAct()
//                    if (wall.count() >= 1)
//                        writeUserLocationInFirebase(UID,"lab_office")
//                }
//            })
//            .create()

        val zoneLOQ1 = this.proximityObserver.zoneBuilder()
                .forTag("EmaroLab Office Area")
                .inCustomRange(2.0)
                .withOnEnterAction(object : Function1<ProximityContext, Unit> {
                    override fun invoke(context: ProximityContext) {
                        val wall = context.getAttachments()["Wall"]
                        //Log.d("watch","Welcome to $wall's wall")
                        if (wall == "Alessandro")
                            writeUserLocationInFirebase(UID,"lab_office_q1")
//                            near_b_yel1 = true
//                        thinkAndAct()
                    }
                })
                .withOnExitAction(object : Function1<ProximityContext, Unit> {
                    override fun invoke(context: ProximityContext) {
                        val wall = context.getAttachments()["Wall"]
                        //Log.d("app", "Bye bye, from $wall's wall")
                        if (wall == "Alessandro")
                            writeUserLocationInFirebase(UID,"lab_office")
//                            near_b_yel1 = false
//                        thinkAndAct()
                    }
                })
                .create()

        val zoneLOQ2 = this.proximityObserver.zoneBuilder()
                .forTag("EmaroLab Office Area")
                .inCustomRange(2.0)
                .withOnEnterAction(object : Function1<ProximityContext, Unit> {
                    override fun invoke(context: ProximityContext) {
                        val wall = context.getAttachments()["Wall"]
                        //Log.d("watch","Welcome to $wall's wall")
                        if (wall == "Yusha")
                            writeUserLocationInFirebase(UID,"lab_office_q2")
//                            near_b_prp1 = true
//                        thinkAndAct()
                    }
                })
                .withOnExitAction(object : Function1<ProximityContext, Unit> {
                    override fun invoke(context: ProximityContext) {
                        val wall = context.getAttachments()["Wall"]
                        //Log.d("app", "Bye bye, from $wall's wall")
                        if (wall == "Yusha")
                            writeUserLocationInFirebase(UID,"lab_office")
//                            near_b_prp1 = false
//                        thinkAndAct()
                    }
                })
                .create()

        val zoneLOQ3 = this.proximityObserver.zoneBuilder()
                .forTag("EmaroLab Office Area")
                .inCustomRange(2.0)
                .withOnEnterAction(object : Function1<ProximityContext, Unit> {
                    override fun invoke(context: ProximityContext) {
                        val wall = context.getAttachments()["Wall"]
                        //Log.d("watch","Welcome to $wall's wall")
                        if (wall == "OfficeCenter")
                            writeUserLocationInFirebase(UID,"lab_office_q3")
//                            near_b_prp1 = true
//                        thinkAndAct()
                    }
                })
                .withOnExitAction(object : Function1<ProximityContext, Unit> {
                    override fun invoke(context: ProximityContext) {
                        val wall = context.getAttachments()["Wall"]
                        //Log.d("app", "Bye bye, from $wall's wall")
                        if (wall == "OfficeCenter")
                            writeUserLocationInFirebase(UID,"lab_office")
//                            near_b_prp1 = false
//                        thinkAndAct()
                    }
                })
                .create()

        val zoneLM = this.proximityObserver.zoneBuilder()
                .forTag("MotionCapture Area")
                .inCustomRange(3.0)
                .withOnChangeAction(object : Function1<List<ProximityContext>, Unit> {
                    override fun invoke(contexts : List<ProximityContext>) {
                        val wall = ArrayList<String?>()
                        for (context in contexts) {
                            wall.add(context.getAttachments()["Wall"])
                        }
                        //Log.d("app", "In range of desks: $wall")
//                        inZone_l_m = wall.count() >= 1
//                        inZone_l_o = false
//                        thinkAndAct()
                        if (wall.count() >= 1) {
                            timeOfEntry = System.currentTimeMillis()
                            writeUserLocationInFirebase(UID, "lab_mocap")
//                            val handler: Handler? = null
//                            handler!!.postDelayed({ writeUserLocationInFirebase(UID, "lab_alarm") }, 15000)
                        }
                    }
                })
                .create()

        this.proximityObservationHandler = this.proximityObserver.addProximityZones(zoneLM,zoneLOQ1,zoneLOQ2,zoneLOQ3).start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        // Requirements check
        RequirementsWizardFactory.createEstimoteRequirementsWizard().fulfillRequirements(
                this,
                onRequirementsFulfilled = { startProximityObservation() },
                onRequirementsMissing = displayToastAboutMissingRequirements,
                onError = displayToastAboutError
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // IMPORTANT (This applies for mobile app):
        // If you don't stop the scan here, the foreground service will remain active EVEN if the user kills your APP.
        // You can use it to retain scanning when app is killed, but you will need to handle actions properly.
        proximityObservationHandler?.stop()
    }

    // To get a uniqueID for the installation of the app
    @Synchronized
    fun id(context: Context): String {
        if (uniqueID == null) {
            val sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE)
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null)
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString()
                val editor = sharedPrefs.edit()
                editor.putString(PREF_UNIQUE_ID, uniqueID)
                editor.apply()
            }
        }
        return uniqueID as String
    }

    private fun thinkAndAct() {
        writeUserLocationInFirebase(UID, locationFromProximityLogic(near_b_yel1,far_b_yel1,near_b_prp1,far_b_prp1,near_b_pnk1,far_b_pnk1,near_b_yel2,far_b_yel2,near_b_pnk2,far_b_pnk2,near_b_prp2,far_b_prp2,inZone_l_o,inZone_l_m))
    }

    private fun locationFromProximityLogic(near_b_yel1: Boolean, far_b_yel1: Boolean, near_b_prp1: Boolean, far_b_prp1: Boolean, near_b_pnk1: Boolean, far_b_pnk1: Boolean, near_b_yel2: Boolean, far_b_yel2: Boolean, near_b_pnk2: Boolean, far_b_pnk2: Boolean, near_b_prp2: Boolean, far_b_prp2: Boolean, inZone_l_o: Boolean, inZone_l_m: Boolean): String? {
        if (inZone_l_o) {
            currentLocation = "lab_office"
            if (near_b_yel1)
                currentLocation = "lab_office_q1"
            if (near_b_prp1)
                currentLocation = "lab_office_q2"
            if (near_b_pnk1 && far_b_prp1)
                currentLocation = "lab_office_q3"
            if (near_b_pnk1 && far_b_yel1)
                currentLocation = "lab_office_q4"
        }
        else if (inZone_l_m) currentLocation = "lab_mocap"
        else currentLocation = "unknown"
        return currentLocation
    }

    private fun writeUserLocationInFirebase(uid_of_user : String?, locationFromProximityLogic: String?) {
        if (uid_of_user != null) {
            if (locationFromProximityLogic != null) {
                childUpdates.put("/$uid_of_user/location", locationFromProximityLogic)
            } else {
                Log.e("watch", "Problem in getting location based on proximity logic")
            }
        } else {
            Log.e("watch", "Did not get unique ID for the smart-device")
        }
        myRef.updateChildren(childUpdates)
    }
}


/*
UID of watch paired with LG G6-P1 is 997ce849-78f0-43c1-b033-7da567cb1d91
UID of watch paired with LG G6-P2 is f0b03ed8-9527-49e0-ad24-5a70a2cf254e
 */
