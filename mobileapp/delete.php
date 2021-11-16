<?php
$server = "localhost";
$username = "root";
$password = "12345678";
$dbname = "kfc";

$conn = new mysqli($server, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed:" . $conn->connect_error);
}

$sql = "DELETE FROM `cart`";

$result = $conn->query($sql);
print($result);

mysqli_close($conn);
