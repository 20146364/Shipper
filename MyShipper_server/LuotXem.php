<?php
   include_once("connection.php");

  $LuotXem = "1";
  $MaHH = $_POST["MaHH"];
  $SoTKNhan = $_POST["SoTKNhan"];
  // $MaHH = "MMH0002";
  // $SoTKNhan = "LHPL0102";

 $sql="update nhanship set LuotXem = ? where MaHH=? and SoTKNhan =?" ;

   $statement = mysqli_prepare($conn,$sql);
 if ( !$statement) {
echo "con dien";
}else{
   mysqli_stmt_bind_param($statement, "sss", $LuotXem, $MaHH, $SoTKNhan);
   mysqli_stmt_execute($statement);

   $response = array();
   $response["success"] = true;


   echo json_encode($response);
}
?>
