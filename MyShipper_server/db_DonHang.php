<?php
 $con = mysqli_connect("localhost", "root", "", "myshipper");
   // $SoTK = $_POST["SoTK"];
   //$SoTK ='LHPL0007';

   $statement = mysqli_prepare($con, "SELECT khachhang.SoTK= donhang.SoTK, Username, MoTa , VTN, ThoiGian FROM `khachhang`, `donhang` WHERE khachhang.SoTK = donhang.SoTK and TrangThai = 0  ORDER BY MaHH DESC");
   // mysqli_stmt_bind_param($statement, "s", $SoTK);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $SoTK,$Username, $Mota, $VTN , $ThoiGian);


        //$response["success"] = false;

      //  $response["success"] = true;
        $response["donhang"] = array();
        $tmp = array();
    while(mysqli_stmt_fetch($statement)){
    	// $tmp = array();
       // $tmp["MaHH"] = $MaHH;
     $tmp["SoTK"] = $SoTK;
	   $tmp["Username"] =$Username;
       $tmp["MoTa"] = $Mota;
	   $tmp["VTN"] = $VTN;
        //$tmp["VTD"] = $VTD;
          //$tmp["Ngay"] = $Ngay;
        $tmp["ThoiGian"] = $ThoiGian;
       // $tmp["TrangThai"] = $TrangThai;
        array_push($response["donhang"], $tmp);


    }

   echo json_encode($response);



?>
