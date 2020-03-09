<?php
include 'connection.php';

$email = $_POST['email'];
$username = $_POST['username'];
$pass = $_POST['password'];
$name = $_POST['name'];
$birth_date = $_POST['birth_date'];
$handphone_number = $_POST['handphone_number'];

//SET QUERIES
$sql  = "INSERT INTO 1417002_mst_user (email,username,password,name,birth_date,handphone_numb) ";
$sql .= "VALUES ('$email','$username','$pass','$name','$birth_date','$handphone_number') ";


// array for JSON response  
$result   = mysqli_query($conn,$sql) or die(mysqli_error("gagal"));


$response = array();
$response["data"] = array();

while ($row = mysqli_fetch_array($result)) {
	// temp user array
	
	$rs = array();

	
	// push single product into final response array
	array_push($response["data"], $rs);
}
// success
$response["success"] = 1;
$response["message"] = "Successfully retrieved";

echo json_encode($response,JSON_UNESCAPED_SLASHES);
?>