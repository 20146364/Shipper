<?php
   include_once("connection.php");

   $SoTK = $_POST["SoTK"];
   $MaHH = $_POST["MaHH"];
  // $SoTK = "LHPL0118";
  // $MaHH = "MMH0008";


    $statement = mysqli_prepare($conn, "DELETE  FROM donhang WHERE SoTK = ? AND MaHH = ?");
    mysqli_stmt_bind_param($statement, "ss", $SoTK, $MaHH);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
    }

    echo json_encode($response);
?>
