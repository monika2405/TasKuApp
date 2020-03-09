<?php
include 'connection.php';

$id_user = $_POST['id_user'];

$mysql_qry = "SELECT * FROM 1417002_mst_user WHERE id_user = ".$id_user."";

// error jika query gagal
$response["success"] = 0;
$response["message"] = "Error Input";

$result   = mysqli_query($conn,$mysql_qry) or die(json_encode($response,JSON_UNESCAPED_SLASHES));

$response = array();
        $response["data"] = array();
        
        while ($row = mysqli_fetch_array($result)) {
            // temp user array
            
            $payload = array();
			$payload["id_notes"]  = $row["id_notes"];
            $payload["notes_title"]  = $row["email"];
            $payload["isi_notes"] = $row["username"];
            $payload["time_upload"] = $row["password"];
            
            // push single product into final response array
            array_push($response["data"], $payload);
        }
        // success
        $response["success"] = 1;
        $response["message"] = "Successfully retrieved";
    
        echo json_encode($response,JSON_UNESCAPED_SLASHES);
?>