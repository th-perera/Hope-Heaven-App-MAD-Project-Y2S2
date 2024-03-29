package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class MakeDonation : AppCompatActivity() {

    lateinit var fname: EditText
     lateinit var email: EditText
     lateinit var phone: EditText
     lateinit var category: EditText
     lateinit var qty: EditText
     lateinit var location: EditText
     lateinit var date: EditText
     lateinit var btnSaveData: Button
     lateinit var btnCancel:Button
    var count :Int =0

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_donation)

        fname = findViewById(R.id.etName)
        email = findViewById(R.id.mail)
        phone = findViewById(R.id.phone)
        category = findViewById(R.id.cat)
        qty = findViewById(R.id.qty)
        location = findViewById(R.id.location)
        date = findViewById(R.id.day)
        btnSaveData = findViewById(R.id.btnDonate)
        btnCancel=findViewById(R.id.btnCancel)

        dbRef = FirebaseDatabase.getInstance().getReference("Donations")


        btnSaveData.setOnClickListener {
            saveDonationData()
        }

        btnCancel.setOnClickListener {
            navigateToHomeFragment()
        }
    }

     fun saveDonationData() {
        val fName = fname.text.toString()
        val Email = email.text.toString()
        val Phone = phone.text.toString()
        val Category = category.text.toString()
        val Qty = qty.text.toString()
        val Location = location.text.toString()
        val Date = date.text.toString()

        var isValid = true

        if (fName.isEmpty()) {
            fname.error = "please enter name"
            isValid = false
        }
        if (Email.isEmpty()) {
            email.error = "please enter email"
            isValid = false
        } else if (!Pattern.matches("^\\S+@\\S+\\.\\S+\$", Email)) {
            email.error = "please enter valid email"
            isValid = false
        }
        if (Phone.isEmpty()) {
            phone.error = "please enter phone number"
            isValid = false
        } else if (phone.length() != 10) {
            phone.error = "please enter valid phone number"
            isValid = false
        }
        if (Category.isEmpty()) {
            category.error = "please enter category"
            isValid = false
        }
        if (Qty.isEmpty()) {
            qty.error = "please enter quantity"
            isValid = false
        }
        if (Location.isEmpty()) {
            location.error = "please enter pickup location"
            isValid = false
        }
        if (Date.isEmpty()) {
            date.error = "please enter pickup date"
            isValid = false
        }

        if (isValid) {
            val donationId = dbRef.push().key!!

            val donation = DonationModel(donationId, fName, Email, Category,Qty,Location,Date)

            dbRef.child(donationId).setValue(donation)
                .addOnCompleteListener {
                    Toast.makeText(this, "Your donation recorded successfully", Toast.LENGTH_LONG).show()

                    fname.text.clear()
                    email.text.clear()
                    phone.text.clear()
                    category.text.clear()
                    qty.text.clear()
                    location.text.clear()
                    date.text.clear()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "Please fill in all the required fields correctly", Toast.LENGTH_LONG).show()
        }
    }


    private fun navigateToHomeFragment() {
        val homeFragment = Home()
        supportFragmentManager.beginTransaction()
            .replace(R.id.home, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

}