<?php

    include_once("connection.php");

    $query = "SELECT  Username, MoTa FROM `khachhang`, `donhang` WHERE khachhang.SoTK = donhang.SoTK and TrangThai = 0  ORDER BY MaHH DESC"; 
    
    $result = mysqli_query($conn, $query);
	if(mysqli_num_rows($result)>0){
    
    while($row = mysqli_fetch_assoc($result)){
		
		$data[] =   $row;
    }
    echo json_encode($data);}
	else{
	echo "result loi";
}


?>