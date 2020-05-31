const firebase = require("firebase/app");
require("firebase/auth");
require("firebase/firestore");
require("firebase/storage");


const config = {
    apiKey: "AIzaSyDhT9Rf9AURv4H4k5fU93u-5Lc5w-fsfUU",
    authDomain: "facey-a2397.firebaseapp.com",
    databaseURL: "https://facey-a2397.firebaseio.com",
    projectId: "facey-a2397",
    storageBucket: "facey-a2397.appspot.com",
    messagingSenderId: "712648606114",
    appId: "1:712648606114:web:ad0c2b7b233b7f4f"
  };

  
firebase.initializeApp(config);

const db = firebase.firestore();
const auth = firebase.auth();
const storage = firebase.storage();

export {firebase, db, auth, storage}