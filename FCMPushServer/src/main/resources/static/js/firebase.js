var firebaseConfig = {
    apiKey: "apiKey",
    authDomain: "firebaseDomain",
    databaseURL: "databaseURL",
    projectId: "projectID",
    storageBucket: "storageBucket",
    messagingSenderId: "MSGID",
    appId: "appID",
    measurementId: "measurementId"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);
firebase.analytics();
