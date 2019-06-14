package com.example.patientscard.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.patientscard.R
import com.example.patientscard.asynctasks.MedicationAsyncTask
import com.example.patientscard.asynctasks.MedicationRequestAsyncTask
import com.example.patientscard.fragments.MedicationsFragment
import com.example.patientscard.fragments.PatientsFragment
import com.example.patientscard.models.Medication
import com.example.patientscard.models.Patient
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    PatientsFragment.OnFragmentInteractionListener,
MedicationsFragment.OnFragmentInteractionListener{
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        val isoSSSDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ")
    }

    var lastFragment:Fragment = PatientsFragment()
    var medications = ArrayList<Medication>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        setUpFirstFragment()
    }

    private fun setUpFirstFragment() {
        MedicationAsyncTask(medications).execute()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, lastFragment).commit()
    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(lastFragment is PatientsFragment) {
                    (lastFragment as PatientsFragment).searchPatient(query)
                }
                myActionMenuItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_patients -> {
                openPatientsFragment()
            }
            R.id.nav_medications -> {
                openMedicationsFragment()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openMedicationsFragment() {
        lastFragment = MedicationsFragment.newInstance(medications)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, lastFragment).commit()
    }

    private fun openPatientsFragment() {
        lastFragment = PatientsFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, lastFragment).commit()
    }

    fun openPatientActivity(patient: Patient) {
        val intent = Intent(this, PatientActivity::class.java)
        val bundle = Bundle()
        bundle.putString("id", patient.id)
        bundle.putString("firstName", patient.firstName)
        bundle.putString("lastName", patient.lastName)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
