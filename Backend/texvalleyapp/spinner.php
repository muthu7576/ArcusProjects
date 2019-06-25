<?php

$servername = "localhost";
        $username = "root";
        $password = "";
        $dbname = "texvalley";

$mysqli = new mysqli($servername,$username,$password,$dbname)or die (mysqli_error($mysqli));
$sql ="SELECT id,categoryname FROM customer_categories ";

$r = mysqli_query($mysqli,$sql);
$result = array();
while($row = mysqli_fetch_array($r)){
 array_push($result,array(
   'id' => $row['id'],  
'categoryname'=>$row['categoryname']
    ));

 
}
$response['response'] = $result;
echo json_encode($response);
mysqli_close($mysqli);
?>


<!--$servername = "arcus.co.in";
        $username = "arcususer";
        $password = "Y0uM@k3M3M@d";
        $dbname = "texvalley";-->