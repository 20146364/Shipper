<?php
$servername = "localhost";
$username = "root";  //replace it with your database username
$password = "";  //replace it with your database password
$dbname = "myshipper";
$conn = mysqli_connect($servername, $username, $password, $dbname);
mysqli_set_charset( $conn, 'utf8');
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
?>
