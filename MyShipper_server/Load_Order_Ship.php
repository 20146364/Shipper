<?php
 include_once("connection.php");

   $statement = mysqli_prepare($conn, "SELECT donhang.SoTk, nhanship.SoTKNhan , nhanship.MaHH,
   Username, MoTa, VTN, VTD, ThoiGian ,TTDon , nhanship.TGNhan, LuotXem FROM `khachhang`, `donhang` , `nhanship`
   WHERE khachhang.SoTK = nhanship.SoTKNhan and donhang.MaHH = nhanship.MaHH  ORDER BY TGNhan DESC");

   mysqli_stmt_execute($statement);
   mysqli_stmt_store_result($statement);
   mysqli_stmt_bind_result($statement, $SoTK,$SoTKNhan, $MaHH, $Username, $MoTa, $VTN, $VTD, $ThoiGian,$TTDon,$TGNhan, $LuotXem);
   $response["nhanship"] = array();
   $tmp = array();

    while(mysqli_stmt_fetch($statement)){

    $tmp["SoTK"] = $SoTK;
    $tmp["SoTKNhan"] = $SoTKNhan;
    $tmp["MaHH"] = $MaHH;
	  $tmp["Username"] =$Username;
    $tmp["MoTa"] = $MoTa;
	  $tmp["VTN"] = $VTN;
    $tmp["VTD"] = $VTD;
    $tmp["ThoiGian"] = $ThoiGian;
    $tmp["TTDon"] = $TTDon;
    $tmp["TGNhan"] = $TGNhan;
    $tmp["LuotXem"] = $LuotXem;
    array_push($response["nhanship"], $tmp);
    }
   echo json_encode($response);
?>
