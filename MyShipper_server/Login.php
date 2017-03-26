<?php
   include_once("connection.php");

  $username = $_POST["username"];
   $password = $_POST["password"];
	//  $username = "hung";
	//  $password = "123";

    $statement = mysqli_prepare($conn, "SELECT * FROM khachhang WHERE username = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $username, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$sotk, $username, $password, $hoten, $diachi,  $phone, $quyen);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["sotk"] = $sotk;
        $response["username"] = $username;
        $response["password"] = $password;
        $response["hoten"] = $hoten;
        $response["diachi"] = $diachi;
        $response["phone"] = $phone;
    	  $response["quyen"] = $quyen;

    }

    echo json_encode($response);
?>
