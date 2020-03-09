<?php
include 'connection.php';

$id = $_POST['id_user'];
$title = $_POST['title'];
$content = $_POST['content'];
$date = $_POST['date'];

//SET QUERIES
$sql  = "INSERT INTO 1417002_mst_notes (id_user,notes_title,isi_notes,time_upload) ";
$sql .= "VALUES ('$id','$title','$content','$date') ";


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