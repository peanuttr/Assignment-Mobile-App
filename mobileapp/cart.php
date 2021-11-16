<?php
$server = "localhost";
$username = "root";
$password = "12345678";
$dbname = "kfc";

$conn = new mysqli($server, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed:" . $conn->connect_error);
}

$sql = "SELECT * FROM `cart`";
// print($sql);
$result = $conn->query($sql);
if ($result !== false) {
    // echo "Success";
    while ($row = mysqli_fetch_assoc($result)) {
        $output[] = $row;
    }
    mysqli_free_result($result);
} else {
    echo "Error Cannot query : " . $conn->error;
}
$conn->close();
print(json_encode($output));
