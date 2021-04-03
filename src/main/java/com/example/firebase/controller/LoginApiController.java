package com.example.firebase.controller;

import com.example.firebase.firebase.FirebaseService;
import com.example.firebase.model.TestModel;
import com.google.api.client.util.Strings;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/login")
public class LoginApiController {

    private static final String TAG = LoginApiController.class.getSimpleName();

    private final FirebaseService firebaseService;

    private UserRecord user;

    @Autowired
    public LoginApiController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<Object> authFirebase(HttpServletRequest request) throws IOException, InterruptedException {

        final String authToken = request.getHeader("X-Firebase-Auth");

        if (Strings.isNullOrEmpty(authToken)) {
            throw new RuntimeException("Invaild auth token");
        }

        System.out.println("Auth Token is : " + authToken);

        user = firebaseService.verifyToken(authToken);

        System.out.println(TAG + "User's name is : " + user.getDisplayName() + "\nUser's Email is " + user.getEmail());

        testFirebase();

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }


    private void testFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference rootRef = database.getReference();

        //Here is for set test datas to database
//        {
//            "age" : 23,
//            "dept" : "Ceng",
//            "hobby" : {
//              "one" : {
//                "name" : "Basketball"
//              },
//              "three" : {
//                "name" : "Pipe Smoking"
//              },
//              "two" : {
//                "name" : "Chess"
//              }
//            },
//            "name" : "Yunus Bora",
//            "surname" : "Erciyas"
//        }

        /*
        Map<String, TestModelHobby> testMap= new HashMap<String, TestModelHobby>();
        testMap.put("one", new TestModelHobby("Basketball"));
        testMap.put("two", new TestModelHobby("Chess"));
        testMap.put("three", new TestModelHobby("Pipe Smoking"));
        TestModel model = new TestModel("Yunus Bora", "Erciyas", "Ceng", testMap, 23);
        rootRef.setValueAsync(model);
        */


        rootRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                TestModel model = dataSnapshot.getValue(TestModel.class);
                System.out.println(model);
            }

            public void onCancelled(DatabaseError databaseError) {
                System.out.println(TAG + "Error is " + databaseError);
            }
        });
    }
}