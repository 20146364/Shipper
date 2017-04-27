<?php
   include_once("connection.php");
   $MaHH = $_POST["MaHH"];
  $SoTK = $_POST["SoTK"];
  $MoTa = $_POST["MoTa"];
  $VTN = $_POST["VTN"];
  $VTD = $_POST["VTD"];
  $ThoiGian = $_POST["ThoiGian"];
  // $MaHH = "MMH0002";
  // $SoTK = 	"LHPL0103";
  // $MoTa = "Cho Hang";
  // $VTN = "ABC";
  // $VTD = "ABC";
  // $ThoiGian = "2017-03-17";




 $sql="update donhang set MoTa=?,VTN=?,VTD=? , ThoiGian = ? where SoTK=? and MaHH =?" ;

   $statement = mysqli_prepare($conn,$sql);
 if ( !$statement) {
echo "con dien";
}else{
   mysqli_stmt_bind_param($statement, "ssssss", $MoTa, $VTN, $VTD,  $ThoiGian, $SoTK, $MaHH);
   mysqli_stmt_execute($statement);

   $response = array();
   $response["success"] = true;


   echo json_encode($response);
}
?>
