<?php

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "texvalley";

$mysqli = new mysqli($servername, $username, $password, $dbname)or die(mysqli_error($mysqli));

$sql = "SELECT id,stage FROM `stages`";

$r = mysqli_query($mysqli, $sql);
$result = array();
while ($row = mysqli_fetch_array($r)) {
    array_push($result, array(
        'id' => $row['id'],
        'stage' => $row['stage']
    ));
}
$response['response'] = $result;
echo json_encode($response);
mysqli_close($mysqli);
?>

