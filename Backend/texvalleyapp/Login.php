<?php
$servername = "localhost";
        $username = "root";
        $password = "";
        $dbname = "texvalley";

$mysqli = new mysqli($servername,$username,$password,$dbname)or die (mysqli_error($mysqli));
$requestData = json_decode(file_get_contents('php://input'), true);
$username = $requestData['username'];
$password = ($requestData['password']);

$sql ="SELECT id FROM `users` where username = '$username' and password = '$password'";

$query = mysqli_query($mysqli,$sql);

while($rows = mysqli_fetch_assoc($query)){
  $result[] =   $rows;
}

if(count($result) == 1){
    $response['isValid'] = true;
    $response['user_id'] = $result[0]['id'];
}else{
    $response['isValid'] = false;
}
echo json_encode($response);
mysqli_close($mysqli);

?>
