/////////////////////////////////////////////

  // Your web app's Firebase configuration
  // For Firebase JS SDK v7.20.0 and later, measurementId is optional
  var firebaseConfig = {
    apiKey: "AIzaSyB5A_-AomKfuOWjbTUInYvjXP_6F-_Makk",
    authDomain: "example-1813a.firebaseapp.com",
    projectId: "example-1813a",
    storageBucket: "example-1813a.appspot.com",
    messagingSenderId: "777437220676",
    appId: "1:777437220676:web:f4ef9e25c9979aca81d111",
    measurementId: "G-E3E8W4DNPY"
  };

/////////////////////////////////////////////

// Initialize Firebase
firebase.initializeApp(firebaseConfig);


firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            // User is signed in.

            document.getElementById("user_div").style.display = "block";
            document.getElementById("login_div").style.display = "none";

            var user1 = firebase.auth().currentUser;

            if(user1 !== null){

                var email_id = user.email;
                document.getElementById("user_para").innerHTML = "Welcome User : " + email_id;
                console.log("User email is : " + user1.email + "\nUser uid is " + user1.uid + "\nUser token is " + user.idToken);

            }

            getToken(user);

        } else {
            // No user is signed in.

            document.getElementById("user_div").style.display = "none";
            document.getElementById("login_div").style.display = "block";

        }
    });




/////////////////////////////////////////////

    function getToken(user) {
        user.getIdToken(true).then(function (idToken) {
            var url = "/auth";
            var method = "POST";
            var postData = "some-data";

            var shouldBeAsync = true;
            var request = new XMLHttpRequest();


            request.onload = function () {
                var status = request.status;
                var data = request.responseText;

                console.log("Url is : " + url
                    + "\nData is : " + data
                    + "\nStatus is :" + status
                    + "\nPost Data is : " + postData
                    + "\nUser is + " + user);

                if( status===200 || status===500 ){
                    alert("Successfully logged in!");
                }

            };
            request.open(method, url, shouldBeAsync);
            request.setRequestHeader("X-Firebase-Auth", idToken);
            request.send(postData);
        }).catch(function (error) {
            console.log(error);
        });
    }

/////////////////////////////////////////////