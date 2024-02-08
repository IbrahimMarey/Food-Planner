package com.example.foodplanner.network.firebase.firestore;

import static com.example.foodplanner.MainActivity.TAG;

import android.util.Log;

import com.example.foodplanner.model.dtos.UserDto;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepoImplementation;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireStore
{
    private FirebaseFirestore firebaseFirestore;
    private AuthFirebaseRepoImplementation authenticationFireBaseRepo;


    FireStore()
    {
        firebaseFirestore = FirebaseFirestore.getInstance();
        authenticationFireBaseRepo = AuthFirebaseRepoImplementation.getInstance();
    }

    public void addUser(UserDto userDto)
    {
        if (authenticationFireBaseRepo.isAuthenticated()) {
            Log.i(TAG, "addToFav: !!!!!!!!!!!!!!!!!!!!!1");
            firebaseFirestore.collection("users").add(userDto);
        }
    }
    /*
    //        firebaseFirestore.collection("user")
//                .document("favMeal")
//                .collection(authenticationFireBaseRepo.getUser().getUid())
//                .get().getResult().getMetadata();
        firebaseFirestore.collection("users")
                .document("favMeal")
                .collection(authFirebaseRepoImplementation.getUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    * */
}
