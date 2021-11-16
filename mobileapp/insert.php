<?php
$server = "localhost";
$username = "root";
$password = "12345678";
$dbname = "kfc";

$conn = new mysqli($server, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed:" . $conn->connect_error);
}
$name = $_POST['name'];
$price = $_POST['price'];
$qty = $_POST['qty'];

$sql = "insert INTO `cart` (`name`,`price`,`qty`) VALUES ('$name','$price','$qty')";

$result = $conn->query($sql);
print($result);


mysqli_close($conn);
