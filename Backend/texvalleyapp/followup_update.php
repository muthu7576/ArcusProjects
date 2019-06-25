<?php

require_once('database/Database.php');
$db = new Database();

$requestData = json_decode(file_get_contents('php://input'), true);
$opportunityid=$requestData['opportunity_id'];
$mdeofcntct = $requestData['contact_id'];
$classification = $requestData['classification'];
$visitedby = $requestData['user_id'];
$stage = $requestData['stage_id'];
$remark = $requestData['remark'];
$remndrdate = $requestData['reminddate'];
$time = $requestData['remindtime'];

$sql1 = ("INSERT INTO calls (opportunity_id,contact_id,classification,user_id,stage_id,remark,reminddate,remindtime)
         VALUES ('$opportunityid','$mdeofcntct','$classification','$visitedby','$stage','$remark','$remndrdate','$time')");

$z1= $db->insertRow($sql1);

//$check = ("SELECT id from customers where name = '$name' and mobileno = '$contactnumber'");
//$mysqli->query($check);
//while ($rowcheck = mysqli_fetch_assoc($check)){
//    $result[]= $rowcheck;
//}
if($z1){
    $response['status'] = 200;
    
}
else{
    $response['status'] = 0;
    
}
echo json_encode($response);
mysqli_close($db);
?>

