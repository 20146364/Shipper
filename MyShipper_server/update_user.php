 <?php
    include_once("connection.php");
	 $username = $_POST["username"];
	 $password = $_POST["password"];
     $hoten = $_POST["hoten"];
	 $diachi = $_POST["diachi"];
	 $phone = $_POST["phone"];
	//$username = "Hung";
	//$password = "1234";
	//$hoten = "Hung";
	//$diachi = "Ha";
	//$phone ="0";
	 
	
 
	$sql="update khachhang set password=?,hoten=?,diachi=? , phone = ? where username=?";

    $statement = mysqli_prepare($conn,$sql);
	if ( !$statement) {
 echo "con dien";
}else{
    mysqli_stmt_bind_param($statement, "sssss", $password, $hoten, $diachi,  $phone, $username);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;
    

    echo json_encode($response);
}
?>
