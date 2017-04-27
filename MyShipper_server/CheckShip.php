<?php
include_once("connection.php");

$SoTKNhan = $_POST["SoTKNhan"];
$MaHH = $_POST["MaHH"];
// $SoTKNhan = "LHPL0102";
// $MaHH = "MMH0028";

$sql = "UPDATE `nhanship` SET `TTDon`= '1'  WHERE SoTKNhan = ? AND MaHH = ?";
$statement = mysqli_prepare($conn,$sql);

if ( !$statement) {
$response["success"] = true;
}else{

mysqli_stmt_bind_param($statement, "ss", $SoTKNhan, $MaHH);
mysqli_stmt_execute($statement);

$sql1= "DELETE FROM `nhanship` WHERE MaHH = ? and TTDon = '0' ";
$statement1 = mysqli_prepare($conn,$sql1);
mysqli_stmt_bind_param($statement1, "s", $MaHH);
mysqli_stmt_execute($statement1);

$sql2= "UPDATE `donhang` SET `TrangThai`= '1' WHERE MaHH = ?";
$statement2 = mysqli_prepare($conn, $sql2);
mysqli_stmt_bind_param($statement2, "s", $MaHH);
mysqli_stmt_execute($statement2);

$response = array();
$response["success"] = true;
echo json_encode($response);
}
?>
