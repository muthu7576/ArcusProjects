<?php

require_once('database/Database.php');
$db = new Database();


$requestData = json_decode(file_get_contents('php://input'), true);
$id = $requestData['id'];

$sql = "SELECT t.id,t.type
 FROM texvalley.types t
 inner join stages s on s.rand_sc = t.rand_sc
 where s.id = '$id' AND t.deleted = 0";

$shu1 = $db->getRows($sql);

$response['response'] = $shu1;

echo json_encode($response);
$db->Disconnect();

?>


