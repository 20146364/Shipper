<?php
 include_once("connection.php");
$SoTKNhan = $_POST["SoTKNhan"];
$MaHH = $_POST["MaHH"];
$TTDon = "0";
$TTNhan = "1";
$LuotXem = "0";
$TGNhan =  $_POST["TGNhan"];

$sql="INSERT INTO `nhanship`( `SoTKNhan`, `MaHH`, `TTDon`, `LuotXem`, TTNhan, TGNhan) VALUES (?,?,?,?,?, ?)";
$statement = mysqli_prepare($conn, $sql);
if ( !$statement) {
$response["success"] = false;
}else{
   mysqli_stmt_bind_param($statement, "ssssss",$SoTKNhan, $MaHH, $TTDon, $LuotXem, $TTNhan, $TGNhan);
   mysqli_stmt_execute($statement);

   $response = array();
   $response["success"] = true;
   echo json_encode($response);
}
?>
