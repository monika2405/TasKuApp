<?php

    //Filename  : connection.php
    //Date      : 16 Feb 2020
    //Developer : TasKu
    
    //CONNECTION CONFIGURATION
    $host = "localhost";
    $db   = "prob7812_mit";
    $user = "prob7812_mit"; //SESUAIKAN DENGAN KONFIGURASI PADA LAPTOP MASING-MASING
    $pwd  = "prob7812_mit";     //SESUAIKAN DENGAN KONFIGURASI PADA LAPTOP MASING-MASING
	
	$conn = mysqli_connect($host, $user, $pwd, $db);
	
	if($conn){
		echo "connection success";
	}else{
		echo "connection not success";
	}
?>