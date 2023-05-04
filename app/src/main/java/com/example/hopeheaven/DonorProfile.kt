package com.example.hopeheaven

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hopeheaven.databinding.FragmentDonorProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class DonorProfile : Fragment() {

    private lateinit var binding : FragmentDonorProfileBinding
    val user = FirebaseAuth.getInstance().currentUser
    val fireStoreDatabase = FirebaseFirestore.getInstance()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        binding = FragmentDonorProfileBinding.inflate(inflater, container, false)

        fireStoreDatabase.collection("Users").document(user?.uid.toString()).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
// Get the data from the document snapshot
                    val age = documentSnapshot.getString("age").toString()
                    val email = documentSnapshot.getString("email").toString()
                    val from = documentSnapshot.getString("from").toString()
                    val name = documentSnapshot.getString("name").toString()


                    binding.textView21.text = name
                    binding.textView15.text = age
                    binding.textView28.text = email
                    binding.textView24.text = from




                } else {
                    Log.d("", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("", "Error getting document: $exception")
            }





        return binding.root
    }


}