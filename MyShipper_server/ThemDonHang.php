<?php
 include_once("connection.php");
$SoTK = $_POST["SoTK"];
$MoTa = $_POST["MoTa"];
$VTN = $_POST["VTN"];
$VTD = $_POST["VTD"];
$ThoiGian = $_POST["ThoiGian"];
$TrangThai = "0";


// $SoTK = "LHPL0118";
// $MoTa ="Mô Tả";
// $VTN = "VTN";
// $VTD = "VTD";
// $ThoiGian = "2017-03-31";

 $sql="INSERT INTO `donhang`( `SoTK`, `MoTa`, `VTN`, `VTD`, `ThoiGian`, `TrangThai`) VALUES (?,?,?,?,?,?)";

   $statement = mysqli_prepare($conn, $sql);
 if ( !$statement) {
echo "con diem";
}else{
   mysqli_stmt_bind_param($statement, "ssssss",$SoTK, $MoTa, $VTN, $VTD, $ThoiGian, $TrangThai);
   mysqli_stmt_execute($statement);

   $response = array();
   $response["success"] = true;

   echo json_encode($response);
}
?>
