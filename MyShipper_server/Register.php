<?php
include_once("connection.php");

// $username = $_POST["username"];
// $password = $_POST["password"];
// $hoten = $_POST["hoten"];
// $diachi = $_POST["diachi"];
// $quyen = $_POST["quyen"];
// $phone = $_POST["phone"];
$username = "dmDun";
$password = "123";
$hoten = "1235";
$diachi = "123";
$quyen = "1";
$phone = "123456";

 $sql="INSERT INTO `khachhang`( `Username`, `Password`,`hoten`,`diachi`, `phone`,`quyen`) VALUES (?,?,?,?,?,?)";

   $statement = mysqli_prepare($conn, $sql);
 if ( !$statement) {
echo "Loi";
}else{
   mysqli_stmt_bind_param($statement, "ssssss", $username, $password, $hoten, $diachi,  $phone, $quyen);
   mysqli_stmt_execute($statement);

   $statement1 = mysqli_prepare($conn, "SELECT * FROM khachhang WHERE username = ? ");
    mysqli_stmt_bind_param($statement1, "s", $username);
    mysqli_stmt_execute($statement1);

    mysqli_stmt_store_result($statement1);
    mysqli_stmt_bind_result($statement1, $SoTK, $username , $password, $hoten , $diachi,$phone, $quyen);

    $response1 = array();
    $response1["success"] = false;

    while(mysqli_stmt_fetch($statement1)){
        $response1["success"] = true;
        $response1["SoTK"] = $SoTK;
        $response1["username "] = $username ;
        $response1["password" ] = $password;
        $response1["hoten"] = $hoten;
        $response1["diachi"] = $diachi;
		    $response1["phone"] = $phone;
        $response1["quyen"] = $quyen;

    }
	echo json_encode($response1);
}
?>
