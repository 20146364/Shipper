<?php
 include_once("connection.php");

   $statement = mysqli_prepare($conn, "SELECT  khachhang.SoTk, Username, MoTa , MaHH , VTN, VTD, ThoiGian FROM `khachhang`, `donhang`
   WHERE khachhang.SoTK = donhang.SoTK and TrangThai = 0  ORDER BY ThoiGian DESC");

   mysqli_stmt_execute($statement);
   mysqli_stmt_store_result($statement);
   mysqli_stmt_bind_result($statement, $SoTK, $Username,  $Mota, $MaHH, $VTN, $VTD, $ThoiGian);

   $response["success"] = false;
   $response["success"] = true;
   $response["donhang"] = array();
   $tmp = array();

    while(mysqli_stmt_fetch($statement)){
    $tmp["MaHH"] = $MaHH;
    $tmp["SoTK"] = $SoTK;
	  $tmp["Username"] =$Username;
    $tmp["MoTa"] = $Mota;
	  $tmp["VTN"] = $VTN;
    $tmp["VTD"] = $VTD;
    $tmp["ThoiGian"] = $ThoiGian;
    array_push($response["donhang"], $tmp);
    }
   echo json_encode($response);
?>
